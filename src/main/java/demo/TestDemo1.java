package demo;

import com.bea.core.repackaged.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDemo1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("http://VPS:7777/test.xml");
        context.registerShutdownHook(); // 结束Spring容器，才能调用destroy-method
    }
}
