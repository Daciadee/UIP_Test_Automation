package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Queries;
import utils.TestHelpers;

import java.util.List;

public class CertificationsPage {

    private WebDriver driver;

    public CertificationsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    // Certification Selection //
    @FindBy(xpath = "//a[@ui-sref='.certification']")
    public WebElement certificationTab;
    @FindBy(id = "dropdownMenu1")
    public WebElement addCertificationBtn;
    @FindBy(xpath = "//button[@id='dropdownMenu1']/following-sibling::ul//a")
    public List<WebElement> certificationOptions;

    // Certification Table //
    //-- Filters --//
    @FindBy(xpath = "//input[@ng-model='colFilter.term']")
    public List<WebElement> tableFilters;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[1]")
    public WebElement certificationTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[2]")
    public WebElement recognizeDateTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[3]")
    public WebElement lapseDateTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[4]")
    public WebElement testDateTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[5]")
    public WebElement scoreTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[6]")
    public WebElement actionRequiredTbx;

    //-- Professional Links --//
    @FindBy(xpath = "//div[text()='Professional Certification']/parent::div/parent::div//a[text()=' In Progress ']")
    public WebElement professionalCertInProgressLnk;
    @FindBy(xpath = "//div[text()='Professional Certification']/parent::div/parent::div/parent::div//a[text()='Schedule KE']")
    public WebElement professionalScheduleKELnk;
    @FindBy(xpath = "//div[text()='Professional Certification']/parent::div/parent::div/parent::div//a[text()='PE Agreement']")
    public WebElement professionalPEAgreementLnk;
    @FindBy(xpath = "//div[text()='Professional Certification']/parent::div/parent::div/parent::div//a[text()='Schedule PE']")
    public WebElement professionalSchedulePELnk;
    @FindBy(xpath = "//a[contains(text(),'Sign Agreement')]")
    public WebElement professionalPESignContractLnk;
    @FindBy(xpath = "//a[text()='Pending']")
    public WebElement pendingLnk;

    //-- Novice Links --//
    @FindBy(xpath = "//div[text()='Novice Certification']/parent::div/parent::div//a[text()=' In Progress ']")
    public WebElement noviceCertInProgressLnk;
    @FindBy(xpath = "//div[text()='Novice Certification']/parent::div/parent::div/parent::div//a[text()='Schedule KE']")
    public WebElement noviceScheduleKELnk;
    @FindBy(xpath = "//div[text()='Novice Certification']/parent::div/parent::div/parent::div//a[text()='PE Agreement']")
    public WebElement novicePEAgreementLnk;
    @FindBy(xpath = "//div[text()='Novice Certification']/parent::div/parent::div/parent::div//a[text()='Schedule PE']")
    public WebElement noviceSchedulePELnk;
    @FindBy(xpath = "//a[contains(text(),'Sign Agreement')]")
    public WebElement novicePESignContractLnk;

    @FindBy(xpath = "//div[@class='ui-grid-canvas']//div[contains(@ng-if,'R')]//a")
    public List<WebElement> actionRequiredLnks;
    @FindBy(id = "methodId")
    public WebElement paymentMethodCbx;
    @FindBy(xpath = "//button[text()='Agree']")
    public WebElement performanceContractAgreeBtn;




    public void selectCertification(String cert) {
        try {
            Thread.sleep(1000);
            certificationTab.click();
            addCertificationBtn.click();
            Thread.sleep(1000);

            switch (cert) {
                case "professional":
                    certificationOptions.get(0).click();
                    break;
                case "novice":
                    certificationOptions.get(1).click();
                    break;
                case "CI":
                    certificationOptions.get(2).click();
                    break;
                case "CT":
                    certificationOptions.get(3).click();
                    break;
                case "CDI":
                    certificationOptions.get(4).click();
                    break;
                case "RID NAD IV":
                    certificationOptions.get(5).click();
                    break;
                case "RID NAV V":
                    certificationOptions.get(6).click();
                    break;
                case "NIC":
                    certificationOptions.get(7).click();
                    break;
                case "NIC Adv":
                    certificationOptions.get(8).click();
                    break;
                case "NIC Master":
                    certificationOptions.get(9).click();
                    break;
                case "SC:L":
                    certificationOptions.get(10).click();
                    break;
                case "EIPA Elementary PSE":
                    certificationOptions.get(11).click();
                    break;
                case "EIPA Secondary PSE":
                    certificationOptions.get(12).click();
                    break;
                case "EIPA Elementary ASL":
                    certificationOptions.get(13).click();
                    break;
                case "EIPA Secondary ASL":
                    certificationOptions.get(14).click();
                    break;
                case "EIPA Elementary MCE":
                    certificationOptions.get(15).click();
                    break;
                case "EIPA Secondary MCE":
                    certificationOptions.get(16).click();
                    break;
                default:
                    certificationOptions.get(0).click();
                    break;
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean performanceExamFailed() {
        boolean failed = false;
        try {
            for (WebElement link : actionRequiredLnks) {
                if (link.getText().contains("Buy PE")) {
                    failed = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return failed;
    }

    public void setCertificationExpirationDate(String email, String certName, String expirationDate) {
        try {
            Queries queries = new Queries();
            queries.setCertificationExpirationDate(email, certName, expirationDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCertificationRecognizedDate(String email, String certName, String recognizedDate) {
        try {
            Queries queries = new Queries();
            queries.setCertificationRecognizedAcknowledgedDate(email, certName, recognizedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCertificationRenewalDate(String email, String renewalDate) {
        try {
            Queries queries = new Queries();
            queries.setCertificationRenewalDate(email, renewalDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCycleDates(String... cycleData) {
        try {
            Queries queries = new Queries();
            TestHelpers th = new TestHelpers(driver);
            String startDate = th.getTodaysDatePlusYears(-1);
            String endDate = th.getTodaysDatePlusDays(5);

            if (cycleData.length == 1) {
                queries.setCycleDates(cycleData[0], startDate, endDate);
            } else if (cycleData.length == 3) {
                queries.setCycleDates(cycleData[0], cycleData[1], cycleData[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
