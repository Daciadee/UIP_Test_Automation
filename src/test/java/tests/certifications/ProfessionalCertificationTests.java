package tests.certifications;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import pages.admin.AdminInboxPage;
import pages.admin.AdminKnowledgeExamPage;
import pages.dashboard.DashboardPage;
import pages.admin.AdminPerformanceInterpreterPage;
import pages.dashboard.DashboardRaterPage;
import utils.Queries;
import utils.TestHelpers;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import static utils.GetProperties.*;
import static utils.GetProperties.PAYMENT_APPROVAL;
import static utils.GetProperties.VOUCHER_PATH;

public class ProfessionalCertificationTests {

    private WebDriver driver;
    private AdminInboxPage adminInboxPage;
    private AdminKnowledgeExamPage adminKE;
    private AdminPerformanceInterpreterPage adminPI;
    private CertificationsPage certification;
    private DashboardPage dashboard;
    private DashboardRaterPage drp;
    private LoginPage login;
    private Queries queries;
    private RegistrationPage registration;
    private TestHelpers th;
    private String lastName;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeDashboardClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);

        certification = new CertificationsPage(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
        adminInboxPage = new AdminInboxPage(driver);
        adminKE = new AdminKnowledgeExamPage(driver);
        adminPI = new AdminPerformanceInterpreterPage(driver);
        dashboard = new DashboardPage(driver);
        drp = new DashboardRaterPage(driver);
        queries = new Queries();
        th = new TestHelpers(driver);
    }

    // End to End Functional Tests //

    @Test
    public void logIntoUIP() {
        if (REGISTER_NEW_USER) {
            registration.unregisterInterpreter(USER_EMAIL);
        }
        login.loginToUIP(LOGIN_TYPE);
    }

    @Test
    public void registerUser() {
        if (!registration.checkIfRegistered(USER_EMAIL)) {
            registration.registerWithRandomData();
        }
        certification.selectCertification(CERT_TYPE);
        lastName = queries.getInterpreterLastName();
    }

    @Test
    public void watchOrientationVideo() {
        dashboard.watchOrientationVideo();
    }

    @Test
    public void signHonorStatementTest() {
        dashboard.agreeToHonorStatement();
    }

    @Test
    public void payForKnowledgeExam() {
        switch (KNOWLEDGE_PAYMENT_TYPE) {
            case "credit":
                dashboard.payForKnowledgeExamWithCard();
                break;
            case "voucher":
                dashboard.payForKnowledgeExamWithVoucher(lastName, VOUCHER_PATH, PAYMENT_APPROVAL);
                break;
        }
    }

    @Test
    public void scheduleKnowledgeExamTest() {
        certification.certificationTab.click();
        certification.pendingLnk.click();
        dashboard.scheduleKnowledgeExam();
    }

    @Test
    public void newAlertGeneratedTest() {
        dashboard.checkForNewAlerts();
    }

    @Test
    public void checkInInterpreterForKnowledgeExamTest() {
        adminKE.checkInInterpreterForKnowledgeExam(th.getLastName());
    }

    @Test
    public void takeKnowledgeExamTest() {
        dashboard.completeKnowledgeExam(KNOWLEDGE_EXAM_SCORE);
        certification.certificationTab.click();
        certification.pendingLnk.click();
    }

    @Test
    public void takeKnowledgeExamTestProd() {
        dashboard.completeKnowledgeExamByIds(USER_EMAIL,KNOWLEDGE_EXAM_SCORE);
        switch (CERT_TYPE) {
            case "professional":
                certification.professionalPEAgreementLnk.click();
                break;
            case "novice":
                certification.novicePEAgreementLnk.click();
                break;
        }
    }

    @Test
    public void completeKnowledgeExamNoQuestionsTest() {
        dashboard.completeKnowledgeExamLnk.click();
    }

    @Test
    public void signPerformanceAgreementTest() {
        dashboard.signPerformanceExamAgreement();
    }

    @Test
    public void payForPerformanceExam() {
        certification.certificationTab.click();
        switch (PERFORMANCE_PAYMENT_TYPE) {
            case "credit":
                dashboard.payForPerformanceExamWithCard();
                break;
            case "voucher":
                dashboard.payForPerformanceExamWithVoucher(lastName, VOUCHER_PATH, PAYMENT_APPROVAL);
                break;
        }
    }

    @Test
    public void schedulePerformanceExamTest() {
        certification.certificationTab.click();
        certification.pendingLnk.click();
        dashboard.schedulePerformanceExam(CERT_TYPE);
    }

    @Test
    public void assignVideosAndRatersToInterpreterTest() {
        adminPI.openInterpreterTab();
        adminPI.assignVideosAndRaters(th.getLastName());
    }

    @Test
    public void rateAllPerformanceExamsTest() {
        List<String> raters = new LinkedList<>();
        raters.add(DEAF_RATER_1_COOKIE);
        raters.add(DEAF_RATER_2_COOKIE);
        raters.add(DEAF_RATER_3_COOKIE);
        raters.add(ENGLISH_RATER_1_COOKIE);
        raters.add(ENGLISH_RATER_2_COOKIE);
        raters.add(ENGLISH_RATER_3_COOKIE);
        raters.add(INTERPRETER_RATER_1_COOKIE);
        raters.add(INTERPRETER_RATER_2_COOKIE);
        raters.add(INTERPRETER_RATER_3_COOKIE);

        drp.rateAllPerformanceExams(th.getLastName(), PERFORMANCE_EXAM_RESULT, raters);
    }

    @Test
    public void completePerformanceExamTest() {
        dashboard.completePerformanceExam(CERT_TYPE, lastName, PAYMENT_APPROVAL);
        dashboard.completePerformanceExamLnk.click();
    }

    @Test
    public void retakeFailedPerformanceExamAndPassTest() {
        if (RETAKE_FAILED_EXAM && PERFORMANCE_EXAM_SCORE <= 85) {
            queries.resetPerformanceExamSchedule();
            login.logBackInToUIP(LOGIN_TYPE);
            certification.actionRequiredLnks.get(0).click();
            payForPerformanceExam();
            schedulePerformanceExamTest();
            assignVideosAndRatersToInterpreterTest();

            List<String> raters = new LinkedList<>();
            raters.add(DEAF_RATER_1_COOKIE);
            raters.add(DEAF_RATER_2_COOKIE);
            raters.add(DEAF_RATER_3_COOKIE);
            raters.add(ENGLISH_RATER_1_COOKIE);
            raters.add(ENGLISH_RATER_2_COOKIE);
            raters.add(ENGLISH_RATER_3_COOKIE);
            raters.add(INTERPRETER_RATER_1_COOKIE);
            raters.add(INTERPRETER_RATER_2_COOKIE);
            raters.add(INTERPRETER_RATER_3_COOKIE);

            drp.rateAllPerformanceExams(th.getLastName(), PERFORMANCE_EXAM_RESULT, raters);
        }
    }

    @Test
    public void confirmPassingPerformanceExamTest() {
        adminInboxPage.sendPerformanceTestCompletionEmail(lastName);
    }

    @Test
    public void signContractTest() {
        dashboard.signContract(CERT_TYPE, lastName, LOGIN_TYPE);
    }

}
