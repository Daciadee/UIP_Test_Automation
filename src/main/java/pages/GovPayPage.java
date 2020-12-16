package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelpers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.GetProperties.OUTPUT_FILE;
import static utils.GetProperties.USER_EMAIL;

public class GovPayPage {

    private WebDriver driver;
    private TestHelpers th;

    public GovPayPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        th = new TestHelpers(driver);
    }

    @FindBy(id = "buttonCancel")
    public WebElement cancelBtn;
    @FindBy(xpath = "//a[text()='Credit Card']")
    public WebElement creditCardBtn;
    @FindBy(xpath = "//a[text()='Electronic Check']")
    public WebElement electronicCheckBtn;
    @FindBy(xpath = "//a[text()='Subscription Account']")
    public WebElement subscriptionAccountBtn;

    @FindBy(id = "cardNumber")
    public WebElement cardNumberTbx;
    @FindBy(id = "cvv")
    public WebElement cvvNumberTbx;
    @FindBy(id = "expirationMonth")
    public WebElement expirationMonthCbx;
    @FindBy(name = "expirationYear")
    public WebElement expirationYearCbx;
    @FindBy(xpath = "//select[@id='expirationMonth']/option")
    public List<WebElement> monthOptions;
    @FindBy(xpath = "//select[@name='expirationYear']/option")
    public List<WebElement> yearOptions;

    @FindBy(id = "name")
    public WebElement nameOnCardTbx;
    @FindBy(id = "country")
    public WebElement countryCbx;
    @FindBy(xpath = "//select[@id='country']/option")
    public List<WebElement> countryOptions;
    @FindBy(id = "street1")
    public WebElement address1Tbx;
    @FindBy(id = "street2")
    public WebElement address2Tbx;
    @FindBy(id = "city")
    public WebElement cityTbx;
    @FindBy(id = "states")
    public WebElement stateCbx;
    @FindBy(xpath = "//select[@id='states']/option")
    public List<WebElement> stateOptions;
    @FindBy(id = "zipCode")
    public WebElement postalCodeTbx;
    @FindBy(id = "email")
    public WebElement emailTbx;

    @FindBy(id = "full")
    public WebElement fullPaymentRdo;
    @FindBy(id = "remaining")
    public WebElement remainingOnCardRdo;
    @FindBy(id = "other")
    public WebElement otherAmountRdo;
    @FindBy(id = "otherValue")
    public WebElement otherAmountTbx;

    @FindBy(id = "buttonBack")
    public WebElement backBtn;
    @FindBy(id = "buttonCancel")
    public WebElement cancelPaymentBtn;
    @FindBy(id = "buttonContinue")
    public WebElement continueBtn;

    @FindBy(id = "buttonNo")
    public WebElement noBtn;
    @FindBy(id = "buttonContinue")
    public WebElement yesBtn;
    @FindBy(id = "buttonPrint")
    public WebElement printBtn;


    public void payByCreditCard() {
        try {
            creditCardBtn.click();

            List<String> lines = Files.readAllLines(Paths.get(OUTPUT_FILE));
            Map<String, String> data = new HashMap<>();

            data.put("Name", lines.get(0) + " " + lines.get(2));
            data.put("Street", lines.get(3));
            data.put("City", lines.get(4));
            data.put("State", lines.get(5));
            data.put("Zip", lines.get(6));

            cardNumberTbx.sendKeys("4111111111111111");
            cvvNumberTbx.sendKeys("111");
            th.selectComboBoxOption(expirationMonthCbx, monthOptions.get(th.getRandomNumberBetween(0,11)).getText());
            th.selectComboBoxOption(expirationYearCbx, yearOptions.get(th.getRandomNumberBetween(1,10)).getText());

            nameOnCardTbx.sendKeys(data.get("Name"));
            address1Tbx.sendKeys(data.get("Street"));
            cityTbx.sendKeys(data.get("City"));
            th.selectComboBoxOption(stateCbx, data.get("State"));
            postalCodeTbx.sendKeys(data.get("Zip"));
            emailTbx.sendKeys(USER_EMAIL);

            continueBtn.click();
            yesBtn.click();
            yesBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
