# 🎭 Playwright Test Automation Framework

![Java](https://img.shields.io/badge/Java-21-orange)
![Playwright](https://img.shields.io/badge/Playwright-1.52.0-blue)
![JUnit5](https://img.shields.io/badge/JUnit-5.13.0--M3-green)
![Maven](https://img.shields.io/badge/Maven-3.6+-red)

## 📋 Описание проекта

Этот проект представляет собой современный фреймворк для автоматизации тестирования, построенный на базе **Playwright** и **JUnit 5**. Фреймворк поддерживает как UI-тестирование веб-приложений, так и API-тестирование.

### 🎯 Основные возможности

- ✅ **UI-тестирование** с использованием Page Object Model
- ✅ **API-тестирование** с встроенным HTTP-клиентом
- ✅ **Параллельное выполнение** тестов
- ✅ **Гибкая конфигурация** через properties файлы
- ✅ **Множественные источники тестовых данных** (CSV, JSON, методы)
- ✅ **Параметризованные тесты** с различными аннотациями
- ✅ **Динамические тесты** (@TestFactory)
- ✅ **Автоматическое управление ресурсами** браузера

## 🏗️ Архитектура проекта

Представьте наш фреймворк как **многоэтажное здание**, где каждый этаж имеет свою функцию:

```
🏢 Playwright Test Framework
├── 🎯 Tests (4-й этаж)           - Готовые тесты для разных приложений
├── 📄 Pages (3-й этаж)          - Page Object классы для UI и API
├── ⚙️ Base Components (2-й этаж) - Базовые классы и конфигурация  
└── 🔧 Infrastructure (1-й этаж) - Аннотации, расширения, утилиты
```

### 📁 Структура директорий

```
src/
├── main/java/
│   ├── base/                     # 🏗️ Базовые компоненты
│   │   ├── annotations/          # 🏷️ Пользовательские аннотации
│   │   ├── configurations/       # ⚙️ Система конфигурации
│   │   ├── BaseApi.java          # 🌐 Базовый класс для API
│   │   ├── BasePageUi.java       # 🖼️ Базовый класс для UI страниц
│   │   └── PlaywrightPageExtension.java # 🔌 JUnit 5 расширение
│   └── pages/                    # 📄 Page Object классы
│       ├── contactList/          # 📞 Страницы Contact List
│       ├── sauceDemo/           # 🛒 Страницы SauceDemo
│       └── [другие приложения]
├── test/java/
│   └── tests/                    # 🧪 Тестовые классы
└── test/resources/
    ├── testdata/                 # 📊 Тестовые данные
    └── config files              # ⚙️ Конфигурационные файлы
```

## 🚀 Быстрый старт

### 1️⃣ Предварительные требования

- **Java 21** или выше
- **Maven 3.6+**
- **Playwright browsers** (устанавливаются автоматически)

### 2️⃣ Установка и настройка

```bash
# Клонируйте репозиторий
git clone <repository-url>
cd PlaywrightFramework

# Установите зависимости и browsers
mvn clean install
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 3️⃣ Запуск тестов

```bash
# Запуск всех тестов
mvn test

# Запуск конкретного класса тестов
mvn test -Dtest=SauceDemoLoginTests

# Запуск в режиме headless (по умолчанию)
mvn test -Dbrowser.headless=true

# Запуск с видимым браузером
mvn test -Dbrowser.headless=false
```

## 🎭 Система аннотаций

Наш фреймворк использует **специальные аннотации** - это как ярлыки на коробках, которые сразу говорят, что внутри:

### @PlaywrightTest
Базовая аннотация для простых тестов - как обычная коробка с одним предметом:

```java
@PlaywrightTest
void simpleLoginTest(SauceLoginPage loginPage) {
    loginPage.navigateToSauceDemo()
        .enterUsername("standard_user")
        .enterPassword("secret_sauce")
        .clickLoginButton();
}
```

### @PlaywrightParameterizedTest
Для параметризованных тестов - как коробка с множеством одинаковых предметов:

```java
@PlaywrightParameterizedTest
@ValueSource(strings = {"user1", "user2", "user3"})
void multipleUsersTest(String username, SauceLoginPage loginPage) {
    // Тест запустится 3 раза с разными пользователями
}

@PlaywrightParameterizedTest
@CsvSource({
    "standard_user, secret_sauce",
    "problem_user, secret_sauce"
})
void csvSourceTest(String username, String password, SauceLoginPage loginPage) {
    // Тест с данными из CSV
}
```

### @PlaywrightFactoryTest 
Для динамических тестов - как фабрика, которая создает тесты на лету:

```java
@TestFactory
@PlaywrightFactoryTest
Stream<DynamicTest> dynamicLoginTests(SauceLoginPage loginPage) {
    List<String> users = List.of("user1", "user2", "user3");
    
    return users.stream()
        .map(user -> dynamicTest("Login test for " + user, () -> {
            // Логика теста
        }));
}
```

## 🏗️ Page Object Model

Page Object Model - это как **адресная книга** для веб-страниц. Каждая страница - это отдельная "визитная карточка" с контактной информацией (локаторы) и возможными действиями (методы).

### Базовый UI класс (BasePageUi)

```java
public abstract class BasePageUi<T extends Page> {
    protected final T page;
    
    // Основные действия "из коробки"
    protected void click(String selector) { /* ... */ }
    protected void fill(String selector, String text) { /* ... */ }
    protected void waitForSelector(String selector) { /* ... */ }
    protected String getText(String selector) { /* ... */ }
}
```

### Пример Page Object класса

```java
public class SauceLoginPage extends BasePageUi<Page> {
    // Локаторы - как "адреса" элементов на странице
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    
    public SauceLoginPage(Page page) {
        super(page);
        // Инициализация локаторов
        usernameInput = page.locator("#user-name");
        passwordInput = page.locator("#password");
        loginButton = page.locator("#login-button");
    }
    
    // Методы - это "действия", которые можно совершить на странице
    public SauceLoginPage navigateToSauceDemo() {
        page.navigate("https://www.saucedemo.com");
        return this; // Fluent API - цепочка вызовов
    }
    
    public SauceLoginPage enterUsername(String username) {
        usernameInput.fill(username);
        return this;
    }
    
    // Метод возвращает следующую страницу после успешного логина
    public InventoryPage clickLoginButton() {
        loginButton.click();
        return new InventoryPage(page);
    }
}
```

## 🌐 API Тестирование

API тестирование - это как **отправка писем** между программами. Мы отправляем запрос и получаем ответ.

### Базовый API класс

```java
public abstract class BaseApi {
    protected final APIRequestContext request;
    
    public BaseApi(APIRequestContext request) {
        this.request = request;
    }
}
```

### Пример API класса

```java
public class ContactListApi extends BaseApi {
    public ContactListApi(APIRequestContext request) {
        super(request);
    }
    
    public void getContactList() {
        var response = request.get("/contacts");
        System.out.println("Status: " + response.status());
        System.out.println("Body: " + response.text());
    }
    
    public void createContact(Contact contact) {
        var response = request.post("/contacts", 
            RequestOptions.create().setData(contact));
        // Обработка ответа
    }
}
```

## ⚙️ Система конфигурации

Конфигурация работает как **пульт управления** телевизором - один файл управляет всеми настройками:

### config.properties
```properties
# Настройки браузера
browser.headless=true         # Невидимый режим браузера
browser.slowmo=0             # Задержка между действиями (мс)
browser.timeout=30000        # Максимальное время ожидания (мс)

# Настройки API
api.baseUrl=https://api.example.com
api.content.type=application/json
api.accept=application/json
```

### Использование в коде

```java
@PlaywrightTest
void configurationExample(Configuration config) {
    // Получение значений из конфигурации
    boolean headless = config.getBoolean("browser.headless", false);
    int timeout = config.getInt("browser.timeout", 30000);
    String apiUrl = config.get("api.baseUrl");
    
    // Использование в тесте...
}
```

## 📊 Работа с тестовыми данными

### 1. CSV файлы (users.csv)
```csv
standard_user, secret_sauce
problem_user, secret_sauce
performance_glitch_user, secret_sauce
```

```java
@PlaywrightParameterizedTest
@CsvFileSource(resources = "/testdata/users.csv")
void csvFileTest(String username, String password, SauceLoginPage loginPage) {
    // Тест выполнится для каждой строки из CSV
}
```

### 2. JSON файлы (users.json)
```json
[
  {
    "username": "standard_user",
    "password": "secret_sauce"
  },
  {
    "username": "problem_user", 
    "password": "secret_sauce"
  }
]
```

```java
public static Stream<Users> loadUsersFromJson() {
    ObjectMapper mapper = new ObjectMapper();
    List<Users> users = mapper.readValue(
        new File("src/test/resources/testdata/users.json"),
        new TypeReference<>() {}
    );
    return users.stream();
}

@PlaywrightParameterizedTest
@MethodSource("loadUsersFromJson")
void jsonDataTest(Users user, SauceLoginPage loginPage) {
    // Тест с данными из JSON
}
```

### 3. Методы-поставщики данных
```java
static Stream<Users> userDataProvider() {
    return Stream.of(
        new Users("standard_user", "secret_sauce"),
        new Users("problem_user", "secret_sauce")
    );
}

@PlaywrightParameterizedTest
@MethodSource("userDataProvider")
void methodSourceTest(Users user, SauceLoginPage loginPage) {
    // Тест с данными из метода
}
```

## 🔄 Параллельное выполнение

Настройки в `junit-platform.properties`:

```properties
# Включить параллельное выполнение
junit.jupiter.execution.parallel.enabled=true

# Параллельность по умолчанию
junit.jupiter.execution.parallel.mode.default=concurrent

# Параллельность для классов
junit.jupiter.execution.parallel.mode.classes.default=concurrent

# Стратегия конфигурации - динамическая
junit.jupiter.execution.parallel.config.strategy=dynamic

# Фактор динамической стратегии (2 = в 2 раза больше потоков чем CPU ядер)
junit.jupiter.execution.parallel.config.dynamic.factor=2
```

## 📝 Примеры использования

### Простой UI тест
```java
@PlaywrightTest
void simpleUITest(SauceLoginPage loginPage) {
    var inventoryPage = loginPage
        .navigateToSauceDemo()
        .enterUsername("standard_user")
        .enterPassword("secret_sauce")
        .clickLoginButton();
    
    assertThat(inventoryPage.getHeaderElement()).isVisible();
}
```

### API тест
```java
@PlaywrightTest
void simpleApiTest(ContactListApi api) {
    api.getContactList();
    // Проверки ответа API
}
```

### Параметризованный тест с несколькими источниками
```java
@PlaywrightParameterizedTest
@CsvSource({
    "standard_user, secret_sauce, Products",
    "problem_user, secret_sauce, Products"  
})
void parameterizedLoginTest(String username, String password, 
                          String expectedHeader, SauceLoginPage loginPage) {
    var inventoryPage = loginPage
        .navigateToSauceDemo()
        .enterUsername(username)
        .enterPassword(password)
        .clickLoginButton();
    
    assertThat(inventoryPage.getHeaderElement())
        .hasText(expectedHeader);
}
```

## 🛠️ Расширение фреймворка

### Добавление новой Page Object

1. Создайте класс, наследующий `BasePageUi`:

```java
public class NewPage extends BasePageUi<Page> {
    public NewPage(Page page) {
        super(page);
    }
    
    // Ваши локаторы и методы
}
```

2. Добавьте поддержку в `PlaywrightPageExtension`:

```java
// В методе resolveParameter
else if (type == NewPage.class) {
    return new NewPage(page);
}
```

### Добавление нового API класса

1. Создайте класс, наследующий `BaseApi`:

```java
public class NewApi extends BaseApi {
    public NewApi(APIRequestContext request) {
        super(request);
    }
    
    // Ваши API методы
}
```

2. Добавьте поддержку в `PlaywrightPageExtension` аналогично.

## 🎯 Лучшие практики

### 1. Именование тестов
```java
// ✅ Хорошо - понятно что тестируем
@PlaywrightTest
void shouldLoginSuccessfullyWithValidCredentials() { }

// ❌ Плохо - неинформативно
@PlaywrightTest 
void test1() { }
```

### 2. Организация тестовых данных
```java
// ✅ Хорошо - константы в отдельном классе
public class TestData {
    public static final String VALID_USERNAME = "standard_user";
    public static final String VALID_PASSWORD = "secret_sauce";
}

// ❌ Плохо - хардкод в тестах
loginPage.enterUsername("standard_user");
```

### 3. Ожидания (Assertions)
```java
// ✅ Хорошо - используем Playwright assertions
assertThat(page.locator("#header")).isVisible();
assertThat(page.locator("#title")).hasText("Expected Title");

// ❌ Плохо - обычные JUnit assertions для UI
assertTrue(page.locator("#header").isVisible());
```

### 4. Fluent API
```java
// ✅ Хорошо - цепочка вызовов
loginPage
    .navigateToSauceDemo()
    .enterUsername("user")
    .enterPassword("pass")
    .clickLoginButton();

// ❌ Плохо - отдельные вызовы
loginPage.navigateToSauceDemo();
loginPage.enterUsername("user");
loginPage.enterPassword("pass");
loginPage.clickLoginButton();
```

## 🐛 Отладка

### Включение логирования Playwright
```java
// В переменных окружения или properties
PLAYWRIGHT_BROWSER_DEBUG=1
PLAYWRIGHT_DEBUG=1
```

### Скриншоты при падении тестов
```java
@PlaywrightTest
void debugTest(Page page) {
    try {
        // Ваш тест
    } catch (Exception e) {
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get("screenshot.png")));
        throw e;
    }
}
```

## 📞 Поддержка

При возникновении вопросов или проблем:

1. Проверьте логи выполнения тестов
2. Убедитесь, что все зависимости установлены
3. Проверьте конфигурационные файлы
4. Создайте issue в репозитории проекта

## 📄 Лицензия

Этот проект лицензирован на условиях MIT License - см. файл [LICENSE](LICENSE) для подробностей.

---

*Этот фреймворк создан для упрощения автоматизации тестирования и повышения качества программных продуктов. Удачных тестов! 🚀*