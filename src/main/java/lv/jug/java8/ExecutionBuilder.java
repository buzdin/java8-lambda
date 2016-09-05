package lv.jug.java8;

import java.util.ArrayList;
import java.util.List;

public class ExecutionBuilder {

    private final Class<?> type;
    private final List<StepDefinition> steps = new ArrayList<>();

    public ExecutionBuilder(Class<?> type) {

        this.type = type;
    }

    ExecutionBuilder addMethodCall(StepDefinition definition) {
        steps.add(definition);
        return this;
    }

    public Execution build() {
        return new Execution(type, steps);
    }

}
