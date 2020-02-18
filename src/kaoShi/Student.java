public class Student {
    private String name;
    private int age;
    private boolean gender;
    public void xueXi(){
        System.out.println("姓名："+this.name+"年龄："+this.age+"性别："+(this.isGender(true) == true ? "男":"女"));
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender(boolean b) {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
