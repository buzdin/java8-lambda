package lv.jug.java8.serialized;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

class LambdaInvocationR1<T> extends LambdaInvocation {

    private final StepR1<T> step;

    @FunctionalInterface
    public interface StepR1<T> extends java.io.Serializable {
        Object accept(T t);
    }

    public LambdaInvocationR1(StepR1<T> step) {
        super(getSerializedLambda(step));
        this.step = step;
    }

    @Override
    public Optional<Object> execute(Object... args) throws Exception {
        checkArgument(args.length == 1);
        Object result = step.accept((T) args[0]);
        return Optional.of(result);
    }

}