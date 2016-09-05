package lv.jug.java8;

import org.junit.Test;

import static lv.jug.java8.Calls.*;

public class ApiTest {

    @Test
    public void shouldTest() throws Exception {
        Example example = new Example();
        Execution execution = framework(Example.class)
                .addMethodCall(call(example::sayHello))
                .addMethodCall(call(example::add, 2))
                .build();

        execution.go();
    }

    private ExecutionBuilder framework(Class<?> type) {
        return new ExecutionBuilder(type);
    }

    public static class Example {

        public String sayHello() {
            System.out.println("Hello");
            return "";
        }

        public Integer add(Integer x) {
            return x + x;
        }

    }

}
