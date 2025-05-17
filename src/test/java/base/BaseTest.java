package base;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import utils.Config;

import java.util.Properties;

import static org.junit.jupiter.params.support.ParameterInfo.NAMESPACE;

@UsePlaywright(BaseTest.class)
public class BaseTest implements OptionsFactory {

    static Properties properties;

    @BeforeAll
    static void setUp() {
        properties = Config.getProps();
    }


    @Override
    public Options getOptions() {
        return new Options().setHeadless(Boolean.parseBoolean(properties.getProperty("headless")));
    }
}
