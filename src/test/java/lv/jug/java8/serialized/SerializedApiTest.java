package lv.jug.java8.serialized;

import org.junit.Test;

import static lv.jug.java8.serialized.Framework.*;

public class SerializedApiTest {

    @Test
    public void shouldRun() throws Exception {
        Example example = new Example();

        Framework.Execution execution = framework()
                .addMethodCall(call(example::sayHello))
                .addMethodCall(call(example::add, value(1)))
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

    }

}
