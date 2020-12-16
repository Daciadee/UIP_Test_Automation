package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.admin.AdminInboxPage;
import pages.admin.AdminPage;
import utils.FileUtilities;
import utils.TestHelpers;

import java.util.List;

import static utils.GetProperties.VOUCHER_PATH;

public class AddCertificationPage {

    private WebDriver driver;
    private TestHelpers th;

    public AddCertificationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        th = new TestHelpers(driver);
    }

    @FindBy(id = "expirationDate")
    public WebElement expirationDateTbx;
    @FindBy(id = "certFileId")
    public WebElement chooseFileBtn;
    @FindBy(xpath = "//button[contains(text(),'Upload Certification')]")
    public WebElement uploadCertificationBtn;

    public void uploadCertification(int yearsTilExpiration) {
        try {
            expirationDateTbx.sendKeys(th.getTodaysDatePlusYears(yearsTilExpiration));

            FileUtilities fu = new FileUtilities();
            fu.chooseFile(VOUCHER_PATH, chooseFileBtn);
            Thread.sleep(1000);

            uploadCertificationBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveUploadedCertificate(String lastName) {
        try {
            AdminInboxPage adminInbox = new AdminInboxPage(driver);
            adminInbox.approveCertificationUpload(lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payForCertification(String certName) {
        try {
            CertificationsPage cert = new CertificationsPage(driver);
            List<WebElement> actions = cert.actionRequiredLnks;

            cert.certificationTbx.sendKeys(certName);
            for (WebElement action : actions) {
                if (action.getText().equals("Pay")) {
                    action.click();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
