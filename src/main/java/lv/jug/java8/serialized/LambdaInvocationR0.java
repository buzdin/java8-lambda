package lv.jug.java8.serialized;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

class LambdaInvocationR0 extends LambdaInvocation {

    private final StepR0 step;

    @FunctionalInterface
    public interface StepR0 extends java.io.Serializable {
        Object accept();
    }

    public LambdaInvocationR0(StepR0 step) {
        super(getSerializedLambda(step));
        this.step = step;
    }

    @Override
    public Optional<Object> execute(Object... args) throws Exception {
        checkArgument(args.length == 0);
        Object result = step.accept();
        return Optional.of(result);
    }
}