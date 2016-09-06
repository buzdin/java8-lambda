package lv.jug.java8;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

    public static SerializedLambda serializedLambda(Serializable lambda) {
        final Method method;
        try {
            Class<? extends Serializable> lambdaClass = lambda.getClass();
            method = lambdaClass.getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return (SerializedLambda) method.invoke(lambda);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("Not Valid Method", e);
        }
    }

}
