package pages.ceh;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.Queries;
import utils.TestHelpers;

import java.util.List;

public class CEHApprovalRequestPage {

    private WebDriver driver;
    private Queries queries = new Queries();

    public CEHApprovalRequestPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@ui-sref='.profile']")
    public WebElement profileTab;
    @FindBy(xpath = "//a[@ui-sref='.ce']")
    public WebElement ceProgressTab;
    @FindBy(xpath = "//a[@ui-sref='user.ce-approval']")
    public WebElement requestCeApprovalBtn;

    // Request Form //
    @FindBy(name = "activityName")
    public WebElement activityNameTbx;
    @FindBy(name = "activityStart")
    public WebElement startDateTbx;
    @FindBy(name = "activityEnd")
    public WebElement endDateTbx;
    @FindBy(name = "activityPresenter")
    public WebElement presenterTbx;
    @FindBy(name = "ceRequested")
    public WebElement hoursRequestedTbx;
    @FindBy(name = "activityType")
    public WebElement cehTypeCbx;
    @FindBy(xpath = "//select[@name='activityType']/option")
    public List<WebElement> cehTypeOptions;
    @FindBy(name = "activityDescription")
    public WebElement descriptionTbx;
    @FindBy(name = "activityWhy")
    public WebElement whyTbx;
    @FindBy(name = "activityGoals")
    public WebElement whatAndHowTbx;
    @FindBy(name = "activityVerification")
    public WebElement whatDocumentationTbx;
    @FindBy(xpath = "//button[text()='Submit Request']")
    public WebElement submitRequestBtn;


    public void fillAndSubmitCEHRequestForm(List<String> data) {
        try {
            TestHelpers th = new TestHelpers(driver);

            String activity = data.get(0);
            String start = data.get(1);
            String end = data.get(2);
            String presenter = data.get(3);
            String hours = data.get(4);
            String description = data.get(5);
            String why = data.get(6);
            String how = data.get(7);
            String what = data.get(8);

            activityNameTbx.sendKeys(activity);
            th.clearText(startDateTbx, driver);
            startDateTbx.sendKeys(start);
            th.clearText(endDateTbx, driver);
            endDateTbx.sendKeys(end);
            presenterTbx.sendKeys(presenter);
            th.clearText(hoursRequestedTbx, driver);
            hoursRequestedTbx.sendKeys(hours);
            descriptionTbx.sendKeys(description);
            whyTbx.sendKeys(why);
            whatAndHowTbx.sendKeys(how);
            whatDocumentationTbx.sendKeys(what);

            Thread.sleep(1000);
            submitRequestBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
