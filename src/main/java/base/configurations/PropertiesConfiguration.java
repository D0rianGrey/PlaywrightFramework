package base.configurations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfiguration implements Configuration {

    private final Properties properties;
    private final String configFile;

    public PropertiesConfiguration(String configFile) {
        this.configFile = configFile;
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (resourceAsStream == null) {
                try {
                    throw new ConfigurationException("Configuration file not found: " + configFile);
                } catch (ConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
            properties.load(resourceAsStream);
            System.out.println("Loaded configuration from: " + configFile);
        } catch (IOException e) {
            throw new ConfigurationException("Error loading configuration: " + configFile, e);
        }
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public Properties getAllProperties() {
        return new Properties(properties); // Возвращаем копию для безопасности
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }
}