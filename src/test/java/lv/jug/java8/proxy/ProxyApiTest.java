package lv.jug.java8.proxy;

import org.junit.Test;

import static lv.jug.java8.proxy.Framework2.*;

public class ProxyApiTest {

    @Test
    public void shouldRun() throws Exception {
        Execution execution = framework2(Example.class)
                .addMethodCall(call(Example::sayHello))
                .addMethodCall(call(Example::add, 1))
                .build();

        execution.go();
    }

    public static class Example {

        public String sayHello() {
            System.out.println("Hello");
            return "";
        }

        public String add(Integer x) {
            System.out.printf("add" + x);
            return "";
        }

    }

}
