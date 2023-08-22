import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.example.forms.*;
import org.example.utils.RandomUtils;
import org.example.utils.RobotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.utils.MyConstants.*;

public class TestCases extends BaseTest {

    @Test
    public void loginAvatarAndInterestsTest() {
        ISettingsFile config = new JsonSettingsFile("config.json");
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.state().isDisplayed(), "Welcome page is not displayed");
        homePage.clickOnHereLink();

        AqualityServices.getBrowser().waitForPageToLoad();
        LoginForm loginForm = new LoginForm();
        Assert.assertTrue( loginForm.state().isDisplayed(), "The '1' card is not open." );
        int start = Integer.parseInt(config.getValue(RANDOM_GENERATOR_START_KEY).toString());
        int end = Integer.parseInt(config.getValue(RANDOM_GENERATOR_END_KEY).toString());
        loginForm.clearAndTypeInPasswordField( RandomUtils.getPasswordOrEmail( start, end ) );
        loginForm.clearAndTypeInEmailField( RandomUtils.getPasswordOrEmail( start, end ) );
        loginForm.clearAndTypeInDomainField( RandomUtils.getDomain( start, end ) );
        loginForm.clickOnTldDropdown();
        loginForm.chooseTLD(RandomUtils.getTldIndex());
        loginForm.clickCheckbox();
        loginForm.clickOnNextButton();

        AqualityServices.getBrowser().waitForPageToLoad();
        AvatarAndInterestsForm avatarAndInterestsForm = new AvatarAndInterestsForm();
        Assert.assertTrue( avatarAndInterestsForm.state().isDisplayed(), "The '2' card is not open." );
        avatarAndInterestsForm.clickOnUploadButton();
        RobotUtils robotImage = new RobotUtils();
        robotImage.upload();
        avatarAndInterestsForm.waitForUploadedImageToAppear();

        int numberOfInterestCheckboxesToBeChecked = Integer.parseInt( config.getValue(NUMBER_OF_INTEREST_CHECK_BOXES_TO_BE_CHECKED_KEY).toString() );
        avatarAndInterestsForm.checkRandomInterests( numberOfInterestCheckboxesToBeChecked );
        avatarAndInterestsForm.clickNextButton();

        AqualityServices.getBrowser().waitForPageToLoad();
        PersonalDetailsForm personalDetailsForm = new PersonalDetailsForm();
        Assert.assertTrue( personalDetailsForm.state().isDisplayed(), "The '3' card is not open." );
    }

    @Test
    public void hideHelpFormTest() {
        HomePage homePage = new HomePage();
        Assert.assertTrue( homePage.state().isDisplayed(), "Welcome page is not displayed" );
        homePage.clickOnHereLink();

        AqualityServices.getBrowser().waitForPageToLoad();
        HelpForm helpForm = new HelpForm();
        helpForm.clickOnHideHelpButton();
        helpForm.state().waitForNotDisplayed();
        Assert.assertFalse( helpForm.state().isDisplayed(), "Form content is not hidden" );
    }

    @Test
    public void closeCookiesTest() {
        HomePage homePage = new HomePage();
        Assert.assertTrue( homePage.state().isDisplayed(), "Welcome page is not displayed" );
        homePage.clickOnHereLink();

        AqualityServices.getBrowser().waitForPageToLoad();
        LoginForm loginForm = new LoginForm();
        loginForm.clickOnAcceptCookiesButton();
        Assert.assertFalse( loginForm.isAcceptCookiesButtonOpen(), "Cookies form is not closed" );
    }

    @Test
    public void timerStartValueTest() {
        ISettingsFile environment = new JsonSettingsFile("testdata.json" );
        String timer = environment.getValue(TIMER_VALUE_KEY).toString();

        HomePage homePage = new HomePage();
        Assert.assertTrue( homePage.state().isDisplayed(), "Welcome page is not displayed" );
        homePage.clickOnHereLink();

        AqualityServices.getBrowser().waitForPageToLoad();
        LoginForm loginForm = new LoginForm();
        Assert.assertEquals( loginForm.getTimerText(), timer, "Timer doesn't start from '00:00:00'." );

    }

}
