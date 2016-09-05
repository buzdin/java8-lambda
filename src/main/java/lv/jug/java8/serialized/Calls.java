package lv.jug.java8.serialized;

import com.google.common.collect.Lists;
import lv.jug.java8.serialized.Framework.StepDefinition;

public final class Calls {

    private Calls() {
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

}
