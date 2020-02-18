public class XiaoMai extends Plant {
    @Override
    public void shengchan() {
        System.out.println("小麦可以生产粮食");
        if (isSex() == true){
            System.out.println();
        }
    }
}
