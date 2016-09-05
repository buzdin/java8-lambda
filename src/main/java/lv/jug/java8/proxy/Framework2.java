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

    public static <T, U, R> StepDefinition<T> call(Function1<T, U, R> function, U value) {
        return proxy -> function.apply(proxy, value);
    }

    interface Function1<T, U, R> {
        R apply(T t, U u);
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
                step.method.invoke(instance, step.args);
            }
        }

    }

}
