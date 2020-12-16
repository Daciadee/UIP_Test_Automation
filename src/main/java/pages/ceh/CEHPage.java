package pages.ceh;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Queries;
import utils.TestHelpers;

public class CEHPage {

    private WebDriver driver;
    private Queries queries = new Queries();

    public CEHPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@ui-sref='.ceh']")
    public WebElement cehTab;
    @FindBy(xpath = "//a[text()='Upload Proof of Attendance']")
    public WebElement uploadAttendanceBtn;
    @FindBy(xpath = "//a[text()='Workshop Approval']")
    public WebElement requestCehApprovalBtn;

    public void openCehTabAndBeginUpload() {
        try {
            cehTab.click();
            uploadAttendanceBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openCEHRequestPage() {
        try {
            cehTab.click();
            requestCehApprovalBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
