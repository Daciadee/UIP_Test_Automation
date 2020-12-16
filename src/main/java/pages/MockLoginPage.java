package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MockLoginPage {

    private WebDriver driver;

    public MockLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(name = "email")
    public WebElement emailAddressTbx;
    @FindBy(name = "application")
    public WebElement applicationTbx;
    @FindBy(name = "applicationId")
    public WebElement applicationIdTbx;
    @FindBy(xpath = "ssoId")
    public WebElement ssoIdTbx;
    @FindBy(xpath = "//input[@type='submit']")
    public WebElement submitBtn;

    public void loginToMockSingleSignOn(String email) {
        try {
            driver.navigate().to("http://localhost:8080/sso/login.aspx?application=INTERPRETER&returnurl=/jsp/uip/user/");
            emailAddressTbx.sendKeys(email);
            applicationTbx.sendKeys("INTERPRETER");
            ssoIdTbx.sendKeys(email);
            submitBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
