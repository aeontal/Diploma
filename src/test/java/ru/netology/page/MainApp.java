package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainApp {

    private final String mailUrl = System.getProperty("sut.url");
    private final SelenideElement CardButton = $(byText("Купить"));
    private final SelenideElement CreditButton = $(byText("Купить в кредит"));

    public MainApp() {
        open(mailUrl);
        SelenideElement heading = $("h2").shouldHave(text("Путешествие дня"));
        heading.shouldBe(visible);
    }

    public PurchasePage getPurchasePage() {
        CardButton.click();
        return new PurchasePage();
    }

    public CreditPage getCreditPage() {
        CreditButton.click();
        return new CreditPage();
    }
}
