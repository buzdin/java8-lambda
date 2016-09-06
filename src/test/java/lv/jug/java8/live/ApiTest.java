package lv.jug.java8.live;

import lv.jug.java8.RecordingObject;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiTest {

    public static class Example {
        public void hello() {
            System.out.println("Hello, World!");
        }
        public Integer add(Integer x, Integer y) {
            return x + y;
        }
    }

    @Test
    public void demo() throws Exception {
        Execution e = framework(Example.class)
                .addMethodCall(call(Example::hello))
                .addMethodCall(call(Example::add, 1, 2))
                .build();

        e.run();
    }

    private static <T> MethodBuilder<T> framework(Class<T> type) {
        return new MethodBuilder<>(type);
    }

    private static class MethodBuilder<T> {

        private final Class<T> type;

        List<RecordingObject.MethodCall> steps = new ArrayList<>();

        public MethodBuilder(Class<T> type) {
            this.type = type;
        }

        public MethodBuilder addMethodCall(StepDefinition<T> step) {
            RecordingObject recordingObject = new RecordingObject();
            Object proxy = Enhancer.create(type, recordingObject);
            step.apply((T) proxy);
            List<RecordingObject.MethodCall> recordedCalls = recordingObject.getRecordedCalls();
            steps.addAll(recordedCalls);
            return this;
        }

        public Execution<T> build() {
            return new Execution<T>(type, steps);
        }
    }

    public interface Lambda0<T> extends Serializable {
        void apply(T t);
    }

    public interface Lambda2<T, U, V, R> extends Serializable {
        R apply(T t, U u, V v);
    }

    private static <T> StepDefinition<T> call(Lambda0<T> lambda) {
        return lambda::apply;
    }

    private static <T, U, V, R> StepDefinition call(Lambda2<T, U, V, R> lambda, U u, V v) {
        return (StepDefinition<T>) proxy -> lambda.apply(proxy, u, v);
    }

    private static class Execution<T> {

        private final Class<T> type;
        private final List<RecordingObject.MethodCall> steps;

        public Execution(Class<T> type, List<RecordingObject.MethodCall> steps) {
            this.type = type;
            this.steps = steps;
        }

        void run() throws Exception {
            for (RecordingObject.MethodCall step : steps) {
                System.out.println(step.method + " :: " + Arrays.toString(step.args));
                T instance = type.newInstance();
                Object result = step.method.invoke(instance, step.args);
                System.out.println("RESULT : " + result);
            }
        }
    }

    private interface StepDefinition<T> {
        void apply(T proxy);
    }
}
