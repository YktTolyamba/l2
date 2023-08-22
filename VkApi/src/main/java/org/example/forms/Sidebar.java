package org.example.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class Sidebar extends Form {
    private static final By pageUniqueLocator = By.xpath("//nav[@class='side_bar_nav']");
    private final IButton myProfileButton = getElementFactory().getButton(By.xpath("//li[@id='l_pr']"), "My profile button");

    public Sidebar() {
        super(pageUniqueLocator, "Sidebar");
    }

    public void clickMyProfileButton() {
        myProfileButton.click();
    }
}
