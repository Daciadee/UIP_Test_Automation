package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AdminCEHRequestApprovalPage {

    private WebDriver driver;

    public AdminCEHRequestApprovalPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[contains(@ng-model,'ceAwarded')]")
    public WebElement cehAwardedTbx;
    @FindBy(name = "cehType")
    public WebElement cehTypeCbx;
    @FindBy(xpath = "//textarea[contains(@ng-model,'notes')]")
    public WebElement notesTbx;
    @FindBy(xpath = "//button[text()='Approve Request']")
    public WebElement approveRequestBtn;
    @FindBy(xpath = "//button[text()='Deny Request']")
    public WebElement denyRequestBtn;

    public void approveCEHRequest(String option) {
        try {
            Select selector = new Select(cehTypeCbx);
            selector.selectByVisibleText(option);
            approveRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void denyCEHRequest(String option) {
        try {
            Select selector = new Select(cehTypeCbx);
            selector.selectByVisibleText(option);
            denyRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
