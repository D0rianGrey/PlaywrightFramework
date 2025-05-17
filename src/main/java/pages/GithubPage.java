package pages;

import com.microsoft.playwright.Page;
import base.BasePage;

public class GithubPage extends BasePage<Page> {

    public GithubPage(Page page) {
        super(page);
    }

    public GithubPage navigateToGithub() {
        page.navigate("https://github.com");

        return this;
    }
}
