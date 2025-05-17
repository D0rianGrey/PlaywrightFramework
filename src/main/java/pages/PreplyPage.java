package pages;

import com.microsoft.playwright.Page;
import base.BasePage;

public class PreplyPage extends BasePage<Page> {

    public PreplyPage(Page page) {
        super(page);
    }

    public PreplyPage navigateToPreply() {
        page.navigate("https://preply.com");

        return this;
    }
}
