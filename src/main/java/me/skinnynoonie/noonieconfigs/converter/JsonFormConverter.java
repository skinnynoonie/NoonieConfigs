package me.skinnynoonie.noonieconfigs.converter;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.skinnynoonie.noonieconfigs.exception.MalformedBodyException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;

public final class JsonFormConverter implements RawFormConverter<JsonObject> {

    public static JsonFormConverter createDefault() {
        return new JsonFormConverter(new GsonBuilder().serializeNulls().create());
    }

    public static JsonFormConverter withTypeAdapters(@NotNull Map<@NotNull Type, @NotNull Object> typeAdapters) {
        Preconditions.checkNotNull(typeAdapters, "Parameter typeAdapters is null.");

        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        typeAdapters.forEach(gsonBuilder::registerTypeAdapter);
        return new JsonFormConverter(gsonBuilder.create());
    }

    public static JsonFormConverter newInstance(@NotNull Gson gson) {
        return new JsonFormConverter(gson);
    }

    private final Gson gson;

    private JsonFormConverter(@NotNull Gson gson) {
        Preconditions.checkNotNull(gson, "Parameter gson is null.");

        this.gson = gson;
    }

    @Override
    public JsonObject toRawForm(@NotNull Object object) {
        Preconditions.checkNotNull(object, "Parameter object is null.");

        return this.gson.toJsonTree(object).getAsJsonObject();
    }

    @Override
    public <C> @NotNull C toObjectForm(@NotNull JsonObject rawFormData, @NotNull Class<C> type) {
        Preconditions.checkNotNull(rawFormData, "Parameter rawFormData is null.");
        Preconditions.checkNotNull(type, "Parameter type is null.");

        try {
            return this.gson.fromJson(rawFormData, type);
        } catch (JsonParseException exception) {
            throw new MalformedBodyException(
                    "Class %s cannot be represented using the following JSON object: %s"
                            .formatted(type.getName(), rawFormData.toString()),
                    exception
            );
        }
    }

}
