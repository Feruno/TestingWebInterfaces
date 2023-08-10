package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class appOrderTest {

    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        //driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void appOrderTest() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Ну пачиму");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void appInvalidName() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("fail");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void appInvalidPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Алекс");
        form.$("span [name=phone]").setValue("78");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void appInvalidCheckbox() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Алекс");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("button").click();
        $(".input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
