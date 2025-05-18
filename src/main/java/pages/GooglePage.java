package pages;

import com.microsoft.playwright.Page;
import base.BasePageUi;

public class GooglePage extends BasePageUi<Page> {

    public GooglePage(Page page) {
        super(page);
    }

    public GooglePage navigateToGoogle() {
        page.navigate("https://www.google.com");

        return this;
    }
}
