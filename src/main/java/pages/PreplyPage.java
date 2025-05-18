package pages;

import com.microsoft.playwright.Page;
import base.BasePageUi;

public class PreplyPage extends BasePageUi<Page> {

    public PreplyPage(Page page) {
        super(page);
    }

    public PreplyPage navigateToPreply() {
        page.navigate("https://preply.com");

        return this;
    }
}
