package pages.sauceDemo;

import base.BasePageUi;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage extends BasePageUi<Page> {

    private final Locator header;

    public InventoryPage(Page page) {
        super(page);
        header = page.locator("div.header_label");
    }

    public Locator getHeaderElement() {
        return header;
    }
}
