package tests.pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ExamSchedulePage;
import pages.dashboard.DashboardPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.FileUtilities;
import utils.TestHelpers;

import java.net.MalformedURLException;

import static utils.GetProperties.*;

public class ExamSchedulePageTests {

    private WebDriver driver;
    private DashboardPage dashboard;
    private ExamSchedulePage calendar;
    private TestHelpers th;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeDashboardClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase testBase = new TestBase();
        driver = testBase.getDriver(browser);

        LoginPage loginPage = new LoginPage(driver);
        RegistrationPage registration = new RegistrationPage(driver);
        dashboard = new DashboardPage(driver);
        calendar = new ExamSchedulePage(driver);
        th = new TestHelpers(driver);

        registration.unregisterInterpreter(USER_EMAIL);
        loginPage.logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
        registration.registerWithRandomData();

        dashboard.agreeToHonorStatement();

        TestHelpers th = new TestHelpers(driver);
        String lastName = th.getLastName();
        dashboard.payForKnowledgeExamWithThirdParty(lastName, THIRD_PARTY, PAYMENT_APPROVAL);
    }

    @Test
    public void multipleClickExamTimeTest() {
        try {
            dashboard.scheduleKnowledgeExamLnk.click();
            calendar.availableKnowledgeExams.get(0).click();
            for (int i=0; i<2; i++) {
                calendar.selectedKnowledgeExams.get(0).click();
                System.out.println(i);
            }

            calendar.calendarTodayLnk.click();
            WebElement seats = driver.findElement(By.xpath(th.getXPathTextFromElement(calendar.reservedKnowledgeExams.get(0)) + "/div[@class='dwscal-event-seats']"));
            int seatNum = Integer.valueOf(seats.getText().substring(0,1));

            Assert.assertTrue(seatNum > 0, "Seats left are 0, should be more.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
