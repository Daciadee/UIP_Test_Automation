package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.TestHelpers;

import java.util.List;

public class AdminCEHAddWorkShopPage {

    private WebDriver driver;
    private AdminPage admin;
    private TestHelpers th;

    public AdminCEHAddWorkShopPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
        th = new TestHelpers(driver);
    }

    @FindBy(xpath = "//a[@ui-sref='.ceh']")
    public WebElement cehTab;
    @FindBy(xpath = "//a[@ui-sref='.workshop']")
    public WebElement manageWorkshopsTab;
    @FindBy(xpath = "//a[text()='Add Workshop']")
    public WebElement addWorkshopBtn;

    @FindBy(name = "title")
    public WebElement titleTbx;
    @FindBy(name = "presenter")
    public WebElement presenterTbx;
    @FindBy(name = "description")
    public WebElement descriptionTbx;
    @FindBy(name = "cehRequested")
    public WebElement cehTbx;
    @FindBy(name = "cehType")
    public WebElement cehTypeCbx;
    @FindBy(xpath = "//input[@type='checkbox' and contains(@ng-model,'partialCredit')]")
    public WebElement partialCreditChk;
    @FindBy(name = "partialCreditAmt")
    public WebElement partialCreditAmountTbx;
    @FindBy(name = "startTime")
    public WebElement startDateTbx;
    @FindBy(name = "endTime")
    public WebElement endDateTbx;
    @FindBy(xpath = "//input[@type='checkbox' and contains(@ng-model,'registrationReq')]")
    public WebElement registrationRequiredChk;
    @FindBy(name = "registrationDeadline")
    public WebElement registrationDeadlineTbx;
    @FindBy(xpath = "//div[@class='form-group']/button")
    public WebElement saveOrUpdateWorkshopBtn;


    public void navigateToAddWorkshopPage() {
        try {
            admin.logInToAdmin();
            cehTab.click();
            manageWorkshopsTab.click();
            addWorkshopBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillWorkshopFormAndSubmit(String name, String start, String end, int hours) {
        try {
            titleTbx.sendKeys(name);
            presenterTbx.sendKeys("Mr. Test Presenter");
            descriptionTbx.sendKeys("This is a test CEH workshop.");
            cehTbx.sendKeys(String.valueOf(hours));

            Select selector = new Select(cehTypeCbx);
            selector.selectByVisibleText("Professional Studies");

            startDateTbx.sendKeys(start);
            endDateTbx.sendKeys(end);
            registrationRequiredChk.click();
            Thread.sleep(1000);

            registrationDeadlineTbx.sendKeys(end);
            saveOrUpdateWorkshopBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateWorkshopDatesAndHours(String start, String end, int hours) {
        try {
            th.clearText(cehTbx, driver);
            cehTbx.sendKeys(String.valueOf(hours));

            th.clearText(startDateTbx, driver);
            startDateTbx.sendKeys(start);

            Thread.sleep(1000);
            th.clearText(endDateTbx, driver);
            endDateTbx.sendKeys(end);

            registrationRequiredChk.click();
            Thread.sleep(1000);
            th.clearText(registrationDeadlineTbx, driver);
            registrationDeadlineTbx.sendKeys(end);

            Thread.sleep(1000);
            saveOrUpdateWorkshopBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOrAddNewWorkshop(List<String> formData, boolean partialCredit, boolean registrationRequired) {
        try {
            String title = formData.get(0);
            String presenter = formData.get(1);
            String description = formData.get(2);
            String ceh = formData.get(3);
            String cehType = formData.get(4);
            String startDate = formData.get(5);
            String endDate = formData.get(6);

            admin.logInToAdmin();

            cehTab.click();
            manageWorkshopsTab.click();
            addWorkshopBtn.click();

            titleTbx.sendKeys(title);
            presenterTbx.sendKeys(presenter);
            descriptionTbx.sendKeys(description);

            if (cehType.contains("General") && Integer.valueOf(ceh) > 8) {
                ceh = "8";
            }
            cehTbx.sendKeys(ceh);

            Select selector = new Select(cehTypeCbx);
            selector.selectByVisibleText(cehType);

            if (partialCredit) {
                partialCreditChk.click();
                Thread.sleep(500);
                String partialCreditAmount = String.valueOf(Integer.valueOf(formData.get(3)) / 2);
                partialCreditAmountTbx.sendKeys(partialCreditAmount);
            }

            startDateTbx.sendKeys(startDate);
            endDateTbx.sendKeys(endDate);

            if (registrationRequired) {
                registrationRequiredChk.click();
                Thread.sleep(500);
                registrationDeadlineTbx.sendKeys(startDate);
            }

            saveOrUpdateWorkshopBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
