package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.GooglePage;
import pages.base.PlaywrightTest;
import parameterResolvers.PlaywrightPageExtension;

public class GoogleTests extends BaseTest {

    @Test
    @ExtendWith(PlaywrightPageExtension.class)
    void googleTest1(GooglePage googlePage) {
        googlePage.navigateToGoogle();
    }

    @PlaywrightTest
    void test2(GooglePage googlePage) {
        googlePage.navigateToGoogle();
    }

}