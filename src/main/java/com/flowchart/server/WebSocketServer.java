package com.flowchart.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.flowchart.entity.OnlineFile;
import com.flowchart.service.OnlineFileService;
import com.flowchart.util.FileUtils;
import com.flowchart.util.RedisTemplateUtil;
import com.flowchart.util.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Component
@ServerEndpoint("/webSocket/{groupId}/{userInfo}")
public class WebSocketServer {

    @Resource
    public RedisUtil redisUtil;
    @Resource
    public RedisTemplateUtil redisTemplateUtil;

    public static OnlineFileService onlineFileService;

    public static SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    /**
     * 当前在线连接总人数
     */
    private static final AtomicInteger onlineNum = new AtomicInteger();

    /**
     * 获取组内在线人数
     * 存储结构，<groupId,AtomicInteger>
     */
    private static final ConcurrentHashMap<String,Integer> GroupAtomicInteger = new ConcurrentHashMap<>();

    /**
     * 用户连接，消息发送
     * 开始连接或者断开连接都要更新连接池
     * 存储结构，<组ID,[Session,Session,Session]>
     */
    private static ConcurrentHashMap<String, ArrayList<Session>> sessionPools = new ConcurrentHashMap<>();

    /**
     * 获取用户名
     * 开始连接或者断开连接都要更新缓存池
     * 存储结构，<sessionId,username>
     */
    private static ConcurrentHashMap<String,String> sessionUsernames = new ConcurrentHashMap<>();




    /**
     * 用户连接，消息发送
     * 开始连接或者断开连接都要更新连接池
     * 存储结构，<组ID,[Session,Session,Session]>
     */
    private static ConcurrentHashMap<String, Session> sessionPools2 = new ConcurrentHashMap<>();

    /**
     * 1.建立连接成功调用
     * @param session 连接用户session
     * @param groupId 连接用户组ID
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "groupId") String groupId, @PathParam(value = "userInfo") String userInfo){

        JSONObject parse = JSONObject.parseObject(userInfo);
        parse.put("sessionId",session.getId());
        sessionPools2.put(session.getId(),session);

        redisTemplateUtil.sAdd("groupId_"+groupId, userInfo);

        //新用户获取数据从文件中直接调用
        OnlineFile onlineFile = new OnlineFile();
        onlineFile.setSocketCode(groupId);

        OnlineFile onlineFile1 = onlineFileService.selectFileBySocketCode(onlineFile);
        //获取文件数据
        String fileData = FileUtils.getFileData(onlineFile1.getFilePath(),"chart");

        //向登录用户发送文件数据
        sendMessage(session,"2", fileData,null);







        ArrayList<Session> list = new ArrayList<>();
        list.add(session);
        ArrayList<Session> sessionList = sessionPools.get(groupId);
        //判断当前组是否已经存在成员
        if(null == sessionList){
            sessionPools.put(groupId, list);
        }else{
            sessionList.addAll(list);
            sessionPools.put(groupId, sessionList);
        }

        //存储每一位用户的姓名
        sessionUsernames.put(session.getId(),userName);
        //计数器
        addOnlineCount(session);

        Integer groupCount = GroupAtomicInteger.get(groupId);
        if(groupCount !=null){
            GroupAtomicInteger.put(groupId,groupCount+1);
        }
        if(groupCount == null){
            GroupAtomicInteger.put(groupId,1);
        }


        //新用户获取数据从文件中直接调用
        OnlineFile onlineFile = new OnlineFile();
        onlineFile.setSocketCode(groupId);

        OnlineFile onlineFile1 = onlineFileService.selectFileBySocketCode(onlineFile);
        //获取文件数据
        String fileData = FileUtils.getFileData(onlineFile1.getFilePath(),"chart");
        //向登录用户发送文件数据
        sendMessage(session,"2", fileData,null);

        List<Session> sList = sessionPools.get(groupId);

        List<String> userNameList = new ArrayList<>();
        //同步在线人员列表
        for (Session s : sList) {
            userNameList.add(sessionUsernames.get(s.getId()));
        }
        String o = JSON.toJSON(userNameList).toString();

        //2.用户加入连接通知
        for (Session value : sList) {
            sendMessage(value,"5",o,null);
            if(!session.equals(value)){
                sendMessage(value, "3", userName + "加入连接！",null);
            }
        }
    }

    /**
     * 2.关闭连接时调用
     * @param groupId 当前组ID
     * @param session 退出连接用户session
     */
    @OnClose
    public void onClose(@PathParam(value = "groupId") String groupId,Session session){

        ArrayList<Session> list = sessionPools.get(groupId);
        //从缓存池中移除当前用户session
        list.remove(session);
        sessionPools.put(groupId,list);
        //从用户名数组中获取用户名
        String userName = sessionUsernames.get(session.getId());
        //更新计数器
        subOnlineCount(session);

        Integer groupCount = GroupAtomicInteger.get(groupId);
        if(groupCount != null && groupCount > 0){
            GroupAtomicInteger.put(groupId,groupCount-1);
        }


        //从用户名数组中移除当前用户名
        sessionUsernames.remove(session.getId());

        List<String> userNameList = new ArrayList<>();
        //同步在线人员列表
        for (Session s : list) {
            userNameList.add(sessionUsernames.get(s.getId()));
        }
        String o = JSON.toJSON(userNameList).toString();

        //已经断开了连接，组发
        for (Session value : list) {
            sendMessage(value,"5",o,null);
            if(!session.equals(value)){
                sendMessage(value, "4", userName + "断开了连接！",null);
            }
        }
    }

