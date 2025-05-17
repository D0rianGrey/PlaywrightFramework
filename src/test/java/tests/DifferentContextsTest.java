package tests;

import pages.GithubPage;
import pages.GooglePage;
import pages.PreplyPage;
import base.PlaywrightTest;

public class DifferentContextsTest {

    @PlaywrightTest
    void google(GooglePage googlePage) {
        googlePage.navigateToGoogle();
    }

    @PlaywrightTest
    void github(GithubPage githubPage) {
        githubPage.navigateToGithub();
    }

    @PlaywrightTest
    void preply(PreplyPage preplyPage) {
        preplyPage.navigateToPreply();
    }

    @PlaywrightTest
    void udemy(GithubPage githubPage) {
        githubPage.navigateToGithub();
    }
}
