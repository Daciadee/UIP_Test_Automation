package tests.pages;

import base.TestBase;
import org.testng.annotations.*;
import pages.LoginPage;

import static utils.GetProperties.*;

public class LoginPageTest extends TestBase {

    private LoginPage loginPage;

    @BeforeClass
    public void start() {
        driver.navigate().to(BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginToUipTest() throws Exception {
        loginPage.logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
    }

}
