package me.skinnynoonie.noonieconfigs.service;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ConfigService<T> {

    void initialize() throws IOException;

    <C extends T> @NotNull C load(@NotNull Class<C> configClass) throws IOException;

    <C extends T> @NotNull C loadWithFallback(@NotNull C fallbackConfig) throws IOException;

    void save(@NotNull T config) throws IOException;

    boolean isSaved(@NotNull Class<? extends T> configClass) throws IOException;

}
