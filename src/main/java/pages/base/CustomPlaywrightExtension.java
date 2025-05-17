package pages.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.*;

public class CustomPlaywrightExtension
        implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(CustomPlaywrightExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();

        // Сохраняем все объекты в контексте
        context.getStore(NAMESPACE).put("playwright", playwright);
        context.getStore(NAMESPACE).put("browser", browser);
        context.getStore(NAMESPACE).put("page", page);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Page page = context.getStore(NAMESPACE).get("page", Page.class);
        Browser browser = context.getStore(NAMESPACE).get("browser", Browser.class);
        Playwright playwright = context.getStore(NAMESPACE).get("playwright", Playwright.class);

        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        return type == Page.class || type == Browser.class || type == Playwright.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        if (type == Page.class) {
            return extensionContext.getStore(NAMESPACE).get("page", Page.class);
        } else if (type == Browser.class) {
            return extensionContext.getStore(NAMESPACE).get("browser", Browser.class);
        } else if (type == Playwright.class) {
            return extensionContext.getStore(NAMESPACE).get("playwright", Playwright.class);
        }

        return null;
    }
}