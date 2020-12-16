package pages.admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utils.GetProperties.ADMIN_URL;

public class AdminInterpretersPage {

    private WebDriver driver;

    public AdminInterpretersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//ul[@class='nav nav-tabs']/li/a[text()='Interpreters']")
    public WebElement interpretersBtn;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[1]")
    public WebElement certificationsFilterTbx;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[2]")
    public WebElement nameFilterTbx;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[3]")
    public WebElement emailFilterTbx;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[4]")
    public WebElement phoneFilterTbx;

    public void displayRegisteredInterpreter(String email) {
        try {
            interpretersBtn.click();
            emailFilterTbx.sendKeys(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
