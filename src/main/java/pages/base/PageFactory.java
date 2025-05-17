package pages.base;

import com.microsoft.playwright.Page;
import pages.GooglePage;

public class PageFactory {
    private final Page page;

    public PageFactory(Page page) {
        this.page = page;
    }

    public GooglePage createGooglePage() {
        return new GooglePage(page);
    }
}