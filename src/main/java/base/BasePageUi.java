package base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePageUi<T extends Page> {

    protected final T page;

    public BasePageUi(T page) {
        this.page = page;
    }

    protected void click(String selector) {
        page.locator(selector).click();
    }

    protected void fill(String selector, String text) {
        page.locator(selector).fill(text);
    }

    protected void waitForSelector(String selector) {
        page.waitForSelector(selector,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    protected String getText(String selector) {
        return page.locator(selector).textContent();
    }
}