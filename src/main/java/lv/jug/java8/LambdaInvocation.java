package lv.jug.java8;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

abstract class LambdaInvocation implements MethodInvocation {

    private final Class<?> type;
    private final Method method;

    LambdaInvocation(SerializedLambda lambda) {
        type = parseClass(lambda.getImplClass()); //todo logic in constructor to minimize code in extending classes. Replace by static create()?
        method = findMethodByName(type, lambda.getImplMethodName()); //todo works by id. need method signature
    }

    private Method findMethodByName(Class<?> type, String methodName) {
        return Arrays.stream(type.getMethods())
                .filter((m) -> methodName.equals(m.getName()))
                .findFirst()
                .get();
    }

    private static Class<?> parseClass(String className) {
        try {
            String testStepClass = className.replace("/", ".").trim(); //todo improve
            return Class.forName(testStepClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Not Valid Class", e);
        }
    }

    protected Class<?> getStepClass() {
        return type;
    }

    protected Method getMethod() {
        return method;
    }

    protected static SerializedLambda getSerializedLambda(Serializable lambda) {
        final Method method;
        try {
            method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return (SerializedLambda) method.invoke(lambda);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("Not Valid Method", e);
        }
    }

    static StepDefinition getStepDefinition(LambdaInvocation lamdaInvocation, List<DataRecordProvider<?>> args) {
        return new StepDefinition(lamdaInvocation, args);
    }

}