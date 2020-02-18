package com.hengshui.net;

import com.hengshui.pojo.User;

import java.util.HashMap;
import java.util.Map;

//单例模式,保存全局唯一的数据
public class Singleton {
    public static Singleton self;
    //包含所有用户的map
    public Map<String, User>allUser = new HashMap<>();

    //用来记录所以手机号的map
    //key  手机号
    private  Map<String, User>allNums = new HashMap<>();


//获取自己类的示例的方法
    public static Singleton instance() {
        //self是空指针
        if (self == null){
            self = new Singleton();
        }
        return self;
    }

    public Map<String, User> getAllNums() {
        return allNums;
    }

    public void setAllNums(Map<String, User> allNums) {
        this.allNums = allNums;
    }

    public Map<String, User> getAllUser() {
        return allUser;
    }

    public void setAllUser(Map<String, User> allUser) {
        this.allUser = allUser;
    }
}
