package tests.certifications;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CertificationsPage;

import java.net.MalformedURLException;

import static utils.GetProperties.USER_EMAIL;

public class NationalCertificationTests {

    private WebDriver driver;
    private CertificationsPage certification;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);
        certification = new CertificationsPage(driver);
    }

    @Test
    public void setCertificationExpirationDate() {
        certification.setCycleDates(USER_EMAIL, "09/15/2017", "10/07/2018");
        certification.setCertificationRecognizedDate(USER_EMAIL, "Professional", "09/15/2017");
        certification.setCertificationExpirationDate(USER_EMAIL, "Professional", "10/31/2018");
        certification.setCertificationRenewalDate(USER_EMAIL, "10/31/2018");

        // *Be sure to run batch job after executing
    }

    @Test
    public void setRenewalDate() {
        certification.setCertificationRenewalDate(USER_EMAIL, "10/31/2018");
        certification.setCertificationExpirationDate(USER_EMAIL, "Novice", "10/31/2018");
    }


}
