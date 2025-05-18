package base;

import base.configurations.Configuration;
import base.configurations.ConfigurationException;
import base.configurations.ConfigurationManager;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.extension.*;
import pages.GithubPage;
import pages.GooglePage;
import pages.PreplyPage;
import pages.UdemyPage;
import pages.contactList.ContactListApi;
import pages.contactList.ContactListLoginPage;
import pages.sauceDemo.SauceLoginPage;

import java.lang.reflect.Method;
import java.util.Map;

public class PlaywrightPageExtension
        implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(PlaywrightPageExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        // Получаем конфигурацию
        Configuration config = ConfigurationManager.getDefault();

        // Создаем Playwright объекты
        Playwright playwright = Playwright.create();

        // Настраиваем браузер из конфигурации
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(config.getBoolean("browser.headless", false))
                        .setSlowMo(config.getInt("browser.slowmo", 0))
        );

        Page page = browser.newPage();
        page.setDefaultTimeout(config.getInt("browser.timeout", 30000));

        // Создаем API контекст из конфигурации
        String apiBaseUrl = config.get("api.baseUrl", "https://thinking-tester-contact-list.herokuapp.com");
        if (apiBaseUrl == null) {
            throw new ConfigurationException("'api.baseUrl' property is required in configuration");
        }

        APIRequestContext apiRequestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(apiBaseUrl)
                        .setExtraHTTPHeaders(Map.of(
                                "Content-Type", config.get("api.content.type", "application/json"),
                                "Accept", config.get("api.accept", "application/json")
                        ))
        );

        // Сохраняем все в контексте
        context.getStore(NAMESPACE).put("playwright", playwright);
        context.getStore(NAMESPACE).put("browser", browser);
        context.getStore(NAMESPACE).put("page", page);
        context.getStore(NAMESPACE).put("api", apiRequestContext);
        context.getStore(NAMESPACE).put("config", config);

        var testMethodName = context.getTestMethod()
                .map(Method::getName)
                .orElseThrow(() -> new IllegalStateException("Test method not found"));

        System.out.println("PlaywrightPageExtension: Initialized for test -> " + testMethodName);
        System.out.println("API Base URL: " + apiBaseUrl);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        try {
            // Закрываем ресурсы в правильном порядке
            Page page = context.getStore(NAMESPACE).get("page", Page.class);
            if (page != null && !page.isClosed()) {
                page.close();
                context.getStore(NAMESPACE).remove("page");
            }

            APIRequestContext apiRequestContext = context.getStore(NAMESPACE).get("api", APIRequestContext.class);
            if (apiRequestContext != null) {
                apiRequestContext.dispose();
                context.getStore(NAMESPACE).remove("api");
            }

            Browser browser = context.getStore(NAMESPACE).get("browser", Browser.class);
            if (browser != null && browser.isConnected()) {
                browser.close();
                context.getStore(NAMESPACE).remove("browser");
            }

            Playwright playwright = context.getStore(NAMESPACE).get("playwright", Playwright.class);
            if (playwright != null) {
                playwright.close();
                context.getStore(NAMESPACE).remove("playwright");
            }

            context.getStore(NAMESPACE).remove("config");

            String testMethodName = context.getTestMethod()
                    .map(Method::getName)
                    .orElse("unknown");
            System.out.println("Resources closed for test -> " + testMethodName);

        } catch (Exception e) {
            System.err.println("Error while closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();

        // Поддержка Configuration
        if (type == Configuration.class) {
            return true;
        }

        // Поддержка Page объектов
        if (BasePageUi.class.isAssignableFrom(type)) {
            return true;
        }

        // Поддержка API классов
        if (type == ContactListApi.class) {
            return true;
        }

        // Поддержка базовых Playwright типов
        return type == Page.class ||
                type == Browser.class ||
                type == Playwright.class ||
                type == APIRequestContext.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();

        // Получаем объекты из контекста
        Page page = extensionContext.getStore(NAMESPACE).get("page", Page.class);
        APIRequestContext apiRequestContext = extensionContext.getStore(NAMESPACE).get("api", APIRequestContext.class);
        Configuration config = extensionContext.getStore(NAMESPACE).get("config", Configuration.class);

        // Возвращаем конфигурацию
        if (type == Configuration.class) {
            return config;
        }

        // Создаем Page объекты
        if (type == GooglePage.class) {
            return new GooglePage(page);
        } else if (type == UdemyPage.class) {
            return new UdemyPage(page);
        } else if (type == GithubPage.class) {
            return new GithubPage(page);
        } else if (type == PreplyPage.class) {
            return new PreplyPage(page);
        } else if (type == SauceLoginPage.class) {
            return new SauceLoginPage(page);
        } else if (type == ContactListLoginPage.class) {
            return new ContactListLoginPage(page);
        } else if (type == ContactListApi.class) {
            return new ContactListApi(apiRequestContext);
        } else if (BasePageUi.class.isAssignableFrom(type)) {
            try {
                return type.getConstructor(Page.class).newInstance(page);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create page of type " + type.getName(), e);
            }
        }

        // Возвращаем базовые Playwright объекты
        if (type == Page.class) {
            return page;
        } else if (type == Browser.class) {
            return extensionContext.getStore(NAMESPACE).get("browser", Browser.class);
        } else if (type == Playwright.class) {
            return extensionContext.getStore(NAMESPACE).get("playwright", Playwright.class);
        } else if (type == APIRequestContext.class) {
            return apiRequestContext;
        }

        return null;
    }
}