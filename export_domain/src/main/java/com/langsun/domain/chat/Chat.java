package com.langsun.domain.chat;

import java.util.Date;

public class Chat {
    private String chatId;	        /*消息Id*/
    private String companyId;		/*企业ID*/
    private Date chatData;		/*消息日期*/
    private String userName;		/*用户名字*/
    private String date;

    private String content;		    /*聊天内容*/
    private String userId;       	/*用户ID*/

    @Override
    public String toString() {
        return "Chat{" +
                "chatId='" + chatId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", chatData=" + chatData +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getChatData() {
        return chatData;
    }

    public void setChatData(Date chatData) {
        this.chatData = chatData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
