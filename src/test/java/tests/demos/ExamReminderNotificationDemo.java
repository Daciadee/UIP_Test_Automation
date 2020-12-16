package tests.demos;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.CertificationsPage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.admin.AdminCEHManageWorkshopsPage;
import pages.admin.AdminKnowledgeExamPage;
import pages.admin.AdminPerformanceInterpreterPage;
import pages.ceh.CEHApprovalRequestPage;
import pages.ceh.CEHPage;
import pages.ceh.CEHUploadDocumentationPage;
import pages.dashboard.DashboardPage;
import pages.dashboard.DashboardRaterPage;
import utils.Queries;
import utils.TestHelpers;

import java.net.MalformedURLException;

import static utils.GetProperties.*;
import static utils.GetProperties.PAYMENT_APPROVAL;
import static utils.GetProperties.THIRD_PARTY;

public class ExamReminderNotificationDemo {

    private WebDriver driver;
    private AdminKnowledgeExamPage adminKE;
    private AdminPerformanceInterpreterPage adminPI;
    private CertificationsPage certification;
    private DashboardPage dashboard;
    private DashboardRaterPage drp;
    private LoginPage login;
    private Queries queries;
    private RegistrationPage registration;
    private TestHelpers th;
    private CEHPage cehPage;
    private CEHApprovalRequestPage cehApproval;
    private CEHUploadDocumentationPage cehUpload;
    private AdminCEHManageWorkshopsPage adminManageWorkshop;
    private String LAST_NAME = "User";

    @Parameters({"browser"})
    @BeforeClass
    private void beforeClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);

        certification = new CertificationsPage(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
        th = new TestHelpers(driver);

        adminKE = new AdminKnowledgeExamPage(driver);
        adminPI = new AdminPerformanceInterpreterPage(driver);
        dashboard = new DashboardPage(driver);
        drp = new DashboardRaterPage(driver);
        queries = new Queries();
        cehPage = new CEHPage(driver);
        cehApproval = new CEHApprovalRequestPage(driver);
        cehUpload = new CEHUploadDocumentationPage(driver);
        adminManageWorkshop = new AdminCEHManageWorkshopsPage(driver);

        // Mallory Seamons Email
        USER_EMAIL = "uiptester27@gmail.com";
        LOGIN_TYPE = "gmail";
    }

    @Test
    public void setup() {
        // Register User
        registration.unregisterInterpreter(USER_EMAIL);
        login.loginToUIP(LOGIN_TYPE);

        if (!registration.checkIfRegistered(USER_EMAIL)) {
            registration.registerWithRandomData("Test", LAST_NAME);
        }
        certification.selectCertification("professional");

        // Sign Knowledge Exam Agreement
        dashboard.agreeToHonorStatement();

        // Pay for Knowledge Exam
        switch (KNOWLEDGE_PAYMENT_TYPE) {
            case "credit":
                dashboard.payForKnowledgeExamWithCard();
                break;
            case "voucher":
                dashboard.payForKnowledgeExamWithVoucher(LAST_NAME, USER_PATH + VOUCHER_PATH, PAYMENT_APPROVAL);
                break;
            case "third party":
                dashboard.payForKnowledgeExamWithThirdParty(LAST_NAME, THIRD_PARTY, PAYMENT_APPROVAL);
                break;
            default:
                dashboard.payForKnowledgeExamWithCard();
                break;
        }

        // Schedule Knowledge Exam
        certification.professionalScheduleKELnk.click();
        dashboard.scheduleKnowledgeExam();

        // Approve Knowledge Exam
        adminKE.checkInInterpreterForKnowledgeExam(LAST_NAME);

        // Take Knowledge Exam
        dashboard.completeKnowledgeExam(KNOWLEDGE_EXAM_SCORE);
        certification.professionalPEAgreementLnk.click();

        // Sign Performance Exam Agreement
        dashboard.signPerformanceExamAgreement();

        // Pay for Performance Exam
        switch (PERFORMANCE_PAYMENT_TYPE) {
            case "credit":
                dashboard.payForPerformanceExamWithCard();
                break;
            case "voucher":
                dashboard.payForPerformanceExamWithVoucher(LAST_NAME, USER_PATH + VOUCHER_PATH, PAYMENT_APPROVAL);
                break;
            case "third party":
                dashboard.payForPerformanceExamWithThirdParty(LAST_NAME, THIRD_PARTY, PAYMENT_APPROVAL);
                break;
            default:
                dashboard.payForPerformanceExamWithCard();
                break;
        }

        // Schedule Performance Exam
        certification.professionalSchedulePELnk.click();
        dashboard.schedulePerformanceExam("professional");
    }
}
