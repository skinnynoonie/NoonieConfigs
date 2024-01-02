package me.skinnynoonie.noonieconfigs.dao;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface RawConfigDao<T> {

    void initiate() throws IOException;

    @NotNull T load(@NotNull String configName) throws IOException;

    void save(@NotNull String configName, @NotNull T config) throws IOException;

    boolean isSaved(@NotNull String configName) throws IOException;

}
