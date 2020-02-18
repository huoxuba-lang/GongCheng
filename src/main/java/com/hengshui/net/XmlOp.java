package com.hengshui.net;

import com.hengshui.pojo.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class XmlOp {

    // 保存用户数据
    public static void saveUserData(){
        try {
            // 打开XML文件===============================
            String n = System.getProperty("user.dir");
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/users.xml");
            Document doc = sr.read(file);


            // 添加所有的用户数据到XML===============================
            Element root = doc.getRootElement();
            // 清空所有用户数据
            root.clearContent();

            Map<String, User> allUser = Singleton.instance().getAllUser();

            // 把所有的用户数据添加到xml中
            Iterator<Map.Entry<String,User>> it =
                    allUser.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<String,User> item = it.next();
                User user = item.getValue();
                Element eleUser = root.addElement("user");
                Element ele1 = eleUser.addElement("name");
                ele1.setText(user.getUserName());
                Element ele2 = eleUser.addElement("pwd");
                ele2.setText(user.getUserPassword());
                Element ele3 = eleUser.addElement("phonenum");
                ele3.setText(user.getPhoneNum());
                Element ele4 = eleUser.addElement("money");
                ele4.setText(user.getMoney() + "");
            }


            // 关闭保存XML===============================
            OutputFormat of = OutputFormat.createPrettyPrint();
            of.setEncoding("UTF-8");
            XMLWriter wr = new XMLWriter(new FileWriter(n + "/src/users.xml"),of);
            wr.write(doc);
            wr.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 读取xml里的所有用户到map
    public static void readUserToMap(){
        // 打开XML文件===============================
        try {
            String n = System.getProperty("user.dir");
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/users.xml");
            Document doc = sr.read(file);

            // 增查删改XML===============================
            Element root = doc.getRootElement();
            // 代表了遍历时的每个用户
            Iterator it = root.elementIterator();
            while(it.hasNext()){
                Element ele = (Element)it.next();

                // 一个用户里的每个节点
                User oneUser = new User();
                Iterator it2 = ele.elementIterator();
                while (it2.hasNext()){
                    Element ele2 = (Element)it2.next();
                    if(ele2.getName().equals("name")){
                        oneUser.setUserName(ele2.getText());
                    }else if(ele2.getName().equals("pwd")){
                        oneUser.setUserPassword(ele2.getText());
                    }else if(ele2.getName().equals("phonenum")){
                        oneUser.setPhoneNum(ele2.getText());
                    }else if(ele2.getName().equals("money")){
                        String str = ele2.getText();
                        // 使用包装类，把一个字符串变成float
                        oneUser.setMoney(Float.parseFloat(str));
                    }
                }
                // 链式写法  所有用户
                Singleton
                        .instance()
                        .getAllUser()
                        .put(oneUser.getUserName(),oneUser);
                // 记录所有手机号
                if(!oneUser.getPhoneNum().equals("")){
                    Singleton
                            .instance()
                            .getAllUser()
                            .put(oneUser.getPhoneNum()
                                    ,oneUser);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 关闭保存XML===============================
    }
}
