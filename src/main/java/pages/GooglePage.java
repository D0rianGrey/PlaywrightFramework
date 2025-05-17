package pages;

import com.microsoft.playwright.Page;
import base.BasePage;

public class GooglePage extends BasePage<Page> {

    public GooglePage(Page page) {
        super(page);
    }

    public GooglePage navigateToGoogle() {
        page.navigate("https://www.google.com");

        return this;
    }
}
