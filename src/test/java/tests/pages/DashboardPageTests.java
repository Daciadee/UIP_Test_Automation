package tests.pages;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.dashboard.DashboardPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.TestHelpers;

import java.net.MalformedURLException;

import static utils.GetProperties.*;

public class DashboardPageTests {

    private WebDriver driver;
    private LoginPage login;
    private RegistrationPage registration;
    private DashboardPage dashboard;
    private TestHelpers th;
    private String lastName;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeDashboardClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);

        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);

        registration.unregisterInterpreter(USER_EMAIL);
        login.logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
        registration.registerWithRandomData();

        dashboard = new DashboardPage(driver);

        th = new TestHelpers(driver);
        lastName = th.getLastName();
    }

    // End to End Functional Tests //
    @Test
    public void signHonorStatementTest() {
        dashboard.agreeToHonorStatement();
    }

    @Test
    public void payForKnowledgeExamWithCreditCardTest() {
        dashboard.payForKnowledgeExamWithCard();
    }

    @Test
    public void payForKnowledgeExamWithThirdPartyTest() {
        dashboard.payForKnowledgeExamWithThirdParty(lastName, THIRD_PARTY, PAYMENT_APPROVAL);
    }

    @Test
    public void payForKnowledgeExamWithVoucherTest() {
        dashboard.payForKnowledgeExamWithVoucher(lastName, USER_PATH + VOUCHER_PATH, PAYMENT_APPROVAL);
    }

    @Test
    public void scheduleKnowledgeExamTest() {
        dashboard.scheduleKnowledgeExam();
    }

    @Test
    public void newAlertGeneratedTest() {
        dashboard.checkForNewAlerts();
    }

    @Test
    public void completeKnowledgeExamTest() {
        dashboard.completeKnowledgeExam(79);
    }

    @Test
    public void signPerformanceAgreementTest() {
        dashboard.signPerformanceExamAgreement();
    }

    @Test
    public void payForPerformanceExamWithCreditCardTest() {
        dashboard.payForPerformanceExamWithCard();
    }

    @Test
    public void payForPerformanceExamWithThirdPartyTest() {
        dashboard.payForPerformanceExamWithThirdParty(lastName, THIRD_PARTY, PAYMENT_APPROVAL);
    }

    @Test
    public void payForPerformanceExamWithVoucherTest() {
        dashboard.payForPerformanceExamWithVoucher(lastName,USER_PATH + VOUCHER_PATH, PAYMENT_APPROVAL);
    }

    @Test
    public void schedulePerformanceExamTest() {
        dashboard.schedulePerformanceExam(CERT_TYPE);
    }

    @Test
    public void completePerformanceExamTest() {
        dashboard.completePerformanceExam(CERT_TYPE, lastName, PAYMENT_APPROVAL);
    }

    @Test
    public void signContractTest() {
        dashboard.signContract(CERT_TYPE, lastName, LOGIN_TYPE);
    }



}
