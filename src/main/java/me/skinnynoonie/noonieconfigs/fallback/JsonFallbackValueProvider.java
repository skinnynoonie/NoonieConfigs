package me.skinnynoonie.noonieconfigs.fallback;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

public class JsonFallbackValueProvider implements FallbackValueProvider<JsonObject> {

    @Override
    public @NotNull JsonObject appendFallbackValues(@NotNull JsonObject original, @NotNull JsonObject fallback) {
        this.recursiveValueReplacement(original, fallback);
        return original;
    }

    private void recursiveValueReplacement(JsonObject original, JsonObject fallback) {
        System.out.println("hiasdfsdaffsda");
        for (String key : fallback.keySet()) {

            System.out.println(key);

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

            System.out.println(key);
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
