package me.skinnynoonie.noonieconfigs;

import me.skinnynoonie.noonieconfigs.config.TestConfig;
import me.skinnynoonie.noonieconfigs.converter.JsonFormConverter;
import me.skinnynoonie.noonieconfigs.dao.JsonFileConfigRepository;
import me.skinnynoonie.noonieconfigs.fallback.JsonFallbackValueProvider;
import me.skinnynoonie.noonieconfigs.service.BasicConfigService;
import me.skinnynoonie.noonieconfigs.service.ConfigService;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path configFolderPath = Path.of(System.getProperty("user.dir") + "/configs");
        ConfigService<Object> configService = new BasicConfigService<>(
                JsonFileConfigRepository.createDefault(configFolderPath),
                new JsonFallbackValueProvider(),
                new JsonFormConverter()
        );
        configService.initialize();

        TestConfig config = new TestConfig();
        if (configService.isSaved(TestConfig.class)) {
            config = configService.loadWithFallback(config);
        }
        configService.save(config);
    }
}