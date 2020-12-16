package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NationalCertificationPage {

    private WebDriver driver;

    public NationalCertificationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//button[text()='Begin']")
    public WebElement beginBtn;

    @FindBy(id = "nationalId")
    public WebElement nationalCertificationCbx;
    @FindBy(xpath = "//select[@id='nationalId']/option[@label]")
    public List<WebElement> certificationOptions;
    @FindBy(id = "expirationDate")
    public WebElement expirationDateTbx;
    @FindBy(id = "certFileId")
    public WebElement chooseFilesBtn;
    @FindBy(xpath = "//button[text()='Upload Certification']")
    public WebElement uploadCertificationBtn;
}
