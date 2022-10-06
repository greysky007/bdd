
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    public void shouldLogin() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }
    @Test
    public void shouldTransactionFromFirstToSecond() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        var firstBalance = dashboardPage.getFirstCardBalance();
        var secondBalance = dashboardPage.getSecondCardBalance();
        int cardIndex = 1;
        int amount = 150;
        dashboardPage.transferTo(cardIndex).Transfer(amount, DataHelper.getFirstCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstBalance - amount, currentBalanceFirst);
        Assertions.assertEquals(secondBalance + amount, currentBalanceSecond);
    }
    @Test
    public void shouldTransactionFromSecondToFirst() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        var firstBalance = dashboardPage.getFirstCardBalance();
        var secondBalance = dashboardPage.getSecondCardBalance();
        int cardIndex = 0;
        int amount = 250;
        dashboardPage.transferTo(cardIndex).Transfer(amount, DataHelper.getSecondCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstBalance + amount, currentBalanceFirst);
        Assertions.assertEquals(secondBalance - amount, currentBalanceSecond);
    }
    @Test
    public void shouldReturnErrorWithInvalidCard() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        int cardIndex = 0;
        int amount = 150;
        var transferPage = new TransactionPage();
        dashboardPage.transferTo(cardIndex)
                .Transfer(amount, "1234_1234_1234_1234");
        transferPage.getNotification().shouldBe(Condition.visible);
        transferPage.getNotification().shouldHave(Condition.exactText("Ошибка\n" +
                "Ошибка! Произошла ошибка"));
    }
}