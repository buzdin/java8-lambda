package lv.jug.java8;

import java.lang.reflect.Method;
import java.util.List;

public final class Execution {

    private final Class<?> type;
    private final List<StepDefinition> steps;

    public Execution(Class<?> type, List<StepDefinition> steps) {
        this.type = type;
        this.steps = steps;
    }

    public void go() throws Exception {
        Object instance = type.newInstance();
        for (StepDefinition step : steps) {
            Method method = step.getLamdaInvocation().getMethod();
            method.invoke(instance, step.getArgs().toArray());
        }
    }

}
