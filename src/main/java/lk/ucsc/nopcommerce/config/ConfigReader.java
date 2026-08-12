package lk.ucsc.nopcommerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Loads framework defaults while allowing -Dkey=value command-line overrides. */
public final class ConfigReader {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        String configuredValue = PROPERTIES.getProperty(key);
        if (configuredValue == null || configuredValue.isBlank()) {
            throw new IllegalArgumentException("Missing required configuration: " + key);
        }
        return configuredValue.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream stream = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Cannot find " + CONFIG_FILE + " on the classpath");
            }
            properties.load(stream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load " + CONFIG_FILE, exception);
        }
    }
}

