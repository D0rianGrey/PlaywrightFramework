package parameterResolvers;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.impl.junit.PlaywrightExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Constructor;

public class PageObjectParameterResolver implements ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(PlaywrightExtension.class);

    @Override
    public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
        Class<?> type = pc.getParameter().getType();
        return pages.base.BasePage.class.isAssignableFrom(type);
    }

    @Override
    public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
        Page page = ec.getStore(NAMESPACE).get("page", Page.class);
        Class<?> pageClass = pc.getParameter().getType();

        try {
            Constructor<?> constructor = pageClass.getConstructor(Page.class);
            return constructor.newInstance(page);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать PageObject: " + pageClass.getName(), e);
        }
    }
}