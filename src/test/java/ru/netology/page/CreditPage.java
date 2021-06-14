package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private final SelenideElement numberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));
    private final SelenideElement success = $$(".notification__title").find(text("Успешно"));
    private final SelenideElement error = $$(".notification__title").find(text("Ошибка"));
    private final SelenideElement numberUnderField = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthUnderField = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearUnderField = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement ownerUnderField = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvcUnderField = $(byText("CVC/CVV")).parent().$(".input__sub");

    public CreditPage() {
        SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
        heading.shouldBe(visible);
    }

    public CreditPage sendData(String number, String month, String year, String owner, String cvc) {
        numberField.setValue(number);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
        continueButton.click();
        return new CreditPage();
    }

    public CreditPage sendData(DataHelper.CardInfo cardInfo) {
        numberField.setValue(cardInfo.getNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCvc());
        continueButton.click();
        return new CreditPage();
    }

    // проверка на наличия сообщения об успешности операции
    public void checkSuccess() {
        success.waitUntil(visible, 15000);
        error.shouldBe(hidden);
    }

    // проверка на наличия сообщения об ошибке
    public void checkError() {
        error.waitUntil(visible, 15000);
        success.shouldBe(hidden);
    }

    // возвращение текста сообщения под полем "Номер"
    public String getTextUnderNumberField() {
        return numberUnderField.innerText();
    }

    // возвращение текста сообщения под полем "Месяц"
    public String getTextUnderMonthField() {
        return monthUnderField.innerText();
    }

    // возвращение текста сообщения под полем "Год"
    public String getTextUnderYearField() {
        return yearUnderField.innerText();
    }

    // возвращение текста сообщения под полем "Владелец"
    public String getTextUnderOwnerField() {
        return ownerUnderField.innerText();
    }

    // возвращение текста сообщения под полем "CVC\CVV"
    public String getTextUnderCvcField() {
        return cvcUnderField.innerText();
    }
}
