package me.skinnynoonie.noonieconfigs;

import com.google.gson.JsonObject;
import me.skinnynoonie.noonieconfigs.converter.JsonFormConverter;
import me.skinnynoonie.noonieconfigs.dao.JsonFileConfigRepository;
import me.skinnynoonie.noonieconfigs.fallback.JsonFallbackValueProvider;
import me.skinnynoonie.noonieconfigs.service.BasicConfigService;
import me.skinnynoonie.noonieconfigs.service.ConfigService;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class DefaultConfigServices {

    @NotNull
    public static <T> ConfigService<T> createJsonConfigService(@NotNull Path configFolderPath) {
        return new BasicConfigService<T, JsonObject>(
                JsonFileConfigRepository.createDefault(configFolderPath),
                new JsonFallbackValueProvider(),
                new JsonFormConverter()
        );
    }

    private DefaultConfigServices() {
        throw new UnsupportedOperationException("Cannot instantiate this class.");
    }

}
