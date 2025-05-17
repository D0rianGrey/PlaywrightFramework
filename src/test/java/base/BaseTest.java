package base;


import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;

import java.util.Properties;

@UsePlaywright(BaseTest.class)
public class BaseTest implements OptionsFactory {

    static Properties properties;

    static {
        properties = Config.getProps();
    }

    @Override
    public Options getOptions() {
        return new Options().setHeadless(Boolean.parseBoolean(properties.getProperty("headless")));
    }
}
