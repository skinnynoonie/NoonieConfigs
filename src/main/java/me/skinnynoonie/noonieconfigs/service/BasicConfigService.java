package me.skinnynoonie.noonieconfigs.service;

import com.google.common.base.Preconditions;
import me.skinnynoonie.noonieconfigs.attribute.Config;
import me.skinnynoonie.noonieconfigs.converter.RawFormConverter;
import me.skinnynoonie.noonieconfigs.dao.RawConfigRepository;
import me.skinnynoonie.noonieconfigs.fallback.FallbackValueProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BasicConfigService<ConfigType, RawFormType> implements ConfigService<ConfigType> {

    private final RawConfigRepository<RawFormType> rawConfigRepository;
    private final FallbackValueProvider<RawFormType> fallbackValueProvider;
    private final RawFormConverter<RawFormType> rawFormConverter;

    public BasicConfigService(@NotNull RawConfigRepository<RawFormType> rawConfigRepository,
                              @NotNull FallbackValueProvider<RawFormType> fallbackValueProvider,
                              @NotNull RawFormConverter<RawFormType> rawFormConverter) {
        Preconditions.checkNotNull(rawConfigRepository, "Parameter rawConfigRepository is null.");
        Preconditions.checkNotNull(fallbackValueProvider, "Parameter fallbackValueProvider is null.");
        Preconditions.checkNotNull(rawFormConverter, "Parameter rawFormConverter is null.");

        this.rawConfigRepository = rawConfigRepository;
        this.fallbackValueProvider = fallbackValueProvider;
        this.rawFormConverter = rawFormConverter;
    }

    @Override
    public void initialize() throws IOException {
        this.rawConfigRepository.initialize();
    }

    @NotNull
    @Override
    public <C extends ConfigType> C load(@NotNull Class<C> configClass) throws IOException {
        Preconditions.checkNotNull(configClass, "Parameter configClass is null.");

        RawFormType rawFormConfig = this.rawConfigRepository.load(this.validateAndGetConfigName(configClass));
        return this.rawFormConverter.toObjectForm(rawFormConfig, configClass);
    }

    @NotNull
    @Override
    public <C extends ConfigType> C loadWithFallback(@NotNull C fallbackConfig) throws IOException {
        Preconditions.checkNotNull(fallbackConfig, "Parameter configClass is null.");

        Class<? extends C> configClass = (Class<? extends C>) fallbackConfig.getClass();
        RawFormType rawFormConfig = this.rawConfigRepository.load(this.validateAndGetConfigName(configClass));
        RawFormType rawFormFallbackConfig = this.rawFormConverter.toRawForm(fallbackConfig);
        RawFormType rawFormUpdated = this.fallbackValueProvider.appendFallbackValues(rawFormConfig, rawFormFallbackConfig);
        return this.rawFormConverter.toObjectForm(rawFormUpdated, configClass);
    }

    @Override
    public void save(@NotNull ConfigType config) throws IOException {
        Preconditions.checkNotNull(config, "Parameter config is null.");

        this.rawConfigRepository.save(this.validateAndGetConfigName(config.getClass()), this.rawFormConverter.toRawForm(config));
    }

    @Override
    public boolean isSaved(@NotNull Class<? extends ConfigType> configClass) throws IOException {
        Preconditions.checkNotNull(configClass, "Parameter configClass is null.");

        return this.rawConfigRepository.isSaved(this.validateAndGetConfigName(configClass));
    }

    private String validateAndGetConfigName(Class<?> configClass) {
        Preconditions.checkState(
                configClass.isAnnotationPresent(Config.class),
                "Class %s is not annotated with Config.".formatted(configClass.getName())
        );
        return configClass.getAnnotation(Config.class).name();
    }

}
