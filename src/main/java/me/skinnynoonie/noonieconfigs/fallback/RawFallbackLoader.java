package me.skinnynoonie.noonieconfigs.fallback;

import org.jetbrains.annotations.NotNull;

public interface RawFallbackLoader<T> {

    @NotNull T appendFallbackValues(@NotNull T original, @NotNull T fallback);

}
