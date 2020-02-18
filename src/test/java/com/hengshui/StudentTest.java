package com.hengshui;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void getXueHao() {
        List<Student>stus = new ArrayList<>();
        Student s1 = new Student(1,50);
        stus.add(s1);
        Student s2 = new Student(3,90);
        stus.add(s2);
        Student s3 = new Student(2,30);
        stus.add(s3);
        Collections.sort(stus);
        for (Student stu:stus) {
            System.out.println(stu.toString());
        }
    }
}