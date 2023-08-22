package org.example.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class HelpForm extends Form {
    private static final By pageUniqueLocator = By.xpath("//div [ @class = 'help-form' ]");
    private IButton hideHelpButton = getElementFactory().getButton(By.xpath("//button [  contains(  @class, 'send-to-bottom'  )  ]"), "Hide help button");
    private ITextBox helpFormDiv = getElementFactory().getTextBox(By.xpath("//div [ @class = 'help-form' ]"), "Help form div");

    public HelpForm() {
        super(pageUniqueLocator, "Help form");
    }

    public void clickOnHideHelpButton() {
        hideHelpButton.click();
        helpFormDiv.state().waitForNotDisplayed();
    }

}
