package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Test;

@UsePlaywright
public class DebugTests {

    @Test
    void debugTest(Page page) {
        // Просто проверяем, что page не null
        System.out.println("Page is null: " + (page == null));

        if (page != null) {
            // Проверяем базовую функциональность
            page.navigate("https://www.google.com");
            System.out.println("Title: " + page.title());
        }
    }
}