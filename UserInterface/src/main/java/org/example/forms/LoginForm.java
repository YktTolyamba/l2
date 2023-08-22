package org.example.forms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class LoginForm extends Form {
    private static final By pageUniqueLocator = By.xpath("//div[@class='login-form']");
    private By tldDropdownListItem = By.xpath("//div[@class = 'dropdown__list-item']");
    private ITextBox passwordField = getElementFactory().getTextBox(By.xpath("//input[contains(@placeholder, 'Password')]"), "Password field");
    private ITextBox emailField = getElementFactory().getTextBox(By.xpath("//input[contains(@placeholder, 'email')]"), "Email field");
    private ITextBox domainField = getElementFactory().getTextBox(By.xpath("//input[contains(@placeholder, 'Domain')]"), "Domain field");
    private ITextBox timer = getElementFactory().getTextBox(By.xpath("//div [ contains (@class, 'timer') ]"), "Timer");
    private IButton tldDropdownButton = getElementFactory().getButton(By.xpath("//div[@class = 'dropdown__header']"), "Dropdown header");
    private IButton nextButton = getElementFactory().getButton(By.xpath("//a[@class = 'button--secondary']"), "Next button");
    private IButton acceptCookiesButton = getElementFactory().getButton(By.xpath("//div[@class = 'cookies' ]//descendant :: button[contains(text(),'no')]"), "Accept cookies button");
    private IComboBox tldDropdownList = getElementFactory().getComboBox(By.xpath("//div[@class = 'dropdown__list']"), "Dropdown list");
    private ICheckBox checkbox = getElementFactory().getCheckBox(By.xpath("//span[@class = 'checkbox__box']"), "Terms & Conditions check box");
    private List<IButton> tldDropdownListItems = new ArrayList<>();

    public LoginForm() {
        super(pageUniqueLocator, "Login form");
    }

    public void clearAndTypeInPasswordField(String text) {
        passwordField.clearAndType(text);
    }

    public void clearAndTypeInEmailField(String text) {
        emailField.clearAndType(text);
    }

    public void clearAndTypeInDomainField(String text) {
        domainField.clearAndType(text);
    }

    public void clickOnTldDropdown() {
        tldDropdownButton.click();
        tldDropdownList.state().waitForDisplayed();
    }

    public void chooseTLD(int tldIndex) {
        tldDropdownListItems = getElementFactory().findElements(tldDropdownListItem, ElementType.BUTTON);
        tldDropdownListItems.get(tldIndex).click();
        tldDropdownList.state().waitForNotDisplayed();
    }

    public Integer getSizeOfTldDropdownList() {
        tldDropdownListItems = getElementFactory().findElements(tldDropdownListItem, ElementType.BUTTON);
        return tldDropdownListItems.size();
    }

    public void clickCheckbox() {
        checkbox.check();
    }

    public void clickOnNextButton() {
        nextButton.click();
    }

    public void clickOnAcceptCookiesButton() {
        acceptCookiesButton.click();
    }

    public boolean isAcceptCookiesButtonOpen() {
        return acceptCookiesButton.state().isDisplayed();
    }

    public String getTimerText() {
        return timer.getText();
    }
}
