package lv.jug.java8.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Framework2 {

    public static <T> ExecutionBuilder framework2(Class<T> type) {
        return new ExecutionBuilder<>(type);
    }

    public static class ExecutionBuilder<T> {

        private List<RecordingObject.MethodCall> steps = new ArrayList<>();
        private Class<T> type;

        public ExecutionBuilder(Class<T> type) {
            this.type = type;
        }

        ExecutionBuilder<T> addMethodCall(StepDefinition<T> definition) {
            RecordingObject recorder = new RecordingObject();
            T proxy = (T) Enhancer.create(type, recorder);
            definition.apply(proxy);
            List<RecordingObject.MethodCall> calls = recorder.getRecordedCalls();
            steps.addAll(calls);
            return this;
        }

        public Execution<T> build() {
            return new Execution<>(type, steps);
        }

    }

    @FunctionalInterface
    public interface StepDefinition<T> {
        void apply(T proxy);
    }

    public static <T, U> StepDefinition<T> call(Function<T, U> function) {
        return function::apply;
    }

    public static <T, U, R> StepDefinition<T> call(Function1<T, U, R> function, ValueProvider<U> value1) {
        return proxy -> function.apply(proxy, value1.get());
    }

    public static <T, U, V, R> StepDefinition<T> call(Function2<T, U, V, R> function, ValueProvider<U> value1, ValueProvider<V> value2) {
        return proxy -> function.apply(proxy, value1.get(), value2.get());
    }

    interface Function1<T, U, R> {
        R apply(T t, U u);
    }

    interface Function2<T, U, V, R> {
        R apply(T t, U u, V v);
    }


    public static class Execution<T> {

        private final Class<T> type;
        private final List<RecordingObject.MethodCall> steps;

        public Execution(Class<T> type, List<RecordingObject.MethodCall> steps) {
            this.type = type;
            this.steps = steps;
        }

        public void go() throws Exception {
            T instance = type.newInstance();
            for (RecordingObject.MethodCall step : steps) {
                Object result = step.method.invoke(instance, step.args);
                System.out.println("RESULT : " + result);
            }
        }

    }

    public static <T> ValueProvider<T> value(T value) {
        return () -> value;
    }

}
