package me.skinnynoonie.noonieconfigs.service;

import com.google.common.base.Preconditions;
import me.skinnynoonie.noonieconfigs.attribute.Config;
import me.skinnynoonie.noonieconfigs.attribute.Configurable;
import me.skinnynoonie.noonieconfigs.converter.RawFormConverter;
import me.skinnynoonie.noonieconfigs.dao.RawConfigDao;
import me.skinnynoonie.noonieconfigs.fallback.RawFallbackLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BasicConfigService<ConfigType extends Configurable, RawFormType> implements ConfigService<ConfigType> {

    private final RawConfigDao<RawFormType> rawConfigDao;
    private final RawFallbackLoader<RawFormType> rawFallbackLoader;
    private final RawFormConverter<RawFormType> rawFormConverter;

    public BasicConfigService(@NotNull RawConfigDao<RawFormType> rawConfigDao,
                              @NotNull RawFallbackLoader<RawFormType> rawFallbackLoader,
                              @NotNull RawFormConverter<RawFormType> rawFormConverter) {
        Preconditions.checkNotNull(rawConfigDao, "Parameter rawConfigDao is null.");
        Preconditions.checkNotNull(rawFallbackLoader, "Parameter rawFallbackLoader is null.");
        Preconditions.checkNotNull(rawFormConverter, "Parameter rawFormConverter is null.");

        this.rawConfigDao = rawConfigDao;
        this.rawFallbackLoader = rawFallbackLoader;
        this.rawFormConverter = rawFormConverter;
    }

    @NotNull
    @Override
    public <C extends ConfigType> C load(@NotNull Class<C> configClass) throws IOException {
        Preconditions.checkNotNull(configClass, "Parameter configClass is null.");

        RawFormType rawFormConfig = this.rawConfigDao.load(this.getConfigName(configClass));
        return this.rawFormConverter.toObjectForm(rawFormConfig, configClass);
    }

    @NotNull
    @Override
    public <C extends ConfigType> C loadWithFallback(@NotNull C fallbackConfig) throws IOException {
        Preconditions.checkNotNull(fallbackConfig, "Parameter configClass is null.");

        Class<C> configClass = (Class<C>) fallbackConfig.getClass();
        RawFormType rawFormConfig = this.rawConfigDao.load(this.getConfigName(configClass));
        RawFormType rawFormFallbackConfig = this.rawFormConverter.toRawForm(fallbackConfig);
        RawFormType rawFormUpdated = this.rawFallbackLoader.appendFallbackValues(rawFormConfig, rawFormFallbackConfig);
        return this.rawFormConverter.toObjectForm(rawFormUpdated, configClass);
    }

    @Override
    public void save(@NotNull ConfigType config) throws IOException {
        Preconditions.checkNotNull(config, "Parameter config is null.");

        this.rawConfigDao.save(this.getConfigName(config.getClass()), this.rawFormConverter.toRawForm(config));
    }

    @Override
    public boolean isSaved(@NotNull Class<ConfigType> configClass) throws IOException {
        Preconditions.checkNotNull(configClass, "Parameter configClass is null.");

        return this.rawConfigDao.isSaved(this.getConfigName(configClass));
    }

    private String getConfigName(Class<?> configClass) {
        Preconditions.checkState(
                configClass.isAnnotationPresent(Config.class),
                "Class %s is not annotated with Config.".formatted(configClass.getName())
        );
        return configClass.getAnnotation(Config.class).name();
    }

}
