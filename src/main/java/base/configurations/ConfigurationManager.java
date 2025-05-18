package base.configurations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationManager {

    private static final Map<String, Configuration> configurations = new ConcurrentHashMap<>();
    private static final String DEFAULT_CONFIG = "config.properties";

    // Получить конфигурацию по умолчанию
    public static Configuration getDefault() {
        return getConfiguration(DEFAULT_CONFIG);
    }

    // Получить конфигурацию по имени файла (кэшируется)
    public static Configuration getConfiguration(String configFile) {
        return configurations.computeIfAbsent(configFile, PropertiesConfiguration::new);
    }

    // Для разных сред
    public static Configuration getEnvironmentConfiguration(String environment) {
        String configFile = "config-" + environment + ".properties";
        return getConfiguration(configFile);
    }

    // Сброс кэша (для тестов)
    public static void clearCache() {
        configurations.clear();
    }

    // Проверка, загружена ли конфигурация
    public static boolean isLoaded(String configFile) {
        return configurations.containsKey(configFile);
    }

    // Для отладки - показать все загруженные конфигурации
    public static void printLoadedConfigurations() {
        System.out.println("=== Loaded Configurations ===");
        configurations.forEach((file, config) -> {
            System.out.println("File: " + file + " -> " + config.getClass().getSimpleName());
        });
        System.out.println("==============================");
    }
}
