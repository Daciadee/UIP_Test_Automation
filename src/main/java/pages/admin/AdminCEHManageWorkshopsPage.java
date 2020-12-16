package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import utils.TestHelpers;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminCEHManageWorkshopsPage {

    private WebDriver driver;
    private AdminPage admin;
    private AdminCEHAddWorkShopPage adminAddWorkshop;
    private LoginPage login;

    public AdminCEHManageWorkshopsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
        adminAddWorkshop = new AdminCEHAddWorkShopPage(driver);
        login = new LoginPage(driver);
    }

    @FindBy(xpath = "//a[@ui-sref='.ceh']")
    public WebElement cehTab;
    @FindBy(xpath = "//a[@ui-sref='.workshop']")
    public WebElement manageWorkshopsTab;
    @FindBy(xpath = "//a[text()='Add Workshop']")
    public WebElement addWorkshopBtn;

    @FindBy(xpath = "(//div[@role='grid']//input)[1]")
    public WebElement titleTbx;
    @FindBy(xpath = "(//div[@role='grid']//input)[2]")
    public WebElement presenterTbx;
    @FindBy(xpath = "(//div[@role='grid']//input)[3]")
    public WebElement dateTbx;
    @FindBy(xpath = "(//div[@role='grid']//input)[4]")
    public WebElement cehTbx;
    @FindBy(xpath = "(//div[@role='grid']//input)[5]")
    public WebElement cehTypeTbx;

    @FindBy(xpath = "(//div[@class='ui-grid-canvas']//div[@class='ui-grid-cell-contents ng-binding ng-scope'])[1]")
    public WebElement firstGridCell;
    @FindBy(xpath = "//div[@class='ui-grid-canvas']/div[@ng-repeat]//a")
    public List<WebElement> viewBtns;


    public void addOrUpdateWorkshopDates(String workshopName, int hours) {
        try {
            TestHelpers th = new TestHelpers(driver);
            String startDate = th.getTodaysDatePlusDays(-7);
            String endDate = th.getTodaysDatePlusDays(-5);

            admin.logInToAdmin();

            cehTab.click();
            manageWorkshopsTab.click();

            titleTbx.sendKeys(workshopName);

            if (!firstGridCell.getText().contains(workshopName)) {
                addWorkshopBtn.click();
                adminAddWorkshop.fillWorkshopFormAndSubmit(workshopName, startDate, endDate, hours);
                System.out.println("New workshop '" + workshopName + " added. Workshop active from '" + startDate + "' to '" + endDate + "'");
            } else {
                viewBtns.get(0).click();
                adminAddWorkshop.updateWorkshopDatesAndHours(startDate, endDate, hours);
                System.out.println("Updated workshop '" + workshopName + "'. Workshop now active from '" + startDate + "' to '" + endDate + "'");
            }

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfWorkshopExists(String workshopName) {
        boolean exists = false;
        try {
            admin.logInToAdmin();

            cehTab.click();
            manageWorkshopsTab.click();

            titleTbx.sendKeys(workshopName);

            try {
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                if (firstGridCell.getText().contains(workshopName)) {
                    exists = true;
                }
            } catch (Exception e) {
                exists = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}
