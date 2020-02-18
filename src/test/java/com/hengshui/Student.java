package com.hengshui;

public class Student implements Comparable{
    private int xueHao;
    private int score;

    public int getXueHao() {
        return xueHao;
    }

    public void setXueHao(int xueHao) {
        this.xueHao = xueHao;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Student(int xueHao, int score) {
        this.xueHao = xueHao;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        Student student = (Student) o;
        //如果这个学生的学号大于传入学生的学号
        if (this.score > student.getScore()){
            return 1;            //自己大
        }else if (this.score < student.getScore()){
            return -1;           //对方大
        }else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "xueHao=" + xueHao +
                ", score=" + score +
                '}';
    }
}
