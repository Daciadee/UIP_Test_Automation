package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import utils.TestHelpers;

import java.util.LinkedList;
import java.util.List;

import static utils.GetProperties.*;

public class AdminPerformanceInterpreterPage {

    private WebDriver driver;
    private AdminPage admin;
    private AdminPerformanceRaterPage raterPage;
    private LoginPage login;
    private TestHelpers th;

    public AdminPerformanceInterpreterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        admin = new AdminPage(driver);
        raterPage = new AdminPerformanceRaterPage(driver);
        login = new LoginPage(driver);
        th = new TestHelpers(driver);
    }

    // Menu //
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Interpreter']")
    public WebElement interpreterBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Rater']")
    public WebElement raterBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Questions']")
    public WebElement questionsBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Assignment']")
    public WebElement assignmentBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='In Progress']")
    public WebElement inProgressBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Complete']")
    public WebElement completeBtn;

    // Interpreter Assignment Sub-Page //
    //** Assignment List Table **//
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[1]")
    public WebElement lastNameTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[2]")
    public WebElement firstNameTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[3]")
    public WebElement examTypeTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[4]")
    public WebElement statusTbx;
    @FindBy(xpath = "(//input[@ng-model='colFilter.term'])[5]")
    public WebElement testDateTbx;
    @FindBy(xpath = "//div[@class='ui-grid-canvas']/div[@ng-repeat]")
    public List<WebElement> tableRows;
    @FindBy(xpath = "//span[@class='fa fa-users gridCellPadding']")
    public List<WebElement> raterAssignmentBtns;
    @FindBy(xpath = "//span[@class='fa fa-video gridCellPadding']")
    public List<WebElement> videoAssignmentBtns;
    @FindBy(xpath = "//div[@class='col-md-6']//input")
    public List<WebElement> urlInputBoxes;

    //** Novice Assignment **//
    @FindBy(xpath = "//label[contains(text(),'Spoken English to Sign:')]/following-sibling::div/input")
    public WebElement spokenEngToSignTbx;
    @FindBy(xpath = "//label[contains(text(),'Sign to Spoken English:')]/following-sibling::div/input")
    public WebElement signToSpokenEngTbx;
    @FindBy(xpath = "//label[contains(text(),'Interactive Role Play:')]/following-sibling::div/input")
    public WebElement interactiveRolePlayNoviceTbx;

    //** Professional Assignment **//
    @FindBy(xpath = "//label[contains(text(),'Spoken English to American Sign Language:')]/following-sibling::div/input")
    public WebElement spokenEngToAmerSignLangTbx;
    @FindBy(xpath = "//label[contains(text(),'American Sign Language to Spoken English:')]/following-sibling::div/input")
    public WebElement amerSignLangToSpokenEngTbx;
    @FindBy(xpath = "//label[contains(text(),'Spoken English to English-bound Signing:')]/following-sibling::div/input")
    public WebElement spokenEngToEngBoundSigningTbx;
    @FindBy(xpath = "//label[contains(text(),'English-bound Signing to Spoken English:')]/following-sibling::div/input")
    public WebElement engBoundSigningToSpokenEngTbx;
    @FindBy(xpath = "//label[contains(text(),'Interactive Role Play:')]/following-sibling::div/input")
    public WebElement interactiveRolePlayProfessionalTbx;

    @FindBy(xpath = "//h2[text()='Deaf']/parent::div//label")
    public List<WebElement> deafRaters;
    @FindBy(xpath = "//h2[text()='English']/parent::div//label")
    public List<WebElement> englishRaters;
    @FindBy(xpath = "//h2[text()='Interpreter']/parent::div//label")
    public List<WebElement> interpreterRaters;
    @FindBy(xpath = "//div[contains(@ng-repeat,'rater')]/label")
    public List<WebElement> allRaters;
    @FindBy(xpath = "//div[contains(@ng-repeat,'rater')]/label/input")
    public List<WebElement> allRaterCheckboxes;

    @FindBy(xpath = "//button[text()='Save']")
    public WebElement saveBtn;
    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement cancelBtn;

    public void openInterpreterTab() {
        try {
            admin.logInToAdmin();
            selectPerfInterpreterAssignmentTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPerfInterpreterAssignmentTab() {
        admin.performanceBtn.click();
        interpreterBtn.click();
    }

    public void assignVideosAndRaters(String lastName) {
        try {
            assignPerformanceVideos(lastName);
            assignRaters(lastName);
            login.logOutOfOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignRaters(String lastName) {
        try {
            selectPerfInterpreterAssignmentTab();
            lastNameTbx.sendKeys(lastName);
            raterAssignmentBtns.get(0).click();
            Thread.sleep(1000);
            raterPage.addRater("CDI", "EnglishRater4 UIP");
            raterPage.addRater("CDI", "EnglishRater5 UIP");
            raterPage.addRater("CDI", "EnglishRater6 UIP");
            raterPage.addRater("Deaf", "DeafRater1 UIP");
            raterPage.addRater("Deaf", "DeafRater2 UIP");
            raterPage.addRater("Deaf", "DeafRater3 UIP");
            raterPage.addRater("Interpreter", "InterpreterRater7 UIP");
            raterPage.addRater("Interpreter", "InterpreterRater8 UIP");
            raterPage.addRater("Interpreter", "InterpreterRater9 UIP");
            Thread.sleep(1000);
            raterPage.startRatingBtn.click();
            Thread.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignPerformanceVideos(String lastName) {
        try {
            selectPerfInterpreterAssignmentTab();
            lastNameTbx.sendKeys(lastName);
            videoAssignmentBtns.get(0).click();

            List<String> videos = new LinkedList<>();
            videos.add(UIP_VIDEO_1);
            videos.add(UIP_VIDEO_2);
            videos.add(UIP_VIDEO_3);
            videos.add(UIP_VIDEO_4);
            videos.add(UIP_VIDEO_5);

            raterPage.addVideos(videos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
