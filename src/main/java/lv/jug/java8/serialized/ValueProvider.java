package lv.jug.java8.serialized;

import java.io.Serializable;

@FunctionalInterface
public interface ValueProvider<T> extends Serializable {
    T get();
}