package me.skinnynoonie.noonieconfigs.service;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ConfigService<T> {

    /**
     * This will initiate the service.
     * @throws IOException If an I/O exception occurs.
     */
    void initialize() throws IOException;

    /**
     * This method will load a configuration with a class reference.
     * The config class must already be saved within this service.
     * A MalformedBodyException may be thrown if there are missing values in the saved config compared to the config class.
     * See {@link ConfigService#loadWithFallback(T)}.
     * @param configClass The class that will be loaded; must be annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     * @return The config class as an Object; must be annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     * @throws IOException If an I/O exception occurs.
     * @throws me.skinnynoonie.noonieconfigs.exception.MalformedBodyException If the config body is not valid.
     * @throws java.util.NoSuchElementException If the config class is not saved within this service.
     * @throws IllegalStateException If a parameter is not annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     */
    <C extends T> @NotNull C load(@NotNull Class<C> configClass) throws IOException;

    /**
     * This method will load a configuration with an object reference's class.
     * The fallback config class must already be saved within this service.
     * @param fallbackConfig The fallback config which will replace any missing/invalid values in this service's non-volatile storage.
     * @return The fallback config as a config Object; must be annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     * @throws IOException If an I/O exception occurs.
     * @throws me.skinnynoonie.noonieconfigs.exception.MalformedBodyException If the config body is not valid.
     * @throws java.util.NoSuchElementException If the config class is not saved within this service.
     * @throws IllegalStateException If a parameter is not annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     */
    <C extends T> @NotNull C loadWithFallback(@NotNull C fallbackConfig) throws IOException;

    /**
     * This method will save a config to its non-volatile storage.
     * This will also serialize null key pair values.
     * @param config The config to save; must be annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalStateException If a parameter is not annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     */
    void save(@NotNull T config) throws IOException;

    /**
     * Checks if a config (referred using the config name) is saved within this service's non-volatile storage.
     * This will not check if the config is corrupted/malformed.
     * @param configClass The config class to check if it is saved;
     *                    must be annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     * @return true, if the config is saved within this service, otherwise false.
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalStateException If a parameter is not annotated with {@link me.skinnynoonie.noonieconfigs.attribute.Config}.
     */
    boolean isSaved(@NotNull Class<? extends T> configClass) throws IOException;

}
