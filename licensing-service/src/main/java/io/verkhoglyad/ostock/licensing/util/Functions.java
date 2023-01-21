package io.verkhoglyad.ostock.licensing.util;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Functions {

    private Functions() {}

    public static <T> Function<T, T> peek(Consumer<T> handler) {
        return it -> {
            handler.accept(it);
            return it;
        };
    }
}
