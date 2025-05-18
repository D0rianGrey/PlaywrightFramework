package base.configurations;

public interface Configuration {

    String get(String key);

    String get(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    boolean getBoolean(String key, boolean defaultValue);
}