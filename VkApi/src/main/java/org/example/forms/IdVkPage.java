package org.example.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class IdVkPage extends Form {
    private static final By pageUniqueLocator = By.xpath("//div[ @class = 'vkc__AuthRoot__contentIn' ]");
    private final ITextBox passwordInputField = getElementFactory().getTextBox(By.xpath("//input[@name='password']"), "Password input field");
    private final IButton continueButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Continue button");

    public IdVkPage() {
        super(pageUniqueLocator, "Login page");
    }

    public void enterPasswordInfo(String login) {
        passwordInputField.sendKeys(login);
    }

    public void clickContinueButton() {
        continueButton.click();
    }
}