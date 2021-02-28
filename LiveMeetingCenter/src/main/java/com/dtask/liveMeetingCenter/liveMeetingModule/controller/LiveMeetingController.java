package com.dtask.liveMeetingCenter.liveMeetingModule.controller;

import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhong on 2021-2-28.
 */
@Component
@ServerEndpoint("/liveMeeting/{roomID}/{userID}")
public class LiveMeetingController  {

    private int userID;

    private int roomID;

    private static ConcurrentHashMap<Integer,UserEntity> userMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("roomID") int roomID,@PathParam("userID") int userID, Session session){
        this.userID = userID;
        this.roomID = roomID;

        UserEntity userEntity = new UserEntity();
        userEntity.setRoomID(roomID)
                .setUserID(userID)
                .setSession(session);

        // todo 获取用户角色
        userEntity.setRole("host");

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
        // todo 获取权限，判断角色
        // 会议纪要
        if (text.startsWith("note:")){

           Map<Integer,UserEntity> roomMap = getRoomUser(roomID);
           roomMap.forEach((id,user)->{
               user.getSession().getAsyncRemote().sendText(text);
           });
            return "";
        }

        // 更换记录员
        if (text.startsWith("noteEditor:")){
            // 获取当前记录员,并通知取消记录员身份
            Map<Integer,UserEntity> roomMap = getRoomUser(roomID);
            roomMap.forEach((id,user)->{
                if (user.getRole().equals("editor")){
                    user.getSession().getAsyncRemote().sendText("revokeEditor");
                    user.setRole("host");
                    // todo 判断是否是host,如果是则为host,不是则为audience
                    userMap.put(user.getUserID(),user);
                }
            });

            // 获取当前记录员，并通知授予记录员身份
            int newEditorID = Integer.parseInt(text.replaceFirst("noteEditor:",""));
            roomMap.get(newEditorID).getSession().getAsyncRemote().sendText("grantEditor");

            UserEntity newEditor = userMap.get(newEditorID);
            newEditor.setRole("editor");
            userMap.put(newEditorID,newEditor);

            flushRoomUser(roomID);
            return "";
        }

        return "";
    }

    private Map<Integer,UserEntity> getRoomUser(int roomID){
        Map<Integer,UserEntity> roomMap = new HashMap<>();
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
        Map<Integer,UserEntity> roomMap = getRoomUser(roomID);

        roomMap.forEach((id,user)->{
            user.getSession().getAsyncRemote().sendText("userList:" + JsonUtil.objectToJson(roomMap));
        });
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
