package lv.jug.java8.live;

import lv.jug.java8.Utils;
import org.junit.Test;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Example example = new Example();

        Execution e = framework()
                .addMethodCall(call(example::hello))
                .addMethodCall(call(example::add, 1, 2))
                .build();

        e.run();
    }

    private static MethodBuilder framework() {
        return new MethodBuilder();
    }

    private static class MethodBuilder {

        List<StepDefinition> steps = new ArrayList<>();

        public MethodBuilder addMethodCall(StepDefinition step) {
            steps.add(step);
            return this;
        }

        public Execution build() {
            return new Execution(steps);
        }
    }

    public interface Lambda0<T> extends Serializable {
        void apply();
    }

    public interface Lambda2<U, F> extends Serializable {
        Object apply(U u, F f);
    }

    private static StepDefinition call(Lambda0 lambda) {
        return new StepDefinition(new Lambda0Wrapper(lambda), new Object[] {});
    }

    private static <U, F> StepDefinition call(Lambda2<U, F> lambda, U u, F f) {
        return new StepDefinition(new Lambda2Wrapper(lambda), new Object[] {u, f});
    }

    private static class Execution {

        private final List<StepDefinition> steps;

        public Execution(List<StepDefinition> steps) {
            this.steps = steps;
        }

        void run() throws Exception {
            for (StepDefinition step : steps) {
                LambdaWrapper wrapper = step.getLambda();
                Object[] args = step.getArgs();

                SerializedLambda lambda = wrapper.getLambda();
                System.out.println(lambda.getImplClass() + "::" + lambda.getImplMethodName());
                // running the lambda
                Optional<Object> optional = wrapper.execute(args);

                if (optional.isPresent()) {
                    System.out.println("RESULT -> " + optional.get());
                }
            }
        }
    }

    private static abstract class LambdaWrapper implements LambdaInvocation {

        private final SerializedLambda lambda;

        public LambdaWrapper(SerializedLambda lambda) {
            this.lambda = lambda;
        }

        public SerializedLambda getLambda() {
            return lambda;
        }
    }

    private static class Lambda0Wrapper extends LambdaWrapper {
        private final Lambda0 lambda;

        public Lambda0Wrapper(Lambda0 lambda) {
            super(Utils.serializedLambda(lambda));
            this.lambda = lambda;
        }

        @Override
        public Optional<Object> execute(Object... args) throws Exception {
            lambda.apply();
            return Optional.empty();
        }
    }

    private static class Lambda2Wrapper extends LambdaWrapper {
        private final Lambda2 lambda;

        public Lambda2Wrapper(Lambda2 lambda) {
            super(Utils.serializedLambda(lambda));
            this.lambda = lambda;
        }

        @Override
        public Optional<Object> execute(Object... args) throws Exception {
            Object result = lambda.apply(args[0], args[1]);
            return Optional.of(result);
        }
    }

    interface LambdaInvocation {
        Optional<Object> execute(Object... args) throws Exception;
    }

    private static class StepDefinition {

        private final LambdaWrapper lambda;
        private final Object[] args;

        public StepDefinition(LambdaWrapper invocation, Object[] args) {
            this.lambda = invocation;
            this.args = args;
        }

        public LambdaWrapper getLambda() {
            return lambda;
        }

        public Object[] getArgs() {
            return args;
        }
    }
}
