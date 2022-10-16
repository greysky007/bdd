package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

        private SelenideElement loginField = $x("//span[@data-test-id='login']//input");
        private SelenideElement passwordField = $("[data-test-id=password] input");
        private SelenideElement loginButton = $x("//button[@data-test-id='action-login']");

        public VerificationPage validLogin(DataHelper.AuthInfo info) {
            loginField.setValue(info.getLogin());
            passwordField.setValue(info.getPassword());
            loginButton.click();
            return new VerificationPage();
        }
    }

