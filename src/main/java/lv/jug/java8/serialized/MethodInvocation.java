package lv.jug.java8.serialized;

import java.util.Optional;

public interface MethodInvocation {

    Optional<Object> execute(Object... args) throws Exception;

}
