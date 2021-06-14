package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.page.MainApp;
import ru.netology.page.PurchasePage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class PurchaseTest {

    MainApp mainApp;
    PurchasePage purchasePage;
    CardInfo validCard = getApprovedCard();
    CardInfo invalidCard = getDeclinedCard();
    String validNumber = getValidNumber();
    String validMonth = getMonth();
    String validYear = getYear(3);
    String validOwner = getOwner();
    String validCvc = getCvc();

    @BeforeEach
    void setUp() {
        mainApp = new MainApp();
        mainApp.getPurchasePage();
        purchasePage = new PurchasePage();
    }


    @Test
    void shouldPayWithApprovedCard() {
        purchasePage.sendData(validCard);
        purchasePage.checkSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/allFields.csv", numLinesToSkip = 1)
    void shouldPayWithApprovedCardOtherFields(String num, String month, String year, String owner, String cvc) {
        purchasePage.sendData(num, month, year, owner, cvc);
        purchasePage.checkSuccess();
    }

    @Test
    void shouldGetErrorWithDeclinedCard() {
        purchasePage.sendData(invalidCard);
        purchasePage.checkError();
    }

    @Test
    void shouldErrorAllEmpty() {
        purchasePage.sendData("", "", "", "", "");
        String[] actual = {purchasePage.getTextUnderNumberField(),
                purchasePage.getTextUnderMonthField(),
                purchasePage.getTextUnderYearField(),
                purchasePage.getTextUnderOwnerField(),
                purchasePage.getTextUnderCvcField()};
        String[] expected = {"Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения",
                "Поле обязательно для заполнения"};
        assertArrayEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numberField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectNumber(String num, String expected) {
        purchasePage.sendData(num, validMonth, validYear, validOwner, validCvc);
        val actual = purchasePage.getTextUnderNumberField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/monthField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectMonth(String month, String expected) {
        purchasePage.sendData(validNumber, month, validYear, validOwner, validCvc);
        val actual = purchasePage.getTextUnderMonthField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/yearField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectYear(String year, String expected) {
        purchasePage.sendData(validNumber, validMonth, year, validOwner, validCvc);
        val actual = purchasePage.getTextUnderYearField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ownerField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectOwner(String owner, String expected) {
        purchasePage.sendData(validNumber, validMonth, validYear, owner, validCvc);
        val actual = purchasePage.getTextUnderOwnerField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cvcField.csv", numLinesToSkip = 1)
    void shouldErrorInvalidCvc(String cvc, String expected) {
        purchasePage.sendData(validNumber, validMonth, validYear, validOwner, cvc);
        val actual = purchasePage.getTextUnderCvcField();
        assertEquals(expected, actual);
    }
}