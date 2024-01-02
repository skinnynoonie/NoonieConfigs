package me.skinnynoonie.noonieconfigs.fallback;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class JsonFallbackAppender implements RawFallbackAppender<JsonObject> {

    @Override
    public @NotNull JsonObject appendFallbackValues(@NotNull JsonObject original, @NotNull JsonObject fallback) {
        // Do some recursion blah blah blah ezpz
        return original;
    }

}
