package com.langsun.domain.feedback;

import java.util.Date;

/**
 * @author slang
 * @date 2020-11-08 14:52
 * @Param $
 * @return $
 **/
public class Feedback {

    public Feedback(){

    }

    private String feedbackId;	        /*消息Id*/
    private String companyId;		/*企业ID*/
    private String date;		/*消息日期*/
    private String userName;		/*用户名字*/

    private String content;		    /*聊天内容*/
    private String userId;       	/*用户ID*/
    private int state;              /*0是个人反馈,1是系统反馈*/

    private int read;               /*0是未读,1已读*/

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "feedback{" +
                "feedbackId='" + feedbackId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", state=" + state +
                '}';
    }
}
