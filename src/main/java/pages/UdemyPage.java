package pages;

import com.microsoft.playwright.Page;
import pages.base.BasePage;

public class UdemyPage extends BasePage<Page> {

    public UdemyPage(Page page) {
        super(page);
    }

    public UdemyPage navigateToUdemy() {
        page.navigate("https://www.udemy.com");

        return this;
    }
}
