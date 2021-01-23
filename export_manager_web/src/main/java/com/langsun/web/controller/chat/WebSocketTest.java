package com.langsun.web.controller.chat;

import com.langsun.domain.chat.Chat;
import com.langsun.domain.system.User;
import com.langsun.service.chat.ChatService;
import com.langsun.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Controller
@ServerEndpoint(value="/chat/findAll.do",configurator = SpringConfigurator.class)
public class WebSocketTest extends BaseController {

    //  这里使用静态，让 service 属于类
    private static ChatService chatService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(ChatService chatService) {
        WebSocketTest.chatService = chatService;
    }
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private HttpSession httpSession;
    private User user;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
//        System.out.println("是同一个session吗?--"+(this.session==super.session));
        //System.out.println(session.getRequestURI());
        //System.out.println(session.getId());
        //HttpSession httpSession = (HttpSession) session;

        addOnlineCount();           //在线数加1
        webSocketSet.add(this);     //加入set中
//        super.session.setAttribute("onlineCount",onlineCount);
        System.out.println("new link join！new there are" + getOnlineCount()+" online");
        sendMessage("当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
//        super.session.setAttribute("onlineCount",onlineCount);
        sendMessage("当前在线人数为" + getOnlineCount());
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        String[] arrstr = message.split("@,@!");
        for (String s : arrstr) {
            System.out.println(s);
        }
        String userId = arrstr[2];

//        System.out.println("userid:"+userId);
        String userName = arrstr[1];
        String message1= "";
        String companyId = arrstr[0];
        for(int i = 3;i < arrstr.length;i++){
            message1 = message1 + arrstr[i];
            System.out.println(arrstr[i]);
        }
        Chat chat =new Chat();
        chat.setUserId(userId);
        chat.setUserName(userName);
        chat.setCompanyId(companyId);
        chat.setContent(message1);
        chatService.toAdd(chat);
        //群发消息
        for(WebSocketTest item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}
