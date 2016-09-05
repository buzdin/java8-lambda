package lv.jug.java8.serialized;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Framework {

    public static ExecutionBuilder framework() {
        return new ExecutionBuilder();
    }

    public static class ExecutionBuilder {

        private final List<StepDefinition> steps = new ArrayList<>();

        ExecutionBuilder addMethodCall(StepDefinition definition) {
            steps.add(definition);
            return this;
        }

        public Execution build() {
            return new Execution(steps);
        }

    }

    public static class StepDefinition {

        private final LambdaInvocation lamdaInvocation;
        private final List<ValueProvider<?>> providers;

        public StepDefinition(LambdaInvocation lambdaInvocation, List<ValueProvider<?>> providers) {
            this.lamdaInvocation = lambdaInvocation;
            this.providers = providers;
        }

        public LambdaInvocation getLambdaInvocation() {
            return lamdaInvocation;
        }

        public Object[] getArgs() {
            return providers.stream().map(this::getValue).toArray();
        }

        private <T> Object getValue(ValueProvider<?> provider) {
            return provider.get();
        }

    }

    public static class Execution {

        private final List<StepDefinition> steps;

        public Execution(List<StepDefinition> steps) {
            this.steps = steps;
        }

        public void go() throws Exception {
            for (StepDefinition step : steps) {
                LambdaInvocation invocation = step.getLambdaInvocation();
                Object[] args = step.getArgs();
                Optional<Object> result = invocation.execute(args);
                if (result.isPresent()) {
                    System.out.println("RESULT : " + result.get());
                }
            }
        }

    }

    public static <T> StepDefinition call(LambdaInvocationV0.TestStepV0 method) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationV0(method), Lists.newArrayList());
    }

    public static <T> StepDefinition call(LambdaInvocationV1.StepV1 method, ValueProvider<T> firstArg) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationV1<>(method), Lists.newArrayList(firstArg));
    }

    public static <T> StepDefinition call(LambdaInvocationR0.StepR0 method) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationR0(method), Lists.newArrayList());
    }

    public static <T> StepDefinition call(LambdaInvocationR1.StepR1<T> method, ValueProvider<T> firstArg) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationR1<>(method), Lists.newArrayList(firstArg));
    }

    public static <T> ValueProvider<T> value(T value) {
        return () -> value;
    }

}
