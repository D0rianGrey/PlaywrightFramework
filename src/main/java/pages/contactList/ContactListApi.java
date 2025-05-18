package pages.contactList;

import base.BaseApi;
import com.microsoft.playwright.APIRequestContext;

public class ContactListApi extends BaseApi {

    public ContactListApi(APIRequestContext request) {
        super(request);
    }

    public void getContactList() {
        var response = request.get("/");
        System.out.println("Response status: " + response.status());
        System.out.println("Response body: " + response.text());
    }
}
