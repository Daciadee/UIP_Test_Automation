package pages.admin;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelpers;

import static utils.GetProperties.*;

public class AdminPage {

    private WebDriver driver;
    private TestHelpers th;

    public AdminPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        th = new TestHelpers(driver);
    }

    @FindBy(id = "UserName")
    public WebElement utahIdEmailTbx;
    @FindBy(id = "Password")
    public WebElement passwordTbx;
    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginBtn;

    // Menu //
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Inbox']")
    public WebElement inboxBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Interpreters']")
    public WebElement interpretersBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Schedule']")
    public WebElement scheduleBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Knowledge']")
    public WebElement knowledgeBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Performance']")
    public WebElement performanceBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='CEH']")
    public WebElement cehBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Emails']")
    public WebElement emailsBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Reports']")
    public WebElement reportsBtn;


    public void logInToAdmin() {
        try {
            driver.navigate().to(ADMIN_URL);

            th.clearText(utahIdEmailTbx, driver);
            utahIdEmailTbx.sendKeys(ADMIN_EMAIL);
            passwordTbx.sendKeys(ADMIN_PASSWORD);

            loginBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
