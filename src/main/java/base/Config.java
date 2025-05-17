package base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        loadProperties("config.properties");
    }

    private static void loadProperties(String resourceName) {
        try (InputStream in = Config.class
                .getClassLoader()
                .getResourceAsStream(resourceName)) {
            if (in == null) {
                throw new RuntimeException("Configuration file not found: " + resourceName);
            }
            props.load(in);
            System.out.println("Loaded properties from " + resourceName);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading " + resourceName, e);
        }
    }

    public static Properties getProps() {
        return props;
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key, int defaultValue) {
        String val = props.getProperty(key);
        return val != null ? Integer.parseInt(val) : defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = props.getProperty(key);
        return val != null ? Boolean.parseBoolean(val) : defaultValue;
    }
}