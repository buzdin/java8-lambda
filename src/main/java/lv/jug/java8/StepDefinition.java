package lv.jug.java8;

import java.util.List;

public class StepDefinition {

    private final LambdaInvocation lamdaInvocation;
    private final List<DataRecordProvider<?>> args;

    public StepDefinition(LambdaInvocation lamdaInvocation, List<DataRecordProvider<?>> args) {
        this.lamdaInvocation = lamdaInvocation;
        this.args = args;
    }

    public LambdaInvocation getLamdaInvocation() {
        return lamdaInvocation;
    }

    public List<DataRecordProvider<?>> getArgs() {
        return args;
    }

}
