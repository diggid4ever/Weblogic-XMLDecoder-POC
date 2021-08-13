package demo;

public class Demo1 {
    public Demo1() {
        System.out.println("调用了构造方法");
    }
    private void initDemo() {
        System.out.println("调用了testDemo方法来初始化");
    }

    public void destroyDemo() {
        System.out.println("调用了destroyDemo方法来销毁");
    }

}
