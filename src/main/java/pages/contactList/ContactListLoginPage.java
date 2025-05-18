package pages.contactList;

import base.BasePageUi;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ContactListLoginPage extends BasePageUi<Page> {

    private final Locator emailInputField;
    private final Locator passwordInputField;
    private final Locator submitButton;
    private final Locator signUpButton;
    private final Locator header;

    private final String LOGIN_URL = "https://thinking-tester-contact-list.herokuapp.com/";


    public ContactListLoginPage(Page page) {
        super(page);
        emailInputField = page.locator("#email");
        passwordInputField = page.locator("#password");
        submitButton = page.locator("#submit");
        signUpButton = page.locator("#signup");
        header = page.locator("h1");
    }

    public ContactListLoginPage navigateToContactList() {
        System.out.println("Navigating to Contact List login page");
        page.navigate(LOGIN_URL);

        return this;
    }

    public ContactListLoginPage enterEmail(String email) {
        System.out.println("Entering email: " + email);
        emailInputField.fill(email);

        return this;
    }

    public ContactListLoginPage enterPassword(String password) {
        System.out.println("Entering password: " + password);
        passwordInputField.fill(password);

        return this;
    }

    public ContactListLoginPage clickSubmitButton() {
        System.out.println("Clicking submit button");
        submitButton.click();

        return this;
    }

    public ContactListLoginPage clickSignUpButton() {
        System.out.println("Clicking sign up button");
        signUpButton.click();

        return this;
    }

    public Locator getHeaderElem() {
        return header;
    }
}
