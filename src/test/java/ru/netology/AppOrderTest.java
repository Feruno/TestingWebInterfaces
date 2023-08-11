package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AppOrderTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void appSuccessfulPath() {
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Ну пачиму");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void appInvalidName() {
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("fail");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void appInvalidPhone() {
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Алекс");
        form.$("span [name=phone]").setValue("78");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void appInvalidCheckbox() {
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Алекс");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("button").click();
        $(".input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void appEmptyName() {
        SelenideElement form = $("form");
        form.$("span [name=phone]").setValue("+79012345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void appEmptyPhone() {
        SelenideElement form = $("form");
        form.$("span [name=name]").setValue("Алекс");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
