package pages;

import com.microsoft.playwright.Page;
import base.BasePageUi;

public class GithubPage extends BasePageUi<Page> {

    public GithubPage(Page page) {
        super(page);
    }

    public GithubPage navigateToGithub() {
        page.navigate("https://github.com");

        return this;
    }
}