    /**
     * 3.收到客户端信息
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        // JSON字符串解析
        JSONObject jsonObject = new JSONObject(message);
        // 分类发送数据，type = 1:文本（消息提示，组内通讯），2：画布,3:加入，4：退出，5：同步用户列表
        String type = jsonObject.get("type").toString();
        String data = jsonObject.get("data").toString();

        String event = jsonObject.get("event").toString();
        //消息发送，分组发送
        String groupId = getGroupBySession(session);
        ArrayList<Session> list = sessionPools.get(groupId);
        String userName = null;
        if("1".equals(type)){
            userName = sessionUsernames.get(session.getId());
        }
        try {
            for (Session se : list) {
                //向组内用户同步数据，除自己
                if ("2".equals(type) && !se.getId().equals(session.getId())) {
                    //消息发送
                    sendMessage(se, type, data,event);
                }
                //向组内成员发送消息，所有人
                if ("1".equals(type)) {
                    sendMessage(se, type, data,userName);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 4.发送消息
     */
    public static void sendMessage(Session session, String type, String message,String event)  {
        if(session != null){
            //单体服务加锁
            synchronized (session.getId()) {
                try {
                    //1.创建返回对象
                    JSONObject jsonObject = new JSONObject();
                    if ("1".equals(type)) {
                        //获取用户名
                        jsonObject.put("userName",event);
                    }

                    //获取发送信息
                    jsonObject.put("data",message);
                    //获取发送数据类型，type = 1:文本（消息提示，组内通讯），2：画布
                    jsonObject.put("type",type);
                    //操作类型
                    jsonObject.put("event",event);
                    //当前在线人数
                    String groupId = getGroupBySession(session);
                    jsonObject.put("online",GroupAtomicInteger.get(groupId));

                    jsonObject.put("dateTime",formatter.format(new Date()));

                    //2.返回JSON对象字符串
                    session.getBasicRemote().sendText(JSON.toJSONString(jsonObject));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 错误时调用
     * @param session 发生错的的用户session
     * @param throwable 抛出的异常
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.err.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(Session session){
        onlineNum.incrementAndGet();
        System.err.println(session.getId() + "加入webSocket连接！当前总人数为" + onlineNum);
        FileUtils.setWebSocketNumberInFile(onlineNum.get());
    }

    public static void subOnlineCount(Session session) {
        onlineNum.decrementAndGet();
        System.err.println(session.getId() + "断开webSocket连接！当前总人数为" + onlineNum);
        FileUtils.setWebSocketNumberInFile(onlineNum.get());
    }

    public static String getGroupBySession(Session session){
        AtomicReference<String> result = null;
        //消息发送，分组发送
        ConcurrentHashMap.KeySetView<String, ArrayList<Session>> groupIdSet = sessionPools.keySet();
        for (String s : groupIdSet) {
            ArrayList<Session> list = sessionPools.get(s);
            if(list.contains(session)){
                return s;
            }
        }
        return null;
    }
}


/**
 * 数据模型：
 * type:1-聊天文本，2-画布，3-登录，4-退出登录
 *
 * onlineNum：
 * 当前连接总人数
 *
 * sessionPools：
 * 用户连接，消息发送
 * 开始连接或者断开连接都要更新连接池
 * 存储结构，<组ID,[Session,Session,Session]>
 *
 * sessionUsernames：
 * 获取用户名
 * 开始连接或者断开连接都要更新缓存池
 * 存储结构，<sessionId,username>
 */



