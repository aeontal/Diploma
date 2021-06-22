package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainApp;
import ru.netology.page.PurchasePage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBUtils.*;

public class DatabaseTest {

    MainApp mainApp;
    PurchasePage purchasePage;
    CreditPage creditPage;
    DataHelper.CardInfo validCard = DataHelper.getApprovedCard();
    DataHelper.CardInfo invalidCard = DataHelper.getDeclinedCard();
    private final String APPROVED_STATUS = "APPROVED";
    private final String DECLINED_STATUS = "DECLINED";
    private final int tourAmount = 4500000;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setUp() {
        mainApp = new MainApp();
    }


    @AfterEach
    void clearAll() {
        clearDBTables();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldPayWithApprovedCard() {
        mainApp.getPurchasePage();
        purchasePage = new PurchasePage();
        purchasePage.sendData(validCard);
        purchasePage.checkSuccess();
        var paymentRecord = getLastPayment();
        var orderRecord = getLastOrder();
        assertAll(
                () -> assertEquals(tourAmount, paymentRecord.getAmount()),
                () -> assertEquals(APPROVED_STATUS, paymentRecord.getStatus()),
                () -> assertEquals(paymentRecord.getTransaction_id(), orderRecord.getPayment_id())
        );
    }

    @Test
    void shouldPayWithDeclinedCard() {
        mainApp.getPurchasePage();
        purchasePage = new PurchasePage();
        purchasePage.sendData(invalidCard);
        purchasePage.checkError();
        var paymentRecord = getLastPayment();
        var orderRecord = getLastOrder();
        assertAll(
                () -> assertEquals(tourAmount, paymentRecord.getAmount()),
                () -> assertEquals(DECLINED_STATUS, paymentRecord.getStatus()),
                () -> assertEquals(paymentRecord.getTransaction_id(), orderRecord.getPayment_id())
        );
    }

    @Test
    void shouldGetCreditWithApprovedCard() {
        mainApp.getCreditPage();
        creditPage = new CreditPage();
        creditPage.sendData(validCard);
        creditPage.checkSuccess();
        var creditRecord = getLastCredit();
        var orderRecord = getLastOrder();
        assertAll(
                () -> assertEquals(APPROVED_STATUS, creditRecord.getStatus()),
                () -> assertEquals(creditRecord.getBank_id(), orderRecord.getCredit_id())
        );
    }

    @Test
    void shouldGetCreditWithDeclinedCard() {
        mainApp.getCreditPage();
        creditPage = new CreditPage();
        creditPage.sendData(invalidCard);
        creditPage.checkError();
        var creditRecord = getLastCredit();
        var orderRecord = getLastOrder();
        assertAll(
                () -> assertEquals(DECLINED_STATUS, creditRecord.getStatus()),
                () -> assertEquals(creditRecord.getBank_id(), orderRecord.getCredit_id())
        );
    }
}
