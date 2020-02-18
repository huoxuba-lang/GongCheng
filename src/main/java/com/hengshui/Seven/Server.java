package com.hengshui.Seven;

import com.hengshui.net.TcpSocketThread;
import com.hengshui.net.XmlOp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        // 启动服务器，监听端口
        try {
            //建立一个服务器Socket(ServerSocket)，指定端口并开始监听
            //服务器监听哪个端口
            ServerSocket ss = new ServerSocket(8800);
            System.out.println("服务器启动成功，开始监听");

            //等待客户端连接，阻塞
            Socket socket = null;
            boolean stop = false;

            // 读取所有的用户到map
            XmlOp.readUserToMap();
            while (true) {
                //使用accept（）方法等待客户端发起通信
                socket = ss.accept();
                if(stop == true){
                    break;
                }
                TcpSocketThread t1 = new TcpSocketThread(socket);
                t1.start();
            }

            //关闭流
            socket.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
