package me.skinnynoonie.noonieconfigs.fallback;

import org.jetbrains.annotations.NotNull;

public interface FallbackValueProvider<T> {

    /**
     * This method will replace any missing or invalid values using a fallback reference.
     * For example, if the fallback value has a String and the original value has an Integer,
     * the fallback value will replace the original value.
     * If a fallback value and original value are of the same type, nothing will be replaced.
     * If the original value is null, nothing will be replaced, however, unexpected behavior may happen with primitives
     * (depending on the implementation).
     * @param original The original data. This will be modified and returned again (or cloned and modified depending on mutability).
     * @param fallback The fallback data to replace missing or invalid values of the original data.
     * @return The original data but modified to ensure all values are correct.
     */
    @NotNull T appendFallbackValues(@NotNull T original, @NotNull T fallback);

}
