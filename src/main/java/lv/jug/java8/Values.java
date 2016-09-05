package lv.jug.java8;

public final class Values {

    private Values() {
    }

    public static <T> ValueProvider<T> value(T value) {
        return () -> value;
    }

}
