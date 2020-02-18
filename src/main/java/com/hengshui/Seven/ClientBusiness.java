package com.hengshui.Seven;

import com.hengshui.net.MessageReq;
import com.hengshui.net.MessageResp;
import com.hengshui.net.MsgType;
import com.hengshui.net.OpResult;
import com.hengshui.pojo.User;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientBusiness {
    private OutputStream os= null;
    private InputStream is = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Scanner input = new Scanner(System.in);

    //登录成功后的用户
    private User userLogin ;


    //菜单操作
    public void menu(){
        // 客户端连接哪个IP，port
        try {
            //Socket对象在客户端和服务器之间建立连接
            //构造方法
            Socket socket = new Socket("127.0.0.1",8800);

            //打开输入/输出流
             os = socket.getOutputStream();
             is = socket.getInputStream();
            oos = new ObjectOutputStream(os);

            //注册
            reg();

            //登录
            while (true){
                boolean res = login();
                if (res == true){
                    break;
                }
            }


            //申请号码
            askForNum();
            //话费充值
            chongZhi();


            //查询余额
            queryMoney();

            // 关闭流
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //注册
    public boolean reg(){

        MessageReq req = new MessageReq();
        User user = new User();

        System.out.println("现在开始注册==========");
        System.out.println("请输入用户名");
        String userName = input.next();
        System.out.println("请输入密码");
        String userPwd = input.next();

        user.setUserName(userName);
        user.setUserPassword(userPwd);
        req.setUser(user);
        req.setMsgType(MsgType.REG);
        // 发送一个对象给服务器
        try {
            oos.writeObject(req);

            if(ois == null){
                ois = new ObjectInputStream(is);
            }
            MessageResp resp = (MessageResp)ois.readObject();
            if(resp.getMsgType() == MsgType.REG){
                if(resp.getOpResult() == OpResult.REG_OK){
                    System.out.println("注册成功，请登录");
                    return true;
                }else if(resp.getOpResult() == OpResult.REG_EXISTS){
                    System.out.println("用户名已经存在，请重新注册");
                }else{
                    System.err.println("注册出现未处理的登录结果！" + resp.getOpResult());
                }
            }else{
                System.err.println("出现未处理的消息！" + resp.getMsgType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    //登录
    public boolean login(){
        MessageReq req = new MessageReq();

        //发送客户端登录信息，及向输出流中写入信息
        User user = new User();
        System.out.println("现在开始登陆：");
        System.out.println("请输入用户名");
        String userName = input.next();
        System.out.println("请输入密码");
        String userPwd = input.next();

        user.setUserName(userName);
        user.setUserPassword(userPwd);
        req.setUser(user);
        req.setMsgType(MsgType.LOGIN);


        // 发送一个对象给服务器
        //对象序列化
        try {
            oos.writeObject(req);

            if (ois == null){
                ois = new ObjectInputStream(is);
            }

            MessageResp resp = (MessageResp)ois.readObject();
            if(resp.getMsgType() == MsgType.LOGIN){
                if(resp.getOpResult() == OpResult.LOGIN_OK){
                    System.out.println("登录成功");
                    userLogin = resp.getUser();
                    return true;
                }else if(resp.getOpResult() == OpResult.LOGIN_NOT_EXISTS){
                    System.out.println("用户名不存在，请重新登录");
                }else if(resp.getOpResult() == OpResult.LOGIN_PWD_ERROR){
                    System.out.println("密码错误，请重新登录");
                }else{
                    System.err.println("登录出现未处理的登录结果！" + resp.getOpResult());
                }
            }else{
                System.err.println("出现未处理的消息！" + resp.getMsgType());
            }
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("有一个客户端跳出连接");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    //申请号码
    public boolean askForNum(){
        MessageReq req = new MessageReq();

        System.out.println("现在开始申请号码 ：");

        req.setUser(this.userLogin);
        req.setMsgType(MsgType.ASKFORNUM);


        // 发送一个对象给服务器
        //对象序列化
        try {
            oos.writeObject(req);

            if (ois == null){
                ois = new ObjectInputStream(is);
            }

            MessageResp resp = (MessageResp)ois.readObject();
            if(resp.getMsgType() == MsgType.ASKFORNUM){
                if(resp.getOpResult() == OpResult.ASKFORNUM_OK) {
                    User user2 = resp.getUser();
                    System.out.println("申请号码成功,您可以使用" + user2.getPhoneNum() + "打电话了");
                    return true;
                }else if(resp.getOpResult() == OpResult.ASKFORNUM_EXISTS){
                    User user2 = resp.getUser();
                    System.out.println("申请号码失败,您已经拥有号码"+user2.getPhoneNum()+"直接用它打电话即可");
                    return true;
                }else{
                    System.err.println("申请号码出现未处理的登录结果！" + resp.getOpResult());
                }
            }else{
                System.err.println("出现未处理的消息！" + resp.getMsgType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 话费充值
    public boolean chongZhi(){
        MessageReq req = new MessageReq();

        System.out.println("现在开始充话费==========");
        System.out.println("请输入充值金额");
        req.setUser(this.userLogin);
        req.setMsgType(MsgType.CHONGFEI);
        // 发送一个对象给服务器
        try {
            int value = input.nextInt();
            req.setMoney(value);

            oos.writeObject(req);

            if(ois == null){
                ois = new ObjectInputStream(is);
            }
            MessageResp resp = (MessageResp)ois.readObject();
            if(resp.getMsgType() == MsgType.CHONGFEI){
                if(resp.getOpResult() == OpResult.CHONGZHI_OK){
                    User user2 = resp.getUser();
                    System.out.println("充值成功，您的当前余额" +
                            user2.getMoney() + " 元");
                    return true;
                }else{
                    System.err.println("充值出现未处理的结果！" + resp.getOpResult());
                }
            }else{
                System.err.println("出现未处理的消息！" + resp.getMsgType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 余额查询
    public boolean queryMoney(){
        MessageReq req = new MessageReq();

        System.out.println("现在开始查询余额==========");
        req.setUser(this.userLogin);
        req.setMsgType(MsgType.QUERYMONEY);
        // 发送一个对象给服务器
        try {
            oos.writeObject(req);

            if(ois == null){
                ois = new ObjectInputStream(is);
            }
            MessageResp resp = (MessageResp)ois.readObject();
            if(resp.getMsgType() == MsgType.QUERYMONEY){
                if(resp.getOpResult() == OpResult.QUERYMONEY_OK){
                    User user2 = resp.getUser();
                    System.out.println("查询成功，您的当前余额" +
                            user2.getMoney() + " 元");
                    return true;
                }else{
                    System.err.println("充值出现未处理的结果！" + resp.getOpResult());
                }
            }else{
                System.err.println("出现未处理的消息！" + resp.getMsgType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}


