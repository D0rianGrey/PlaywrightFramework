package parameterResolvers;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.*;
import pages.GooglePage;

public class PlaywrightPageExtension
        implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(PlaywrightPageExtension.class);

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

        System.out.println("PlaywrightPageExtension: Page создан и сохранен");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        try {
            // Закрываем ресурсы
            Page page = context.getStore(NAMESPACE).get("page", Page.class);
            if (page != null) {
                page.close();
                context.getStore(NAMESPACE).remove("page");
            }

            Browser browser = context.getStore(NAMESPACE).get("browser", Browser.class);
            if (browser != null) {
                browser.close();
                context.getStore(NAMESPACE).remove("browser");
            }

            Playwright playwright = context.getStore(NAMESPACE).get("playwright", Playwright.class);
            if (playwright != null) {
                playwright.close();
                context.getStore(NAMESPACE).remove("playwright");
            }

            System.out.println("Ресурсы успешно закрыты");
        } catch (Exception e) {
            System.out.println("Ошибка при закрытии Playwright ресурсов: " + e.getMessage());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        return type == Page.class ||
                type == Browser.class ||
                type == Playwright.class ||
                type == GooglePage.class;  // Добавляем поддержку GooglePage
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
        } else if (type == GooglePage.class) {
            Page page = extensionContext.getStore(NAMESPACE).get("page", Page.class);
            return new GooglePage(page);
        }

        return null;
    }
}