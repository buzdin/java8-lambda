package lv.jug.java8.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RecordingObject implements MethodInterceptor {

    List<MethodCall> recordedCalls = new ArrayList<>();

    public static class MethodCall {
        Method method;
        Object[] args;

        public MethodCall(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }
    }

    @Override
    public Object intercept(Object object, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        recordedCalls.add(new MethodCall(method, args));
        return null;
    }

    public List<MethodCall> getRecordedCalls() {
        return this.recordedCalls;
    }

}