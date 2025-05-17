package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.*;
import pages.GithubPage;
import pages.GooglePage;
import pages.PreplyPage;
import pages.UdemyPage;
import pages.sauceDemo.SauceLoginPage;

import java.lang.reflect.Method;

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

        // Store all the objects in the test context
        context.getStore(NAMESPACE).put("playwright", playwright);
        context.getStore(NAMESPACE).put("browser", browser);
        context.getStore(NAMESPACE).put("page", page);

        var testMethodName = context.getTestMethod()
                .map(Method::getName)
                .orElseThrow(() -> new IllegalStateException("Test method not found"));

        System.out.println("PlaywrightPageExtension: Page created and stored for test -> " + testMethodName);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        try {
            // Close resources
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

            System.out.println("Resources successfully closed for test -> " +
                    context.getTestMethod()
                            .map(Method::getName)
                            .orElseThrow(() -> new IllegalStateException("Test method not found")));

        } catch (Exception e) {
            System.out.println("Error while closing Playwright resources: " + e.getMessage());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();

        // Support all classes that extend BasePage
        if (BasePage.class.isAssignableFrom(type)) {
            return true;
        }

        // Support core Playwright types
        return type == Page.class ||
                type == Browser.class ||
                type == Playwright.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        Page page = extensionContext.getStore(NAMESPACE).get("page", Page.class);

        // Create specific page objects
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
        } else if (BasePage.class.isAssignableFrom(type)) {
            try {
                // Use reflection to instantiate custom page classes
                return type.getConstructor(Page.class).newInstance(page);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create page of type " + type.getName(), e);
            }
        }

        // Return Playwright core objects
        if (type == Page.class) {
            return page;
        } else if (type == Browser.class) {
            return extensionContext.getStore(NAMESPACE).get("browser", Browser.class);
        } else if (type == Playwright.class) {
            return extensionContext.getStore(NAMESPACE).get("playwright", Playwright.class);
        }

        return null;
    }
}