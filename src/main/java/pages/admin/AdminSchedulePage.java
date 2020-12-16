package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminSchedulePage {

    private WebDriver driver;

    public AdminSchedulePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    // Menu //
    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Schedule']")
    public WebElement scheduleBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Calendar']")
    public WebElement calendarBtn;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']/li/a[text()='Settings']")
    public WebElement settingsBtn;

    // Calendar Menu //
    @FindBy(xpath = "//div[@class='filter-toggle']/label[text()='All']")
    public WebElement allCalBtn;
    @FindBy(xpath = "//div[@class='filter-toggle']/label[text()='Knowledge']")
    public WebElement knowledgeCalBtn;
    @FindBy(xpath = "//div[@class='filter-toggle']/label[text()='Professional']")
    public WebElement professionalCalBtn;
    @FindBy(xpath = "//div[@class='filter-toggle']/label[text()='Mock']")
    public WebElement mockCalBtn;

    @FindBy(xpath = "//div[@class='dwscal-title ng-binding']")
    public WebElement calendarMonthTxt;
}
