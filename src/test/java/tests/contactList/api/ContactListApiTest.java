package tests.contactList.api;

import base.annotations.PlaywrightTest;
import pages.contactList.ContactListApi;

public class ContactListApiTest {

    @PlaywrightTest
    public void testContactListApi(ContactListApi contactListApi) {
        contactListApi.getContactList();
    }
}
