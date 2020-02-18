import java.util.InputMismatchException;
import java.util.Scanner;

public class Boss {
    public void kk(Employee a, Employee b){
        if (a.getSalary()>b.getSalary()){
            System.out.println(a.getName());
        }else {
            System.out.println(b.getName());
        }
    }

//    public void jian(){
//        int a = 5;
//        int b = 1;
//        int c = a-b;
//        System.out.println(c);
//    }
//    public void liFang(){
//        int a = 2;
//        int b = a * a * a;
//        System.out.println(b);
//    }
//
//    public void chu(){
//        try {
//            Scanner scanner = new Scanner(System.in);
//            int x,y,z;
//            System.out.println("请输入被除数：");
//            x = scanner.nextInt();
//            System.out.println("请输入除数：");
//            y = scanner.nextInt();
//
//            z= x/y ;
//            System.out.println("整除结果得："+z);
//
//        } catch (InputMismatchException e) {
//            e.getMessage();
//            System.err.println("被除数和除数必须是整数！");
//        }catch (ArithmeticException e) {
//            System.err.println("除数不能为0！");
//        }catch (Exception e) {
//            System.err.println("其他未知异常！");
//        }finally {
//            System.out.println(" ");
//        }
//    }
//
//
//    public int jieCheng(int a ){
//        int n = 1;
//        for (int i = 1; i <= a; i++) {
//            n *= i;
//            System.out.println(n);
//        }
//        return n;
//    }
}
