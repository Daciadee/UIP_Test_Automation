package pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import utils.Queries;
import utils.TestHelpers;

import static utils.GetProperties.LOGIN_TYPE;

public class AdminInboxPage {

    private WebDriver driver;
    private AdminPage admin;
    private AdminCEHRequestApprovalPage adminRequest;
    private LoginPage login;
    private Queries queries;
    private TestHelpers th;

    public AdminInboxPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
        adminRequest = new AdminCEHRequestApprovalPage(driver);
        login = new LoginPage(driver);
        queries = new Queries();
        th = new TestHelpers(driver);
    }

    @FindBy(xpath = "//a[@ui-sref='.tasks']")
    public WebElement tasksBtn;
    @FindBy(xpath = "(//div[@class='ng-scope']/input[@type='text'])[1]")
    public WebElement typeFilterTbx;
    @FindBy(xpath = "(//div[@class='ng-scope']/input[@type='text'])[2]")
    public WebElement interpreterFilterTbx;
    @FindBy(xpath = "//span[@class='fa fa-eye  gridCellPadding']")
    public WebElement eyeBtn;
    @FindBy(xpath = "//button[text()='Accept']")
    public WebElement acceptPaymentBtn;
    @FindBy(xpath = "//button[text()='Deny']")
    public WebElement denyPaymentBtn;
    @FindBy(xpath = "//a[text()='View Request']")
    public WebElement viewRequestBtn;
    @FindBy(xpath = "//button[@ng-click='sendEmail()']")
    public WebElement sendEmailBtn;


    public void acceptPaymentOrAgreement(String type, String lastName) {
        try {
            if (type.length() > 0)
                typeFilterTbx.sendKeys(type);
            interpreterFilterTbx.sendKeys(lastName);
            eyeBtn.click();
            acceptPaymentBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void denyPayment() {
        try {
            eyeBtn.click();
            denyPaymentBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmSelection() {
        driver.switchTo().alert().accept();
    }

    public void loginToAdminAndApprovePayment(String lastName, boolean approved) {
        try {
            admin.logInToAdmin();

            if (approved) {
                acceptPaymentOrAgreement("", lastName);
            } else {
                denyPayment();
            }

            confirmSelection();

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewCEHRequest(String lastName) {
        try {
            interpreterFilterTbx.sendKeys(lastName);

            eyeBtn.click();

            WebElement viewRequestBtn = driver.findElement(By.xpath("//a[text()='View Request']"));
            viewRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveCehUpload(String lastName) {
        try {
            viewCEHRequest(lastName);

            WebElement approveRequestBtn = driver.findElement(By.xpath("//button[text()='Approve Request']"));
            approveRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void denyCehUpload(String lastName) {
        try {
            viewCEHRequest(lastName);

            WebElement denyRequestBtn = driver.findElement(By.xpath("//button[text()='Deny Request']"));
            denyRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveCertificationUpload(String interpreterName) {
        try {
            admin.logInToAdmin();

            interpreterFilterTbx.sendKeys(interpreterName);
            eyeBtn.click();
            acceptPaymentBtn.click();
            confirmSelection();

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveCEHRequestApproval(String interpreterName, String option) {
        try {
            admin.logInToAdmin();

            typeFilterTbx.sendKeys("CEH");
            interpreterFilterTbx.sendKeys(interpreterName);
            eyeBtn.click();
            viewRequestBtn.click();
            adminRequest.approveCEHRequest(option);

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPerformanceTestCompletionEmail(String lastName) {
        try {
            admin.logInToAdmin();

            tasksBtn.click();
            interpreterFilterTbx.sendKeys(lastName);
            eyeBtn.click();
            viewRequestBtn.click();
            Thread.sleep(1000);
            sendEmailBtn.click();
            confirmSelection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
