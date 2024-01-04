package me.skinnynoonie.noonieconfigs.dao;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface RawConfigRepository<T> {

    /**
     * Initializes this repository.
     * @throws IOException If an I/O exception occurs.
     */
    void initialize() throws IOException;

    /**
     * Loads a config by name. The config must already be saved in this repository to load.
     * @param configName The name of the config to load.
     * @return The config still in the serialized form.
     * @throws IOException If an I/O exception occurs.
     * @throws java.util.NoSuchElementException If the config is not saved.
     * @throws me.skinnynoonie.noonieconfigs.exception.MalformedBodyException If the config body is not valid.
     */
    @NotNull T load(@NotNull String configName) throws IOException;

    /**
     * Saves a config by name and its serialized form.
     * @param configName The name of the config which is saved.
     * @param config The serialized form of the config.
     * @throws IOException If an I/O exception occurs.
     */
    void save(@NotNull String configName, @NotNull T config) throws IOException;

    /**
     * Checks if a config (referred using the config name) is saved within this repository.
     * This will not check if the config is corrupted/malformed.
     * @param configName The name of the config to check.
     * @return true if the config is saved within this repository, otherwise false.
     * @throws IOException If an I/O exception occurs.
     */
    boolean isSaved(@NotNull String configName) throws IOException;

}
