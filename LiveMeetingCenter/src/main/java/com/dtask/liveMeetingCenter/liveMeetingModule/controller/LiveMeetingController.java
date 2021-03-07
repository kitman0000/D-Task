package com.dtask.liveMeetingCenter.liveMeetingModule.controller;

import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeetingCenter.liveMeetingModule.dao.LiveMeetingDao;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhong on 2021-2-28.
 */
@Component
@ServerEndpoint("/api/liveMeeting/{roomID}/{userID}")
public class LiveMeetingController  {

    static Logger logger = LoggerFactory.getLogger(LiveMeetingController.class);

    static {
        logger.info("会议插件初始化成功");
    }

    private static LiveMeetingDao liveMeetingDao;

    private final Object lock = new Object();

    @Autowired
    public void setLiveMeetingDao(LiveMeetingDao liveMeetingDao) {
        LiveMeetingController.liveMeetingDao = liveMeetingDao;
    }

    private String userID;

    private int roomID;

    private String username;

    private String role = "";

    private boolean isEditor = false;

    private final String ROLE_HOST = "host";

    private final String ROLE_AUDIENCE = "audience";

    private static ConcurrentHashMap<String,UserEntity> userMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("roomID") int roomID,@PathParam("userID") String uUserID, Session session){
//        int nodeID = Integer.parseInt(uUserID.substring(0,3));
//        int userID = Integer.parseInt(uUserID.substring(5));

        this.userID = uUserID;

        // 获取节点ID和用户ID
        int nodeID = Integer.parseInt(uUserID.substring(0,3));
        int uID = Integer.parseInt(uUserID.substring(3));

        this.username = liveMeetingDao.getUsername(nodeID,uID);

        this.roomID = roomID;

        UserEntity userEntity = new UserEntity();
        userEntity.setRoomID(roomID)
                .setUserID(userID)
                .setSession(session)
                .setUsername(this.username);

        String hostID = liveMeetingDao.getRoomHost(roomID);
        if (userID.equals(hostID)){
            // 用户是主持人
            userEntity.setRole(ROLE_HOST);
            sendSessionText(session,"ROOM_HOST");
        }else {
            // 将用户设为普通观众
            userEntity.setRole(ROLE_AUDIENCE);
        }

        userMap.put(userID,userEntity);

        flushRoomUser(roomID);
    }

    @OnClose
    public void onClose(Session session) {
        userMap.remove(userID);
        flushRoomUser(roomID);
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public String OnMessage(String text) {
        // 会议纪要
        if (text.startsWith("note:")){
            if (!userMap.get(userID).isEditor())
                return ""; // 如果用户不是记录员，返回false;

           Map<String,UserEntity> roomMap = getRoomUser(roomID);
           roomMap.forEach((id,user)->{
               sendSessionText(user.getSession(),text);
           });
            return "";
        }

        // 更换记录员
        if (text.startsWith("noteEditor:")){
            // 获取当前记录员,并通知取消记录员身份
            Map<String,UserEntity> roomMap = getRoomUser(roomID);
            roomMap.forEach((id,user)->{
                if (user.isEditor()){
                    sendSessionText(user.getSession(),"revokeEditor");
                    user.setEditor(false);
                    userMap.put(user.getUserID(),user);
                }
            });

            // 获取当前记录员，并通知授予记录员身份
            String newEditorID = text.replaceFirst("noteEditor:","");
            sendSessionText(roomMap.get(newEditorID).getSession(),"grantEditor");

            UserEntity newEditor = userMap.get(newEditorID);
            newEditor.setEditor(true);
            userMap.put(newEditorID,newEditor);

            flushRoomUser(roomID);
            return "";
        }

        return "";
    }

    private Map<String,UserEntity> getRoomUser(int roomID){
        Map<String,UserEntity> roomMap = new HashMap<>();
        roomMap.putAll(userMap);

        Iterator room = roomMap.entrySet().iterator();
        while (room.hasNext()){
            Map.Entry<Integer,UserEntity> entry = (Map.Entry) room.next();
            if (entry.getValue().getRoomID() != roomID){
                room.remove();
            }
        }

        return roomMap;
    }

    private void flushRoomUser(int roomID){
        Map<String,UserEntity> roomMap = getRoomUser(roomID);

        roomMap.forEach((id,user)->{
            sendSessionText(user.getSession(),"userList:" + JsonUtil.objectToJson(roomMap));
        });
    }

    private void sendSessionText(Session session,String text){
        synchronized (lock){
            try {
                session.getBasicRemote().sendText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * WebSocket通讯协议：
     * 地址：/liveMeeting/{roomID}/{userID}
     * 服务端收：
     *      更新会议纪要：note:+会议纪要
     *      更新记录员：noteEditor:+记录员ID
     * 服务端发：
     *      刷新会议用户：userList+会议人员json
     *      更新会议纪要：note:+会议纪要
     *      授予记录员：grantEditor
     *      取消记录员：revokeEditor
     */
}
