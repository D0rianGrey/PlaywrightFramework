package tests.saucedemo;

import base.BaseTest;
import base.annotations.PlaywrightFactoryTest;
import base.annotations.PlaywrightParameterizedTest;
import base.annotations.PlaywrightTest;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.sauceDemo.SauceLoginPage;

import java.util.List;
import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class SauceDemoLoginTests extends BaseTest {

    @PlaywrightTest
    void openLoginPageTest(SauceLoginPage sauceLoginPage) {
        sauceLoginPage.navigateToSauceDemo();
    }

    @PlaywrightParameterizedTest
    @ValueSource(strings = {"standard_user"})
    void loginTest1(String username, SauceLoginPage sauceLoginPage) {

        var password = "secret_sauce";

        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    @PlaywrightParameterizedTest
    @CsvSource({
            "standard_user, secret_sauce",
    })
    void loginTest2(String username, String password, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    @PlaywrightParameterizedTest
    @MethodSource("tests.saucedemo.SauceDemoTestHelper#getUsers")
    void loginTest3(SauceDemoTestHelper.Users users, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(users.username())
                .enterPassword(users.password())
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    static Stream<SauceDemoTestHelper.Users> userDataProvider1() {
        return SauceDemoTestHelper.getUsers();
    }

    @PlaywrightParameterizedTest
    @MethodSource("userDataProvider1")
    void loginTest4(SauceDemoTestHelper.Users users, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(users.username())
                .enterPassword(users.password())
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    @PlaywrightParameterizedTest
    @CsvFileSource(resources = "/testdata/users.csv")
    void loginTest5(String username, String password, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    static Stream<SauceDemoTestHelper.Users> userDataProvider2() {
        return SauceDemoTestHelper.loadProductsFromJson();
    }

    @PlaywrightParameterizedTest
    @MethodSource("userDataProvider2")
    void loginTest6(SauceDemoTestHelper.Users users, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(users.username())
                .enterPassword(users.password())
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }

    @TestFactory
    @PlaywrightFactoryTest
    Stream<DynamicTest> loginTest7(SauceLoginPage sauceLoginPage) {
        List<String> users = List.of("standard_user");

        return users.stream()
                .map(user -> dynamicTest("Check if " + user + " can login", () -> {
                    var inventoryPage = sauceLoginPage
                            .navigateToSauceDemo()
                            .enterUsername(user)
                            .enterPassword("secret_sauce")
                            .clickLoginButton();

                    assertThat(inventoryPage.getHeaderElement()).isVisible();
                }));
    }

    @PlaywrightTest
    @ExtendWith(TestDataExtension.class)
    void loginTest8(TestDataExtension.TestData testData, SauceLoginPage sauceLoginPage) {
        var inventoryPage = sauceLoginPage
                .navigateToSauceDemo()
                .enterUsername(testData.validUsername)
                .enterPassword(testData.validPassword)
                .clickLoginButton();

        assertThat(inventoryPage.getHeaderElement()).isVisible();
    }
}
