package pages;

import com.microsoft.playwright.Page;
import base.BasePageUi;

public class UdemyPage extends BasePageUi<Page> {

    public UdemyPage(Page page) {
        super(page);
    }

    public UdemyPage navigateToUdemy() {
        page.navigate("https://www.udemy.com");

        return this;
    }
}
