package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.page.CreditPage;
import ru.netology.page.MainApp;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class CreditTest {

    MainApp mainApp;
    CreditPage creditPage;
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
        mainApp.getCreditPage();
        creditPage = new CreditPage();
    }

    @Test
    void shouldPayWithApprovedCard() {
        creditPage.sendData(validCard);
        creditPage.checkSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/allFields.csv", numLinesToSkip = 1)
    void shouldPayWithApprovedCardOtherFields(String num, String month, String year, String owner, String cvc) {
        creditPage.sendData(num, month, year, owner, cvc);
        creditPage.checkSuccess();
    }

    @Test
    void shouldErrorDeclinedCard() {
        creditPage.sendData(invalidCard);
        creditPage.checkError();
    }

    @Test
    void shouldErrorAllEmpty() {
        creditPage.sendData("", "", "", "", "");
        String[] actual = {creditPage.getTextUnderNumberField(),
                creditPage.getTextUnderMonthField(),
                creditPage.getTextUnderYearField(),
                creditPage.getTextUnderOwnerField(),
                creditPage.getTextUnderCvcField()};
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
        creditPage.sendData(num, validMonth, validYear, validOwner, validCvc);
        val actual = creditPage.getTextUnderNumberField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/monthField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectMonth(String month, String expected) {
        creditPage.sendData(validNumber, month, validYear, validOwner, validCvc);
        val actual = creditPage.getTextUnderMonthField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/yearField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectYear(String year, String expected) {
        creditPage.sendData(validNumber, validMonth, year, validOwner, validCvc);
        val actual = creditPage.getTextUnderYearField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ownerField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectOwner(String owner, String expected) {
        creditPage.sendData(validNumber, validMonth, validYear, owner, validCvc);
        val actual = creditPage.getTextUnderOwnerField();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/cvcField.csv", numLinesToSkip = 1)
    void shouldErrorIncorrectCvc(String cvc, String expected) {
        creditPage.sendData(validNumber, validMonth, validYear, validOwner, cvc);
        val actual = creditPage.getTextUnderCvcField();
        assertEquals(expected, actual);
    }
}