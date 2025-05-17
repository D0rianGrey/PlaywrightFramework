package utils;

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
                throw new RuntimeException("Не найден файл конфигурации: " + resourceName);
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке " + resourceName, e);
        }
    }

    /**
     * Получить все свойства
     */
    public static Properties getProps() {
        return props;
    }

    /**
     * Получить значение по ключу
     */
    public static String get(String key) {
        return props.getProperty(key);
    }

    /**
     * Получить значение как int с дефолтным фолбэком
     */
    public static int getInt(String key, int defaultValue) {
        String val = props.getProperty(key);
        return val != null ? Integer.parseInt(val) : defaultValue;
    }

    /**
     * Получить значение как boolean с дефолтным фолбэком
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = props.getProperty(key);
        return val != null ? Boolean.parseBoolean(val) : defaultValue;
    }
}
