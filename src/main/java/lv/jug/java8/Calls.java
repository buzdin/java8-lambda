package lv.jug.java8;

import com.google.common.collect.Lists;

public final class Calls {

    private Calls() {
    }

    public static <T> StepDefinition call(LambdaInvocationV0.TestStepV0 method) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationV0(method), Lists.newArrayList());
    }

    public static <T> StepDefinition call(LambdaInvocationR0.StepR0 method) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationR0(method), Lists.newArrayList());
    }

    public static <T> StepDefinition call(LambdaInvocationR1.StepR1<T> method, DataRecordProvider<T> firstArg) {
        return LambdaInvocation.getStepDefinition(new LambdaInvocationR1(method), Lists.newArrayList(firstArg));
    }

}
