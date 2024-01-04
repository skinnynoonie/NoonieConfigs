package me.skinnynoonie.noonieconfigs.fallback;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public final class JsonFallbackValueProvider implements FallbackValueProvider<JsonObject> {

    @NotNull
    @Override
    public JsonObject appendFallbackValues(@NotNull JsonObject original, @NotNull JsonObject fallback) {
        Preconditions.checkNotNull(original, "Parameter original is null.");
        Preconditions.checkNotNull(fallback, "Parameter fallback is null.");

        this.recursiveValueReplacement(original, fallback);
        return original;
    }

    private void recursiveValueReplacement(JsonObject original, JsonObject fallback) {
        for (String key : fallback.keySet()) {

            if (!original.has(key)) {
                original.add(key, fallback.get(key));
                continue;
            }

            JsonElement originalValue = original.get(key);
            JsonElement fallbackValue = fallback.get(key);

            // NOT THE SAME AS JsonNull, THIS MEANS MISSING KEY.
            if (originalValue == null) {
                original.add(key, fallbackValue);
                continue;
            }

            if (originalValue.isJsonObject() && fallbackValue.isJsonObject()) {
                this.recursiveValueReplacement(originalValue.getAsJsonObject(), fallbackValue.getAsJsonObject());
                continue;
            }

            if (!this.isSameType(originalValue, fallbackValue)) {
                original.add(key, fallback.get(key));
                continue;
            }

        }
    }

    private boolean isSameType(JsonElement original, JsonElement fallback) {
        if (original.isJsonNull()) {
            return true;
        }

        if (original.isJsonArray() && fallback.isJsonArray()) {
            return true;
        }

        if (original.isJsonPrimitive() && fallback.isJsonPrimitive()) {
            JsonPrimitive primitiveOriginal = (JsonPrimitive) original;
            JsonPrimitive primitiveFallback = (JsonPrimitive) fallback;

            if (primitiveOriginal.isNumber() && primitiveFallback.isNumber()) {
                return true;
            }

            if (primitiveOriginal.isString() && primitiveFallback.isString()) {
                return true;
            }
        }

        return false;
    }

}
