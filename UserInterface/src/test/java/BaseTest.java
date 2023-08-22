import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.example.utils.MyConstants.*;

public abstract class BaseTest {

    @BeforeMethod
    public void setUp() {
        ISettingsFile config = new JsonSettingsFile("config.json");
        String url = config.getValue(URL_KEY).toString();
        AqualityServices.getBrowser().maximize();
        AqualityServices.getBrowser().goTo(url);
        AqualityServices.getBrowser().waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown() {
        AqualityServices.getBrowser().quit();
    }

}
