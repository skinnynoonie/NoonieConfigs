package me.skinnynoonie.noonieconfigs.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes that are configurable (implements {@link Configurable}) must also be annotated with this.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {

    /**
     * The name of the configuration, which should be unique and readable.
     * This name will be used for saving, loading, and other various actions.
     * @return The name of this configuration.
     */
    String name();

}
