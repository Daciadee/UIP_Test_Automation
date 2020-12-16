package tests.pages;

import base.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegistrationPage;

import static utils.GetProperties.*;

public class RegistrationPageTests extends TestBase {

    private RegistrationPage registrationPage;
    private LoginPage loginPage;

    @BeforeClass
    private void beforeRegistrationClass() throws Exception {
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);

        driver.navigate().to(BASE_URL);
//        loginPage.logIntoAppWithUtahId();
    }


    @Test
    public void registerMultipleInterpretersTest() throws Exception {
        for (int i = 0; i < 10; i++) {
            registrationPage.registerWithRandomData();
            registrationPage.unregisterInterpreter(USER_EMAIL);
            driver.manage().deleteAllCookies();

            System.out.println((i + 1) + " registrations processed.");

            driver.navigate().to(BASE_URL);
            loginPage.logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
        }
    }

    @Test
    public void registerSingleInterpreterTest() {
        registrationPage.unregisterInterpreter(USER_EMAIL);
        loginPage.logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
        registrationPage.registerWithRandomData();
    }

    @Test
    public void unregisterInterpreter() {
        registrationPage.unregisterInterpreter(USER_EMAIL);
    }

//    @AfterClass
    public void afterClass() {
        driver.close();
    }

}
