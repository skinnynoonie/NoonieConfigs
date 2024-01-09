package me.skinnynoonie.noonieconfigs.dao;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import me.skinnynoonie.noonieconfigs.exception.MalformedBodyException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public final class JsonFileConfigRepository implements RawConfigRepository<JsonObject> {

    @NotNull
    public static JsonFileConfigRepository newInstance(@NotNull Path configFolder, @NotNull Gson gson) {
        return new JsonFileConfigRepository(configFolder, gson);
    }

    @NotNull
    public static JsonFileConfigRepository createDefault(@NotNull Path configFolder) {
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        return new JsonFileConfigRepository(configFolder, gson);
    }

    private final Path configFolder;
    private final Gson gson;

    private JsonFileConfigRepository(@NotNull Path configFolder, @NotNull Gson gson) {
        Preconditions.checkNotNull(configFolder, "Parameter configFolder is null.");
        Preconditions.checkNotNull(gson, "Parameter gson is null.");

        this.configFolder = configFolder;
        this.gson = gson;
    }

    @Override
    public void initialize() throws IOException {
        Files.createDirectories(this.configFolder);
    }

    @NotNull
    @Override
    public JsonObject load(@NotNull String configName) throws IOException {
        Preconditions.checkNotNull(configName, "Parameter configName is null.");

        if (!this.isSaved(configName)) {
            throw new NoSuchElementException("Config named " + configName + " does not exist and thus cannot be loaded.");
        }

        Path pathToConfig = this.configFolder.resolve(configName + ".json");
        String configContent = new String(Files.readAllBytes(pathToConfig));
        try {
            return JsonParser.parseString(configContent).getAsJsonObject();
        } catch (JsonParseException exception) {
            throw new MalformedBodyException(
                    "Error while converting file " + pathToConfig + " to a JsonObject. Content: " + configContent,
                    exception
            );
        }
    }

    @Override
    public void save(@NotNull String configName, @NotNull JsonObject config) throws IOException {
        Preconditions.checkNotNull(configName, "Parameter configName is null.");
        Preconditions.checkNotNull(config, "Parameter config is null.");

        Path pathToConfig = this.configFolder.resolve(configName + ".json");
        PrintWriter printWriter = new PrintWriter(pathToConfig.toFile());
        printWriter.print(this.gson.toJson(config));
        printWriter.close();
    }

    @Override
    public boolean isSaved(@NotNull String configName) throws IOException {
        try {
            return Files.exists(this.configFolder.resolve(configName + ".json"));
        } catch (InvalidPathException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @NotNull
    public Path getConfigFolder() {
        return this.configFolder;
    }

    @NotNull
    public Gson getGson() {
        return this.gson;
    }

}
