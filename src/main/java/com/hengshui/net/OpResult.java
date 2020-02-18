package com.hengshui.net;

public enum OpResult {
    //登陆结果
    LOGIN_OK,
    LOGIN_NOT_EXISTS,
    LOGIN_PWD_ERROR,

    //注册结果
    REG_OK,
    REG_EXISTS,

    //申请号码结果
    ASKFORNUM_OK,
    ASKFORNUM_EXISTS,

    //充费结果
    CHONGZHI_OK,

    //查询结果
    QUERYMONEY_OK
}
