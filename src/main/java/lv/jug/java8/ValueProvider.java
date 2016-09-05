package lv.jug.java8;

import java.io.Serializable;

@FunctionalInterface
public interface ValueProvider<T> extends Serializable {
    T get();
}