package lv.jug.java8.proxy;

import java.io.Serializable;

@FunctionalInterface
public interface ValueProvider<T> extends Serializable {
    T get();
}