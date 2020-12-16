package pages.ceh;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.admin.AdminInboxPage;
import pages.admin.AdminPage;
import utils.FileUtilities;

import static utils.GetProperties.VOUCHER_PATH;

public class CEHUploadDocumentationPage {

    private WebDriver driver;

    public CEHUploadDocumentationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@type='text' and @placeholder='Workshop name']")
    public WebElement workshopNameTbx;
    @FindBy(xpath = "//a[@tabindex='-1']")
    public WebElement workshopOption;
    @FindBy(xpath = "//input[@type='file']")
    public WebElement chooseFileBtn;
    @FindBy(xpath = "//button[text()='Upload Document']")
    public WebElement uploadDocumentBtn;

    public void uploadCehDocument(String workshop) {
        try {
            workshopNameTbx.sendKeys(workshop);
            workshopOption.click();
            chooseFile(VOUCHER_PATH);
            uploadDocumentBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseFile(String filePath) {
        try {
            FileUtilities fu = new FileUtilities();
            chooseFileBtn.click();
            fu.uploadFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveCehUpload(String lastName) {
        try {
            AdminPage admin = new AdminPage(driver);
            AdminInboxPage adminInbox = new AdminInboxPage(driver);

            admin.logInToAdmin();
            adminInbox.approveCehUpload(lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void denyCehUpload(String lastName) {
        try {
            AdminPage admin = new AdminPage(driver);
            AdminInboxPage adminInbox = new AdminInboxPage(driver);

            admin.logInToAdmin();
            adminInbox.denyCehUpload(lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
