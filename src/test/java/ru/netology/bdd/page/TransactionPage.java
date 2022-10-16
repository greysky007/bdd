package ru.netology.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransactionPage {
    private SelenideElement amountField = $x("//div[@data-test-id = 'amount']//input");
    private SelenideElement fromField = $x("//span[@data-test-id = 'from']//input");
    private SelenideElement buttonTrans = $x("//button[@data-test-id='action-transfer']");

    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");

    public DashboardPage transfer(int amount, String cardNumber) {
        amountField.setValue(Integer.toString(amount));
        fromField.setValue(cardNumber);
        buttonTrans.click();
        return new DashboardPage();
    }

    public void getNotification() {

        errorNotification.shouldBe(Condition.visible);
        errorNotification.shouldHave(Condition.exactText("Ошибка\n" +
                "Ошибка! Произошла ошибка"));


    }
}
