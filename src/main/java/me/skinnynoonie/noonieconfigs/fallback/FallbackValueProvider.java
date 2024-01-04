package me.skinnynoonie.noonieconfigs.fallback;

import org.jetbrains.annotations.NotNull;

public interface FallbackValueProvider<T> {

    @NotNull T appendFallbackValues(@NotNull T original, @NotNull T fallback);

}
