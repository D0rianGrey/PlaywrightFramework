package base;

import com.microsoft.playwright.APIRequestContext;

public abstract class BaseApi {
    protected final APIRequestContext request;

    public BaseApi(APIRequestContext request) {
        this.request = request;
    }
}