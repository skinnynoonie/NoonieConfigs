package me.skinnynoonie.noonieconfigs.dao;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

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
    void newInstance_throwsExceptions() {
        assertThrows(NullPointerException.class, () -> JsonFileConfigDao.newInstance(null, null));
        assertThrows(NullPointerException.class, () -> JsonFileConfigDao.newInstance(null, GSON));
        assertThrows(NullPointerException.class, () -> JsonFileConfigDao.newInstance(TEMP_CONFIG_FOLDER, null));
        assertDoesNotThrow(() -> JsonFileConfigDao.newInstance(TEMP_CONFIG_FOLDER, GSON));
    }

    @Test
    void withTypeAdapters_throwsExceptions() {
    }

    @Test
    void createDefault_doesNotThrow() {
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