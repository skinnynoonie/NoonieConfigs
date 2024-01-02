package me.skinnynoonie.noonieconfigs.dao;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import me.skinnynoonie.noonieconfigs.exception.MalformedBodyException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JsonFileConfigDao implements RawConfigDao<JsonObject> {

    @NotNull
    public static JsonFileConfigDao newInstance(@NotNull Path configFolder, @NotNull Gson gson) {
        return new JsonFileConfigDao(configFolder, gson);
    }

    @NotNull
    public static JsonFileConfigDao withTypeAdapters(@NotNull Path configFolder, @NotNull Map<Type, Object> typeAdapters) {
        Preconditions.checkNotNull(typeAdapters, "Parameter typeAdapters is null.");

        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting();
        typeAdapters.forEach(gsonBuilder::registerTypeAdapter);
        return new JsonFileConfigDao(configFolder, gsonBuilder.create());
    }

    @NotNull
    public static JsonFileConfigDao createDefault(@NotNull Path configFolder) {
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();
        return new JsonFileConfigDao(configFolder, gson);
    }

    private final Path configFolder;
    private final Gson gson;

    private JsonFileConfigDao(@NotNull Path configFolder, @NotNull Gson gson) {
        Preconditions.checkNotNull(configFolder, "Parameter configFolder is null.");
        Preconditions.checkNotNull(gson, "Parameter gson is null.");

        this.configFolder = configFolder;
        this.gson = gson;
    }

    @Override
    public void initiate() throws IOException {
        Files.createDirectories(this.configFolder);
    }

    @NotNull
    @Override
    public JsonObject load(@NotNull String configName) throws IOException {
        Preconditions.checkNotNull(configName, "Parameter configName is null.");

        Path pathToConfig = this.configFolder.resolve(configName + ".json");
        String configContent = Files.readString(pathToConfig);
        try {
            return this.gson.toJsonTree(configContent).getAsJsonObject();
        } catch (JsonIOException exception) {
            throw new MalformedBodyException(
                    "Error while converting file %s to a JsonObject. Content: %s"
                            .formatted(pathToConfig.toString(), configContent),
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
        return Files.exists(this.configFolder.resolve(configName + ".json"));
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
