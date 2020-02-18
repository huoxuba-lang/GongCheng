package com.hengshui.net;

import com.hengshui.pojo.User;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

public class TcpSocketThread extends Thread {
    private Socket socket;

    public TcpSocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            System.out.println("有一个客户端连接上来了");
            // 接收数据流
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);

            ObjectInputStream ois = new ObjectInputStream(is);
            // 用流给客户的发送对象
            ObjectOutputStream oos = new ObjectOutputStream(os);
            //反复接收客户端的数据
            while (true) {
                //客户端请求信息读取数据     消息类型
                MessageReq req = (MessageReq) ois.readObject();
                if (req.getMsgType() == MsgType.REG) {
                    boolean res = dualReg(req, oos);


                } else if (req.getMsgType() == MsgType.LOGIN) {
                    boolean res = dualLogin(req, oos);

                } else if (req.getMsgType() == MsgType.ASKFORNUM) {
                    boolean res = dualAskForNum(req, oos);

                } else if (req.getMsgType() == MsgType.CHONGFEI) {
                    boolean res = dualChongFei(req, oos);
                } else if (req.getMsgType() == MsgType.QUERYMONEY) {
                    boolean res = dualQueryMoney(req, oos);
                    //成功
                    if (res = true){
                        break;
                    }
                }
            }
            // 关闭流
            ois.close();
            isr.close();
            os.close();
            is.close();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("有一个客户端断开连接了");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // 处理登录逻辑
    public boolean dualLogin(MessageReq req,ObjectOutputStream oos){
        try {
            //  页码   内容
            Map<String,User> allUser = Singleton.instance().getAllUser();
            //判断用户名是否存在
            //客户端请求信息    请求的用户信息   用户名
            String inputName = req.getUser().getUserName();
            //传进去一个key，返回一个key对应的值
            //          页码，返回这一页的内容
            //         用户名，     用户名
            User user = allUser.get(inputName);
            if (user == null){
                //客户端请求信息
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.LOGIN);
                resp.setOpResult( OpResult.LOGIN_OK);
                System.out.println("登录失败,用户名不存在");
                oos.writeObject(resp);
            } else if (req.getUser().getUserName().equals(user.getUserName())
                    && req.getUser().getUserPassword().equals(user.getUserPassword()) ){
                String reply = "登录成功";
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.LOGIN);
                resp.setOpResult( OpResult.LOGIN_OK);
                //把登录的用户信息发送给客户端
                resp.setUser(user);
                System.out.println(reply);
                oos.writeObject(resp);
                return true;
            }else{
                String reply = "登录失败，密码错误";
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.LOGIN);
                resp.setOpResult( OpResult.LOGIN_PWD_ERROR);
                System.out.println(reply);
                oos.writeObject(resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //处理注册逻辑
    public boolean dualReg(MessageReq req,ObjectOutputStream oos){
        try {
            //  页码   内容
            Map<String,User> allUser = Singleton.instance().getAllUser();
            //判断用户名是否存在
            String inputName = req.getUser().getUserName();
            String inputPassword = req.getUser().getUserPassword();
            //传进去一个key，返回一个key对应的值
            //          页码，返回这一页的内容
            //         用户名，     用户名
            User user = allUser.get(inputName);
            //不存在，注册成功，添加到map
            if (user == null){
                User user1 = new User();
                user1.setUserName(inputName);
                user1.setUserPassword(inputPassword);
                allUser.put(inputName,user1);
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.REG);
                resp.setOpResult( OpResult.REG_OK);
                System.out.println("注册成功");
                oos.writeObject(resp);

                // 把数据写入数据库XML
                XmlOp.saveUserData();
            } else{
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.REG);
                resp.setOpResult( OpResult.REG_EXISTS);
                System.out.println("用户名已经存在，请重新注册");
                oos.writeObject(resp);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    //服务器端申请号码处理
    public boolean dualAskForNum(MessageReq req,ObjectOutputStream oos){
        try {
            //  页码   内容
            Map<String,User> allUser = Singleton.instance().getAllUser();
            //判断用户名是否存在
            String inputName = req.getUser().getUserName();

            //传进去一个key，返回一个key对应的值
            //          页码，返回这一页的内容
            //         用户名，     用户名
            User user = allUser.get(inputName);
            //不存在，注册成功，添加到map
            if ( ! user.getPhoneNum().equals("")){
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.ASKFORNUM);
                resp.setOpResult( OpResult.ASKFORNUM_EXISTS);
                resp.setUser(user);
                System.out.println("申请失败，号码已经存在了，不能再申请");
                oos.writeObject(resp);
            } else if(user.getPhoneNum().equals("") ){
                MessageResp resp = new MessageResp();
                resp.setMsgType(MsgType.ASKFORNUM);
                resp.setOpResult( OpResult.ASKFORNUM_OK);
                //把登录的用户信息发送给客户端
                String phoneNum = getPhoneNum();
                user.setPhoneNum(phoneNum);


                User user3 = new User();
                user3.setUserName(user.getUserName());
                user3.setUserPassword(user.getUserPassword());
                user3.setMoney(user.getMoney());
                user3.setPhoneNum(user.getPhoneNum());

                resp.setUser(user3);


                System.out.println("申请成功 新号码 " + user.getPhoneNum() );
                oos.writeObject(resp);
                Map<String,User> allNums = Singleton.instance().getAllNums();
                allNums.put(phoneNum,user);

                // 把数据写入数据库XML
                XmlOp.saveUserData();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    //获得号码算法
    private String getPhoneNum(){
        //随机8位数
        Random random = new Random(System.currentTimeMillis());
        Map<String, User>allNums = Singleton.instance().getAllNums();
        String newNum  = " ";
        while (true) {
            int num = random.nextInt(100000000);
            if (num >= 10000000){
                continue;
            }

            //+139
            newNum = "139" + num;


            //判断是否重复
            // 传进去一个新号号码，得到一个这个号码对应的用户
            User user = allNums.get(newNum);
            if(user == null){
                // 这个号码没人用,跳出整个循环
                break;
            }
        }
        return  newNum;

    }



     //充值处理
    public boolean dualChongFei(MessageReq req,ObjectOutputStream oos){
        try {
            Map<String, User> allUser =
                    Singleton.instance().getAllUser();
            // 判断用户名是否存在
            String inputName = req.getUser().getUserName();

            // 传进去一个key，返回一个key对应的值
            // 传进去一个页码，返回这一页的内容
            // 传进去一个用户名，返回一个用户名对应user
            User user = allUser.get(inputName);

            MessageResp resp = new MessageResp();
            resp.setMsgType(MsgType.CHONGFEI);
            resp.setOpResult( OpResult.CHONGZHI_OK);
            // 给客户充值
            user.setMoney(user.getMoney() + req.getMoney());

            // 防止数值被恶意修改
            User user3 = new User();
            user3.setUserName(user.getUserName());
            user3.setUserPassword(user.getUserPassword());
            user3.setMoney(user.getMoney());
            user3.setPhoneNum(user.getPhoneNum());

            resp.setUser(user3);
            System.out.println("充值成功 充了 " +req.getMoney() + "元，发短信给客户");
            oos.writeObject(resp);

            // 把数据写入数据库XML
            XmlOp.saveUserData();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }

    // 查询余额的处理
    public boolean dualQueryMoney(MessageReq req,ObjectOutputStream oos){
        try {
            Map<String, User> allUser =
                    Singleton.instance().getAllUser();
            // 判断用户名是否存在
            String inputName = req.getUser().getUserName();

            // 传进去一个key，返回一个key对应的值
            // 传进去一个页码，返回这一页的内容
            // 传进去一个用户名，返回一个用户名对应user
            User user = allUser.get(inputName);

            MessageResp resp = new MessageResp();
            resp.setMsgType(MsgType.QUERYMONEY);
            resp.setOpResult( OpResult.QUERYMONEY_OK);

            // 防止数值被恶意修改
            User user3 = new User();
            user3.setUserName(user.getUserName());
            user3.setUserPassword(user.getUserPassword());
            user3.setMoney(user.getMoney());
            user3.setPhoneNum(user.getPhoneNum());

            resp.setUser(user3);
            System.out.println("查询余额成功 ");
            oos.writeObject(resp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }
}