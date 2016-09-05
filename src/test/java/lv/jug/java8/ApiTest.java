package lv.jug.java8;

import org.junit.Test;

import static lv.jug.java8.Calls.call;
import static lv.jug.java8.Values.value;

public class ApiTest {

    @Test
    public void shouldTest() throws Exception {
        Example example = new Example();

        Execution execution = framework(Example.class)
                .addMethodCall(call(example::sayHello))
                .addMethodCall(call(example::add, value(1)))
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

        public String add(Integer x) {
            System.out.printf("add" + x);
            return "";
        }

    }

}
