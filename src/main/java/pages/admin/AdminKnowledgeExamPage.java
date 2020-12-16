package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.FileUtilities;
import utils.Queries;
import utils.TestHelpers;

import java.util.List;

import static utils.GetProperties.OUTPUT_FILE;

public class AdminKnowledgeExamPage {

    private WebDriver driver;

    private AdminPage admin;
    private TestHelpers th;

    public AdminKnowledgeExamPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
        th = new TestHelpers(driver);
    }

    // Menu //
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Knowledge']")
    public WebElement knowledgeBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Testing Today']")
    public WebElement testingTodayBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Questions']")
    public WebElement questionsBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Categories']")
    public WebElement categoriesBtn;

    // Filters //
    @FindBy(xpath = "(//div[@role='grid']//input[@type='text'])[1]")
    public WebElement checkInFilterTbx;
    @FindBy(xpath = "(//div[@role='grid']//input[@type='text'])[2]")
    public WebElement nameFilterTbx;
    @FindBy(xpath = "(//div[@role='grid']//input[@type='text'])[3]")
    public WebElement startTimeFilterTbx;
    @FindBy(xpath = "(//div[@role='grid']//input[@type='text'])[4]")
    public WebElement seatFilterTbx;

    // Checkboxes //
    @FindBy(xpath = "//input[@type='checkbox']")
    public List<WebElement> checkInCheckboxes;


    private void filterByLastName(String lastName) {
        try {
            nameFilterTbx.sendKeys(lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInInterpreterForKnowledgeExam(String lastName) {
        try {
            admin.logInToAdmin();
            knowledgeBtn.click();
            Thread.sleep(1000);
            filterByLastName(lastName);
            Thread.sleep(500);
            checkInCheckboxes.get(0).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
