package com.hengshui.net;

import com.hengshui.pojo.User;

import java.io.Serializable;

//服务器回复3
public class MessageResp implements Serializable {
    //消息类型
    private MsgType msgType;
    //消息处理结果
    private OpResult opResult;

    //用户信息
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public OpResult getOpResult() {
        return opResult;
    }

    public void setOpResult(OpResult opResult) {
        this.opResult = opResult;
    }
}
