package lv.jug.java8.serialized;

import java.util.ArrayList;
import java.util.List;

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
                invocation.execute(args);
            }
        }

    }

}
