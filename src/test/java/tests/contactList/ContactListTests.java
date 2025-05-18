package tests.contactList;

import base.BaseTest;
import base.annotations.PlaywrightTest;
import pages.contactList.ContactListLoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactListTests extends BaseTest {

    @PlaywrightTest
    public void openContactListLoginPage(ContactListLoginPage contactListLoginPage) {
        var loginPage = contactListLoginPage.navigateToContactList();

        assertThat(loginPage.getHeaderElem()).isVisible();
        assertThat(loginPage.getHeaderElem()).hasText("Contact List App");
    }
}
