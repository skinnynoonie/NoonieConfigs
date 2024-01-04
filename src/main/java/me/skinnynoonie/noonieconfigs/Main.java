package me.skinnynoonie.noonieconfigs;

import me.skinnynoonie.noonieconfigs.service.ConfigService;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path configFolderPath = Path.of(System.getProperty("user.dir") + "/configs");
        ConfigService<Object> configService = DefaultConfigServices.createJsonConfigService(configFolderPath);
    }
}