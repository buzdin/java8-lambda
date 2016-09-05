package lv.jug.java8.serialized;

import org.junit.Test;

import static lv.jug.java8.serialized.Calls.call;
import static lv.jug.java8.serialized.Framework.framework;
import static lv.jug.java8.serialized.Values.value;

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
            return "";
        }

        public String add(Integer x) {
            System.out.printf("add" + x);
            return "";
        }

    }

}
