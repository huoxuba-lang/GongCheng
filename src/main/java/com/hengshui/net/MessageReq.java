package com.hengshui.net;

import com.hengshui.pojo.User;

import java.io.Serializable;

//客户端请求信息
public class MessageReq implements Serializable {
    //消息类型
    private MsgType msgType;
    //请求的用户信息
    private User user;

    //消费金额
    private int  money;

    public float getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
