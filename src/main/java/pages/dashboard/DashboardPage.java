package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.CertificationsPage;
import pages.GovPayPage;
import pages.KnowledgeExamPage;
import pages.LoginPage;
import pages.admin.AdminInboxPage;
import pages.admin.AdminPage;
import pages.admin.AdminPerformanceInterpreterPage;
import utils.FileUtilities;
import utils.Queries;
import utils.TestHelpers;

import java.util.List;

import static utils.GetProperties.*;

public class DashboardPage {

    private WebDriver driver;

    private AdminPage admin;
    private AdminInboxPage adminInbox;
    private AdminPerformanceInterpreterPage adminPI;
    private CertificationsPage certification;
    private FileUtilities fu;
    private KnowledgeExamPage kep;
    private LoginPage login;
    private Queries queries;
    private TestHelpers th;

    public DashboardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        th = new TestHelpers(driver);
        fu = new FileUtilities();
        admin = new AdminPage(driver);
        adminInbox = new AdminInboxPage(driver);
        adminPI = new AdminPerformanceInterpreterPage(driver);
        kep = new KnowledgeExamPage(driver);
        login = new LoginPage(driver);
        certification = new CertificationsPage(driver);
        queries = new Queries();
    }

    // Tabs
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a")
    public List<WebElement> mainTabs;
    @FindBy(xpath = "//a[@ui-sref='.certification']")
    public WebElement certificationsTab;

    // Dashboard Tabs
    // -- Certifications Tab --//
    // ** Orientation ** //
    @FindBy(xpath = "//a[@ng-click='watchOrientation()']")
    public WebElement watchOrientation;
    @FindBy(id = "pin")
    public WebElement videoPinTbx;
    @FindBy(xpath = "//button[@ng-click='verifyPin(pin)']")
    public WebElement verifyPinBtn;

    // ** Knowledge Exam ** //
    @FindBy(xpath = "//a[@ng-click='signHonorStatement()']")
    public WebElement signHonorStatementLnk;
    @FindBy(xpath = "//a[text()='Pay for Knowledge Exam']")
    public WebElement payForKnowledgeExamLnk;
    @FindBy(name = "confirmed")
    public WebElement paymentConfirmationCkb;
    @FindBy(xpath = "//a[contains(@ui-sref,'schedule')]")
    public WebElement scheduleKnowledgeExamLnk;
    @FindBy(xpath = "//a[text()='Complete Knowledge Exam ']")
    public WebElement completeKnowledgeExamLnk;
    @FindBy(xpath = "//a[@ng-click='signConfidentiality()']")

    // ** Performance Exam ** //
    public WebElement signPerformanceAgreementLnk;
    @FindBy(xpath = "//a[text()='Pay for Performance Exam ']")
    public WebElement payForPerformanceExamLnk;
    @FindBy(xpath = "//a[text()='Schedule Performance Exam ']")
    public WebElement schedulePerformanceExamLnk;
    @FindBy(xpath = "//a[text()='Complete Performance Exam ']")
    public WebElement completePerformanceExamLnk;
    @FindBy(xpath = "//a[contains(text(),'Sign Agreement')]")
    public WebElement signContractLnk;

    //-- Novice Specific --//
    @FindBy(xpath = "//a[text()='Schedule Practice Exam (Optional) ']")
    public WebElement schedulePracticeExamLnk;


    //-- Exam Alerts Box --//
    @FindBy(xpath = "//h4/following-sibling::div")
    public List<WebElement> alertBoxes;
    @FindBy(xpath = "//h4/following-sibling::div/div[@class='message']")
    public WebElement messageDetailsTxt;
    @FindBy(xpath = "//button[contains(text()='Reschedule'])")
    public WebElement rescheduleExamBtn;
    @FindBy(xpath = "//button[contains(text()='Dismiss')]")
    public WebElement dismissExamBtn;

    //-- Bottom Links --//
    @FindBy(xpath = "//a[@data-ng-click='switchIntent('NAT')']")
    public WebElement nationalLnk;
    @FindBy(xpath = "//a[@data-ng-click='switchIntent('NOV')']")
    public WebElement noviceLnk;
    @FindBy(xpath = "//a[contains(@ui-sref,'refund')]")
    public WebElement requestRefundLnk;

    // Honor Statement Sub-Page
    @FindBy(xpath = "//button[text()='Agree']")
    public WebElement agreeHonorBtn;
    @FindBy(xpath = "//button[text()='Decline']")
    public WebElement declineHonorBtn;

    // Pay for Exam Sub-Page
    @FindBy(id = "methodId")
    public WebElement paymentMethodCbx;
    @FindBy(xpath = "//select[@id='methodId']/option[@label]")
    public List<WebElement> paymentOptions;
    @FindBy(xpath = "//button[@data-ng-click='makePayment()']")
    public WebElement makePaymentByCreditBtn;
    @FindBy(xpath = "//input[@type='file']")
    public WebElement chooseFileBtn;
    @FindBy(xpath = "//button[@data-ng-click='makePaymentByVoucher()']")
    public WebElement makePaymentByVoucherBtn;
    @FindBy(id = "thirdPartyId")
    public WebElement thirdPartyTbx;
    @FindBy(xpath = "//button[@data-ng-click='makePaymentByThirdParty()']")
    public WebElement makePaymentByThirdPartyBtn;
    @FindBy(xpath = "//a[text()='Continue']")
    public WebElement paymentContinueBtn;

    // Schedule Knowledge Exam Sub-Page
    @FindBy(xpath = "//a[text()='Today']")
    public WebElement calendarTodayLnk;
    @FindBy(xpath = "//a[text()='Next ']")
    public WebElement calendarNextLnk;
    @FindBy(xpath = "//a[text()='Prev']")
    public WebElement calendarPreviousLnk;
    @FindBy(xpath = "//div[@class='dwscal-event available future']")
    public List<WebElement> availableKnowledgeExams;
    @FindBy(xpath = "//button[text()='Yes']")
    public WebElement yesBtn;
    @FindBy(xpath = "//button[text()='No']")
    public WebElement noBtn;

    // Performance Exam Statement Sub-Page
    @FindBy(xpath = "//button[@ng-click='acknowledge()']")
    public WebElement agreePerformanceBtn;
    @FindBy(xpath = "//button[@ng-click='decline()']")
    public WebElement declinePerformanceBtn;

    // Schedule Performance Exam Sub-Page
    @FindBy(xpath = "//div[@class='dwscal-event available future']")
    public List<WebElement> availablePerformanceExams;

    //Schedule Practice Exam Sub-Page
    @FindBy(xpath = "//div[@class='dwscal-event future available mock']")
    public List<WebElement> availablePracticeExams;


    //********************************************************//
    // Profile Tab
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a")
    public List<WebElement> profileTabs;

    // General Info Sub-Tab
    @FindBy(id = "fName")
    public WebElement firstNameTbx;
    @FindBy(id = "mName")
    public WebElement middleInitialTbx;
    @FindBy(id = "lName")
    public WebElement lastNameTbx;
    @FindBy(id = "address1")
    public WebElement address1Tbx;
    @FindBy(id = "address2")
    public WebElement address2Tbx;
    @FindBy(id = "interpreterCity")
    public WebElement cityTbx;
    @FindBy(id = "interpreterState")
    public WebElement stateCbx;
    @FindBy(id = "interpreterZip")
    public WebElement zipTbx;
    @FindBy(id = "phone")
    public WebElement phoneTbx;
    @FindBy(id = "email")
    public WebElement emailTbx;
    @FindBy(xpath = "//button[@ng-click='updateRecord()']")
    public WebElement updateBtn;
    @FindBy(xpath = "//button[@ng-click='cancelRecord()']")
    public WebElement cancelBtn;

    // CE Progress Sub-Tab
    @FindBy(xpath = "//a[@ui-sref='root.ce-approval']")
    public WebElement requestCeApprovalBtn;

    //********************************************************//
    // Rater Tab
    @FindBy(id = "raterId")
    public WebElement mockRaterCbx;
    @FindBy(xpath = "//a[@onclick=\"toggleTab('#scoring')\"]")
    public WebElement videosToScoreLnk;
    @FindBy(xpath = "//a[@ui-sref='.current']")
    public WebElement currentLnk;
    @FindBy(xpath = "//a[@ui-sref='.history']")
    public WebElement historyLnk;


    public void watchOrientationVideo() {
        try {
            watchOrientation.click();
            String pin = null;
            if(CERT_TYPE.equalsIgnoreCase("professional")) {
                pin = queries.getOrientationVideoPIN(10);
            } else if (CERT_TYPE.equalsIgnoreCase("novice")) {
                pin = queries.getOrientationVideoPIN(11);
            } else {
                pin = queries.getOrientationVideoPIN(14);
            }
            videoPinTbx.sendKeys(pin);
            Thread.sleep(500);
            verifyPinBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPaymentMethod(String option) {
        paymentMethodCbx.click();
        th.selectComboBoxOption(paymentMethodCbx, option);
    }

    public void agreeToHonorStatement() {
        signHonorStatementLnk.click();
        agreeHonorBtn.click();
        try {
            WebElement processingMessage = driver.findElement(By.xpath("//*[contains(text(),'Processing Agreement')]"));
            while (processingMessage.isDisplayed()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Processing complete.");
        }
    }

    public void declineHonorAgreement() {
        signHonorStatementLnk.click();
        declineHonorBtn.click();
    }

    public void payForExamWithCreditCard() {
        selectPaymentMethod(paymentOptions.get(0).getText());
        paymentConfirmationCkb.click();
        makePaymentByCreditBtn.click();

        GovPayPage gpp = new GovPayPage(driver);
        gpp.payByCreditCard();
    }

    private void payForExamWithVoucher(String lastName, String voucherPath, boolean approve) {
        try {
            selectPaymentMethod(paymentOptions.get(1).getText());
            chooseFileBtn.sendKeys(voucherPath);
            makePaymentByVoucherBtn.click();

            Thread.sleep(4000);
            if (approve) {
                adminInbox.loginToAdminAndApprovePayment(lastName,true);
            } else {
                adminInbox.loginToAdminAndApprovePayment(lastName,false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payForExamWithThirdParty(String lastName, String name, boolean approve) {
        try {
            selectPaymentMethod(paymentOptions.get(2).getText());

            thirdPartyTbx.sendKeys(name);
            makePaymentByThirdPartyBtn.click();

            if (approve) {
                adminInbox.loginToAdminAndApprovePayment(lastName, true);
            } else {
                adminInbox.loginToAdminAndApprovePayment(lastName, false);
            }
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForKnowledgeExamWithCard() {
        try {
            payForKnowledgeExamLnk.click();
            payForExamWithCreditCard();
            paymentContinueBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void payForKnowledgeExamWithThirdParty(String lastName, String partyName, boolean approve) {
        try {
            payForKnowledgeExamLnk.click();
            payForExamWithThirdParty(lastName, partyName, approve);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForKnowledgeExamWithVoucher(String lastName, String voucherPath, boolean approve) {
        try {
            payForKnowledgeExamLnk.click();
            payForExamWithVoucher(lastName, voucherPath, approve);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleExam(WebElement exam) {
        try {
            exam.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scheduleKnowledgeExam() {
        try {
            scheduleKnowledgeExamLnk.click();

            th.setImplicitWaitTime(driver, 1);
            Thread.sleep(1000);
            while (availableKnowledgeExams.size() < 1) {
                calendarNextLnk.click();
                Thread.sleep(1000);
            }

            scheduleExam(availableKnowledgeExams.get(0));

            th.restoreImplicitWaitTime(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void schedulePerformanceExam(String certType) {
        try {
            schedulePerformanceExamLnk.click();

            th.setImplicitWaitTime(driver, 1);
            Thread.sleep(1000);
            while (availablePerformanceExams.size() < 1) {
                calendarNextLnk.click();
                Thread.sleep(1000);
            }

            int reps = 0;
            if (certType.equals("professional")) {
                int examsFailed = queries.getNumberOfFailedPerformanceExams(USER_EMAIL);
                System.out.println("Performance Exams failed: " + examsFailed);

                if (examsFailed <= 3 && examsFailed > 0) {
                    reps = 1;
                } else {
                    reps = 2;
                }
            } else if (certType.equals("novice")) {
                reps = 1;
            }

            for (int i = 0; i < reps; i++) {
                Thread.sleep(1000);
                scheduleExam(availablePerformanceExams.get(i));
            }

            th.restoreImplicitWaitTime(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void schedulePracticeExam() {
        try {
            schedulePracticeExamLnk.click();
            scheduleExam(availablePracticeExams.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeKnowledgeExam(int score) {
        kep.scoreExamWithCorrectPercentage(score);
    }

    public void completeKnowledgeExamByIds(String email, int score) {
        kep.scoreExamWithCorrectPercentageByIds(email, score);
    }

    public void signPerformanceExamAgreement() {
        try {
            signPerformanceAgreementLnk.click();
            agreePerformanceBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForPerformanceExamWithCard() {
        try {
            payForPerformanceExamLnk.click();
            payForExamWithCreditCard();
            paymentContinueBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForPerformanceExamWithVoucher(String lastName, String voucherPath, boolean approve) {
        try {
            payForPerformanceExamLnk.click();
            payForExamWithVoucher(lastName, voucherPath, approve);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForPerformanceExamWithThirdParty(String lastName, String partyName, boolean approve) {
        try {
            payForPerformanceExamLnk.click();
            payForExamWithThirdParty(lastName, partyName, approve);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void completePerformanceExam(String certType, String lastName, boolean approve) {
        try {
            completePerformanceExamLnk.click();

            List<WebElement> passedExams = driver.findElements(By.xpath("//ul[contains(@ng-if,'progressInfo')]/li/div[contains(text(),'Pass')]"));

            if (passedExams.size() < 5) {
                payForPerformanceExamWithThirdParty(lastName, THIRD_PARTY, approve);
                switch (CERT_TYPE) {
                    case "professional":
                        certification.professionalSchedulePELnk.click();
                        break;
                    case "novice":
                        certification.noviceSchedulePELnk.click();
                        break;
                }
                schedulePerformanceExam(certType);
                completePerformanceExamLnk.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void signContract(String certType, String lastName, String loginType) {
        try {
            login.logBackInToUIP(loginType);
            Thread.sleep(1000);
            certification.certificationTab.click();
            certification.pendingLnk.click();
            signContractLnk.click();

            Thread.sleep(8000);
            certification.performanceContractAgreeBtn.click();

            admin.logInToAdmin();
            adminInbox.acceptPaymentOrAgreement("contract", lastName);

            driver.switchTo().alert().accept();
            login.logInToOpenSession();
            certificationsTab.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkForNewAlerts() {
        try {
            Assert.assertTrue(alertBoxes.size() > 0);
            System.out.println("New alerts successfully generated for scheduled exams.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
