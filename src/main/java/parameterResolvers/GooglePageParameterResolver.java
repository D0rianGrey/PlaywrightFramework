package parameterResolvers;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import pages.GooglePage;
import pages.base.CustomPlaywrightExtension;

public class GooglePageParameterResolver implements ParameterResolver {

    // Используйте тот же namespace, что и в CustomPlaywrightExtension
    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(CustomPlaywrightExtension.class);

    @Override
    public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
        return pc.getParameter().getType() == GooglePage.class;
    }

    @Override
    public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
        // Получаем Page из того же namespace
        Page page = ec.getStore(NAMESPACE).get("page", Page.class);

        // Добавим отладочный вывод
        if (page == null) {
            System.out.println("ОШИБКА: Page равен null в GooglePageParameterResolver!");
        } else {
            System.out.println("Page успешно получен в GooglePageParameterResolver");
        }

        return new GooglePage(page);
    }
}