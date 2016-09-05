package lv.jug.java8;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

class LambdaInvocationV0 extends LambdaInvocation {

    private final TestStepV0 step;

    @FunctionalInterface
    public interface TestStepV0 extends java.io.Serializable {
        void accept();
    }

    public LambdaInvocationV0(TestStepV0 step) {
        super(getSerializedLambda(step));
        this.step = step;
    }

    @Override
    public Optional<Object> execute(Object... args) throws Exception {
        checkArgument(args.length == 0);
        step.accept();
        return Optional.empty();
    }
}