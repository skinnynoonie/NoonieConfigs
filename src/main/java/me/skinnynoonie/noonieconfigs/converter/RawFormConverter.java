package me.skinnynoonie.noonieconfigs.converter;

import org.jetbrains.annotations.NotNull;

/**
 * This interface represents a converter that converts raw data forms into objects, and vice versa.
 * For example, RawFormConverter<{@link com.google.gson.JsonObject}> will convert JsonObjects into a Java object (vice versa).
 * @param <T> The raw data type this converter will wrap.
 */
public interface RawFormConverter<T> {

    /**
     * This method will take in a nonnull value (object), and convert it into a raw form.
     * Fields that are null will show up in the serialized form.
     * @param object The object to convert into a raw form.
     * @return The object in a raw form.
     */
    T toRawForm(@NotNull Object object);

    /**
     * This method will take in a raw form and convert it into a Java object.
     * @param rawFormData The raw form of an object which is represented by the parameter type.
     * @param type The type the raw form be will convert to.
     * @return An object that is represented by the raw form.
     * @throws me.skinnynoonie.noonieconfigs.exception.MalformedBodyException If the raw form is not valid.
     * This could mean that the raw form does not properly represent the type.
     */
    <C> @NotNull C toObjectForm(@NotNull T rawFormData, @NotNull Class<C> type);

}
