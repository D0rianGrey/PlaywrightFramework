package pages.sauceDemo;

import base.BasePageUi;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SauceLoginPage extends BasePageUi<Page> {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    private static final String LOGIN_URL = "https://www.saucedemo.com";

    public SauceLoginPage(Page page) {
        super(page);
        usernameInput = page.locator("#user-name");
        passwordInput = page.locator("#password");
        loginButton = page.locator("#login-button");
    }

    public SauceLoginPage navigateToSauceDemo() {
        System.out.println("Navigating to SauceDemo login page");
        page.navigate(LOGIN_URL);

        return this;
    }

    public SauceLoginPage enterUsername(String username) {
        System.out.println("Entering username: " + username);
        usernameInput.fill(username);

        return this;
    }

    public SauceLoginPage enterPassword(String password) {
        System.out.println("Entering password: " + password);
        passwordInput.fill(password);

        return this;
    }

    public InventoryPage clickLoginButton() {
        System.out.println("Clicking login button");
        loginButton.click();

        return new InventoryPage(page);
    }
}
