package com.hengshui;

import static org.junit.Assert.assertTrue;

import com.sun.corba.se.spi.activation.Server;
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
import java.util.Iterator;



/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        try {
            String n = System.getProperty("user.dir");
//            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/student.xml");
            Document doc = sr.read(file);

            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();

            while (it.hasNext()){
                Element ele = (Element)it.next();

                Attribute attrName = ele.attribute("students");
//                String oneName = attrName.getValue();
//                System.out.println(oneName);
                Iterator it2 = ele.elementIterator();
                while (it2.hasNext()){
                    Element ele2 = (Element)it2.next();
//                    Attribute attr = ele2.attribute("name");
//                    String c = attr.getValue();                   //通过节点得到属性名称
//                    String c = ele2.getStringValue(); // 1
                    String c = ele2.getText();          // 2
                    System.out.println(c);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void tianJia(){
        try {
            String n = System.getProperty("user.dir");
            System.out.println(n);
            SAXReader sr = new SAXReader();
            File file = new File(n + "/src/student.xml");
            Document doc = sr.read(file);


            Element phoneInfo = doc.getRootElement();
            Element eleBrand = phoneInfo.addElement("student");
            eleBrand.addAttribute("name","学生");
            Element ele1 = eleBrand.addElement("name");
            ele1.setText("张三");
            Element ele2 = eleBrand.addElement("age");
            ele2.setText("18");
            Element ele3 = eleBrand.addElement("school");
            ele3.setText("人民大学");

            Element eleBran = phoneInfo.addElement("student");
            eleBran.addAttribute("name","学生");
            Element el1 = eleBran.addElement("name");
            el1.setText("李四");
            Element el2 = eleBran.addElement("age");
            el2.setText("19");
            Element el3 = eleBran.addElement("school");
            el3.setText("浙江大学");

            OutputFormat of = OutputFormat.createPrettyPrint();
            of.setEncoding("UTF-8");
            XMLWriter wr = new XMLWriter(new FileWriter(n + "/src/syudent.xml"),of);
            wr.write(doc);
            wr.close();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
