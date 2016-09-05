package lv.jug.java8;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

class LambdaInvocationV1<T> extends LambdaInvocation {

    private final StepV1<T> step;

    @FunctionalInterface
    public interface StepV1<T> extends java.io.Serializable {
        Object accept(T t);
    }

    public LambdaInvocationV1(StepV1<T> step) {
        super(getSerializedLambda(step));
        this.step = step;
    }

    @Override
    public Optional<Object> execute(Object... args) throws Exception {
        checkArgument(args.length == 1);
        step.accept((T) args[0]);
        return Optional.empty();
    }

}