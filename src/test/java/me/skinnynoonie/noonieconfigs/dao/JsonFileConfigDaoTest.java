package me.skinnynoonie.noonieconfigs.dao;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class JsonFileConfigDaoTest {

    private static final Path TEMP_CONFIG_FOLDER;
    private static final Gson GSON = new Gson();

    static {
        try {
            TEMP_CONFIG_FOLDER = Files.createTempDirectory("file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void initiate() {
    }

    @Test
    void load() {
    }

    @Test
    void save() {
    }

    @Test
    void isSaved() {
    }

    @Test
    void getConfigFolder() {
    }

    @Test
    void getGson() {
    }
}