package lv.jug.java8.complete;

import lv.jug.java8.RecordingObject;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Playground {

    public static class Example {

        public Example() {
        }

        public void hello() {
            throw new RuntimeException();
            //System.out.println("Hello");
        }
        public Integer add(Integer x, Integer y) {
            int result = x + y;
            System.out.println("ADD: " + result);
            return result;
        }

    }

    @Test
    public void testApi() throws Exception {
        Magic magic = framework(Example.class)
                .addStep(call(Example::hello))
                .addStep(call(Example::add, 1, 2))
                .addStep(call(Example::add, 2, 3))
                .build();

        magic.happen();
    }

    private <T> StepBuilder<T> framework(Class<T> type) {
        return new StepBuilder<>(type);
    }


    private class Magic<T> {

        private Class<T> type;
        private List<StepDefinition<T>> definitions;

        public Magic(Class<T> type, List<StepDefinition<T>> definitions) {
            this.type = type;
            this.definitions = definitions;
        }

        public void happen() throws Exception {
            for (StepDefinition definition : definitions) {

                RecordingObject recordingObject = new RecordingObject();
                T proxy = (T) Enhancer.create(type, recordingObject);
                definition.apply(proxy);

                List<RecordingObject.MethodCall> recordedCalls = recordingObject.getRecordedCalls();
                for (RecordingObject.MethodCall recordedCall : recordedCalls) {
                    System.out.println(recordedCall.method);
                    System.out.println(Arrays.toString(recordedCall.args));
                }
            }
        }
    }

    private class StepBuilder<T> {

        List<StepDefinition<T>> definitions = new ArrayList<>();
        private Class<T> type;

        public StepBuilder(Class<T> type) {
            this.type = type;
        }

        StepBuilder<T> addStep(StepDefinition<T> definition) {
            definitions.add(definition);
            return this;
        }
        Magic<T> build() {
            return new Magic<>(type, definitions);
        }
    }

    private interface StepDefinition<T> {
        void apply(T proxy);
    }

    public <T> StepDefinition<T> call(final Lambda0<T> lambda) {
        return proxy -> lambda.apply(proxy);
    }

    public <T, R, F, U> StepDefinition<T> call(final Lambda2<T, R, F, U> lambda, F f, U u) {
        return proxy -> lambda.apply(proxy, f, u);
    }

    @FunctionalInterface
    private interface Lambda0<T> extends Serializable {
        void apply(T t);
    }

    @FunctionalInterface
    private interface Lambda2<T, R, F, U> extends Serializable {
        R apply(T t, F f, U u);
    }

}
