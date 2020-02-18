package com.hengshui;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Dom4jReadExmple {
    /**
     * 遍历整个XML文件，获取所有节点的值与其属性的值，并放入HashMap中
     * @param  filename String 待遍历的XML文件（相对路径或者绝对路径）
     * @param  hm  HashMap存放遍历结果
     */
    @Test
    public void testDom4jRead(){
        try {
            // 打开XML文件===============================
            String n = System.getProperty("user.dir");
            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/phone.xml");
            Document doc = sr.read(file);

            // 增查删改XML===============================
            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();
            while(it.hasNext()){
                Element ele = (Element)it.next();
                Attribute attrName = ele.attribute("name");
                String oneName = attrName.getValue();
                System.out.println(oneName);

                Iterator it2 = ele.elementIterator();
                while (it2.hasNext()){
                    Element ele2 = (Element)it2.next();
                    Attribute attr = ele2.attribute("model");
                    String nameBrand = attr.getValue();
                    System.out.println("  " + nameBrand +
                            ele2.getText());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testDom4jAdd(){
        try {
            // 打开XML文件===============================
            String n = System.getProperty("user.dir");
            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/phone.xml");
            Document doc = sr.read(file);

            // 操作XML文件===============================
            Element root = doc.getRootElement();
            Element eleBrand = root.addElement("Brand");
            eleBrand.addAttribute("name","OPPO");
            Element ele1 = eleBrand.addElement("Type");
            ele1.addAttribute("model","R17");
            ele1.setText("2333");

            // 关闭保存XML===============================
            OutputFormat of = OutputFormat.createPrettyPrint();
            of.setEncoding("UTF-8");
            XMLWriter wr = new XMLWriter(new FileWriter(n + "/src/phone.xml"),of);
            wr.write(doc);
            wr.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDom4jUpdate() {
        try {
            // 打开XML文件===============================
            String n = System.getProperty("user.dir");
            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/phone.xml");
            Document doc = sr.read(file);
            Element root = doc.getRootElement();

            // 操作XML文件===============================
            Iterator it = root.elementIterator();
            while(it.hasNext()) {
                Element ele = (Element) it.next();
                Attribute att = ele.attribute("name");
                if (att.getValue().equals("HUAWEI")) {
                    Iterator it2 = ele.elementIterator();
                    while (it2.hasNext()) {
                        Element ele2 = (Element) it2.next();
                        Attribute att2 = ele2.attribute("model");
                        if (att2.getValue().equals("P30")) {
                            // 修改节点的值
                            ele2.setText("4555");
                            // 修改属性的值
                            att2.setValue("P30");
                        }
                    }
                }
            }

            // 关闭保存XML===============================
            OutputFormat of = OutputFormat.createPrettyPrint();
            of.setEncoding("UTF-8");
            XMLWriter wr = new XMLWriter(new FileWriter(n + "/src/phone.xml"),of);
            wr.write(doc);
            wr.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDom4jDelete() {
        try {
            // 打开XML文件===============================
            String n = System.getProperty("user.dir");
            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/phone.xml");
            Document doc = sr.read(file);
            Element root = doc.getRootElement();

            // 操作XML文件===============================
            Iterator it = root.elementIterator();
            while(it.hasNext()) {
                Element ele = (Element) it.next();
                Attribute att = ele.attribute("name");
                if(att.getValue().equals("HUAWEI")){
                    // 删除一个节点，元素
                    ele.getParent().remove(ele);
                }
            }

            // 关闭保存XML===============================
            OutputFormat of = OutputFormat.createPrettyPrint();
            of.setEncoding("UTF-8");
            XMLWriter wr = new XMLWriter(new FileWriter(n + "/src/phone.xml"),of);
            wr.write(doc);
            wr.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

