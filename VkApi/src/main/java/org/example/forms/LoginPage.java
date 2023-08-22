package org.example.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginPage extends Form {
    private static final By pageUniqueLocator = By.xpath("//div[@class='VkIdForm']");
    private final ITextBox loginInputField = getElementFactory().getTextBox(By.xpath("//input[@id='index_email']"), "Login input field");
    private final IButton signInButton = getElementFactory().getButton(By.xpath("//button[ contains(@class, 'VkIdForm__signInButton') and @type='submit' ]"), "Sign in button");

    public LoginPage() {
        super(pageUniqueLocator, "Login page");
    }

    public void enterLoginInfo(String login) {
        loginInputField.sendKeys(login);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}
