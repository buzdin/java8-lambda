package lv.jug.java8.proxy;

import org.junit.Test;

import static lv.jug.java8.proxy.Framework2.*;

public class ProxyApiTest {

    @Test
    public void shouldRun() throws Exception {
        Execution execution = framework2(Example.class)
                .addMethodCall(call(Example::sayHello))
                .addMethodCall(call(Example::add, value(1)))
                .addMethodCall(call(Example::minus, value(1), value(1)))
                .build();

        execution.go();
    }

    public static class Example {

        public String sayHello() {
            System.out.println("Hello");
            return "1";
        }

        public String add(Integer x) {
            System.out.println("add" + x);
            return "2";
        }

        public Integer minus(Integer x, Integer y) {
            return x - y;
        }

    }

}
