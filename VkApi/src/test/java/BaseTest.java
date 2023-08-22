import aquality.selenium.browser.AqualityServices;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.example.constants.JsonKeys.*;
import static org.example.constants.JsonFiles.*;

public abstract class BaseTest {

    @BeforeMethod
    public void setUp() {
        String url = CONFIG.getValue(URL_KEY).toString();
        AqualityServices.getBrowser().maximize();
        AqualityServices.getBrowser().goTo(url);
        AqualityServices.getBrowser().waitForPageToLoad();
    }

    @AfterMethod
    public void tearDown() {
        AqualityServices.getBrowser().quit();
    }

}
