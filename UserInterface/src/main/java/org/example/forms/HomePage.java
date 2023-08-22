package org.example.forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class HomePage extends Form {
    private static final By pageUniqueLocator = By.xpath("//button[@class='start__button']");
    private ILink hereLink = getElementFactory().getLink(By.xpath("//a[@class='start__link']"), "HERE link");

    public HomePage() {
        super(pageUniqueLocator, "Home page");
    }

    public void clickOnHereLink() {
        hereLink.click();
    }
}
