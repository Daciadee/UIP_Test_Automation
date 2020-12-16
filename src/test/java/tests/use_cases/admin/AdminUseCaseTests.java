package tests.use_cases.admin;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class AdminUseCaseTests {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);
    }


}
