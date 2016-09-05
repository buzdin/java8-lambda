package lv.jug.java8;

import java.util.List;

public class StepDefinition {

    private final LambdaInvocation lamdaInvocation;
    private final List<ValueProvider<?>> providers;

    public StepDefinition(LambdaInvocation lamdaInvocation, List<ValueProvider<?>> providers) {
        this.lamdaInvocation = lamdaInvocation;
        this.providers = providers;
    }

    public LambdaInvocation getLamdaInvocation() {
        return lamdaInvocation;
    }

    public Object[] getArgs() {
        return providers.stream().map(this::getValue).toArray();
    }

    private <T> Object getValue(ValueProvider<?> provider) {
        return provider.get();
    }

}