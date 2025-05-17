package tests;

import base.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;
import pages.GithubPage;
import pages.GooglePage;
import pages.PreplyPage;
import pages.UdemyPage;


public class EugeneTests extends BaseTest {

    @Test
    void googleTest(Page page) {
        new GooglePage(page).navigateToGoogle();
    }

    @Test
    void udemyTest(Page page) {
        new UdemyPage(page).navigateToUdemy();
    }

    @Test
    void githubTest(Page page) {
        new GithubPage(page).navigateToGithub();
    }

    @Test
    void preplyTest(Page page) {
        new PreplyPage(page).navigateToPreply();
    }
}
