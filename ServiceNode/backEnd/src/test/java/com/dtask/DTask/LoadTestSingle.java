package com.dtask.DTask;

import lombok.Data;
import lombok.experimental.Accessors;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by zhong on 2021-3-13.
 */
public class LoadTestSingle {
    private static volatile int success = 0;
    private static volatile int fail = 0;

    public static void main(String[] args) throws JSONException, InterruptedException {
        setContext();

        final int clientCount = 3500;

        final CountDownLatch downLatch = new CountDownLatch(clientCount);

        final String rootUrl = "https://node1.taskingcenter.com"; //https://node1.taskingcenter.com

        final String token = "eyJ1c2VySUQiOjEwMDAsInVzZXJuYW1lIjoiYWRtaW4iLCJjcmVhdGVUaW1lIjoxNjE1NTUzOTAyNDg0fQ==";
        final String postTaskUrl = rootUrl + "/api/localTask/localTask";
        final String postSubTaskUrl = rootUrl + "/api/localTask/localSubTask";
        final String getSubTaskUrl = rootUrl + "/api/localTask/UserLocalTaskList";

//        LocalTask localTask = new LocalTask();
//        localTask.setName("测试任务123456");
        long startSec = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();


        for (int i = 0; i < clientCount; i++) {
            int finalI = i;
            executorService.submit(() -> {

                try {
                    Map<String, String> param = new HashMap();
                    param.put("name", "测试任务" + finalI);
                    (new HttpUtils()).submitPostData(postTaskUrl, param, "utf-8", token,"POST");
                    success++;

                    Map<String, String> param2 = new HashMap();
                    param2.put("page", "1");
                    param2.put("taskName", "测试任务" + finalI);
                    param2.put("taskCreatorID", "0" + finalI);


                    String result = (new HttpUtils()).doGet(getSubTaskUrl,param2,token);

                    String id = result.split("id\":")[1].split(",\"nam")[0];
                    success++;

                    for (int j = 0; j < 3; j++) {
                        int status = getRandom(4,0);
                        int level = getRandom(4,0);
                        int start = getRandom(4,0);
                        String endTime = getRandomTime();

                        Map<String, String> param3 = new HashMap();
                        param3.put("taskID", id);
                        param3.put("name", "测试子任务" + j);
                        param3.put("content", "压力测试子任务,id:" + j);
                        param3.put("deadline", endTime);
                        param3.put("status", String.valueOf(status));
                        param3.put("startTime", String.valueOf("2021-03-01"));
                        param3.put("level", String.valueOf(level));
                        param3.put("tag", String.valueOf("测试任务"));
                        param3.put("star", String.valueOf(start));

                        String str = (new HttpUtils()).submitPostData(postSubTaskUrl, param3, "utf-8", token,"POST");

                        if (str.equals("ERR")){
                            fail++;
                        }else {
                            success++;
                        }

                        Thread.sleep(10);

                    }
                }catch (Throwable e){
                    fail++;
                }

                downLatch.countDown();


            });
        }

        downLatch.await();
        long endSec = System.currentTimeMillis();
        System.out.println("Time spend:" + (endSec - startSec) / 1000);

        System.out.println("Success:" + success + "\tFail:" + fail);

    }

    private static int getRandom(int max,int min) {
        return (int) (Math.random()*(max-min)+min);
    }

    private static String getRandomTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,getRandom(365,0));
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(calendar.getTime());
    }

    private static void setContext(){
        Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));

        for(String log:loggers) {
            Logger logger = (Logger) LoggerFactory.getLogger(log);
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
        }
    }
}
