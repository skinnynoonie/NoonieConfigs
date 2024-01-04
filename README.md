# NoonieConfigs

NoonieConfigs is a simple library to manage configurations.
It does this by using concrete classes to represent configuration files.
NoonieConfigs also uses a unique system, which helps the user set and update to valid values.

NoonieConfigs offers:
1. Automatic updates for configs.
2. Automatic fallback value replacement.


# Set Up
To set up a basic configuration environment, a service must be instantiated:
```java
public class MyJavaPlugin {

    public static void main(String[] args) throws Exception {
        Path pathToConfigFolder = Path.of(/*Your path here.*/);
        ConfigService<Object> configService = DefaultConfigServices
                .createJsonConfigService(pathToConfigFolder);
        configService.initialize();
    }

}
```
To set up a configuration class, make sure the class is annotated with `@Config`, like so:</br>
```java
@Config(name = "MyConfig")
public class MyConfig {
    
    private final String food = "Pizza";
    private final int favoriteInteger = 100;
    
}
```
*Note: In the configuration class it is recommended to set up default values which can be used as fallback values.*

# Save a Configuration
To save a configuration, all that needs to be done is:
```java
configService.save(myConfig);
```

# Load a Configuration
To load a configuration from storage, it requires a little few more steps:</br>

Firstly, the configuration must exist already.</br>
Secondly, the configuration must be a valid representation of the class. </br>
*Note: To enforce the second step, load the configuration with a fallback object.*

```java
MyConfig myConfig;
if (configService.isSaved(MyConfig.class)) {
    myConfig = configService.loadWithFallback(new MyConfig());
} else {
    myConfig = new MyConfig();
}
```
