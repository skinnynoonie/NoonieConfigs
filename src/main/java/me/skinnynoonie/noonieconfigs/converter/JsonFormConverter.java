package me.skinnynoonie.noonieconfigs.converter;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import me.skinnynoonie.noonieconfigs.exception.MalformedBodyException;
import org.jetbrains.annotations.NotNull;

public class JsonFormConverter implements RawFormConverter<JsonObject> {

    private final Gson gson;

    public JsonFormConverter() {
        this.gson = new Gson();
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
        } catch (JsonSyntaxException exception) {
            throw new MalformedBodyException(
                    "Class %s cannot be represented using the following JSON object: %s"
                            .formatted(type.getName(), rawFormData.toString()),
                    exception
            );
        }
    }

}
