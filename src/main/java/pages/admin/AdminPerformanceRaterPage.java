package pages.admin;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class AdminPerformanceRaterPage {

    private WebDriver driver;
    private AdminPage admin;

    public AdminPerformanceRaterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
    }

    // Main Buttons //
//    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Rater']")
    @FindBy(xpath = "//button[@ng-click='addRaterModal()']")
    public WebElement addRaterBtn;
    @FindBy(xpath = "//button[@ng-click='startRating()']")
    public WebElement startRatingBtn;

    // Add Rater Model //
    @FindBy(id = "raterType")
    public WebElement raterTypeSelect;
    @FindBy(id = "selectedRater")
    public WebElement raterNameTbx;
    @FindBy(xpath = "//button[@ng-click='addRater()']")
    public WebElement saveRaterBtn;
    @FindBy(xpath = "//button[@ng-click='cancelModal()']")
    public WebElement cancelRaterBtn;

    // Add Videos Model //
    @FindBy(xpath = "//form/div[@class='col-md-12']//input")
    public List<WebElement> urlInputBoxes;
    @FindBy(xpath = "//button[@ng-click='saveEdit()']")
    public WebElement saveVideosBtn;
    @FindBy(xpath = "//button[@ng-click='cancelButton()']")
    public WebElement cancelVideosBtn;

    // Rater Table //
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[1]")
    public WebElement lastNameTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[2]")
    public WebElement firstNameTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[3]")
    public WebElement raterTypeTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[4]")
    public WebElement statusTbx;
    @FindBy(xpath = "//span[@class='fa fa-trash gridCellPadding']")
    public List<WebElement> deleteAssignmentBtns;
    @FindBy(xpath = "//span[@class='fa fa-share gridCellPadding']")
    public List<WebElement> assignmentRaterBtns;

    public void addRater(String raterType, String raterName) {
        try {
            addRaterBtn.click();
            Thread.sleep(1000);
            Select select = new Select(raterTypeSelect);
            select.selectByVisibleText(raterType);
            raterNameTbx.sendKeys(raterName);
            raterNameTbx.sendKeys(Keys.ENTER);
            saveRaterBtn.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addVideos(List<String> videos) {
        try {
            for (int i = 0; i < urlInputBoxes.size(); i++) {
                urlInputBoxes.get(i).sendKeys(videos.get(i));
            }
            saveVideosBtn.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
