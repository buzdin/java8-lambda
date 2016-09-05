package lv.jug.java8;

import java.io.Serializable;

@FunctionalInterface
public interface DataRecordProvider<T> extends Serializable {
    T get();
}