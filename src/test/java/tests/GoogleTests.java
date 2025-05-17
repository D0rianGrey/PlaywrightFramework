package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.GooglePage;
import parameterResolvers.PlaywrightPageExtension;

@ExtendWith(PlaywrightPageExtension.class)
public class GoogleTests extends BaseTest {

    @Test
    void googleTest1(GooglePage googlePage) {
        googlePage.navigateToGoogle();
    }

}