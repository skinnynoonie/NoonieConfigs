package me.skinnynoonie.noonieconfigs.service;

import me.skinnynoonie.noonieconfigs.attribute.Configurable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ConfigService<T extends Configurable> {

    @NotNull
    <C extends T> C load(@NotNull Class<C> configClass) throws IOException;

    @NotNull
    <C extends T> C loadWithFallback(@NotNull C fallbackConfig) throws IOException;

    void save(@NotNull T config) throws IOException;

    boolean isSaved(@NotNull Class<T> configClass) throws IOException;

}
