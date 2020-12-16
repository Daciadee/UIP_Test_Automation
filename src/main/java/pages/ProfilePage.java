package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProfilePage {

    // Tabs
    @FindBy(xpath = "//a[@ui-sref='.general']")
    public WebElement generalInfoTab;
    @FindBy(xpath = "//a[@ui-sref='.payment-history']")
    public WebElement paymentHistoryTab;
    @FindBy(xpath = "//a[@ui-sref='.ke-history']")
    public WebElement knowledgeExamHistory;
    @FindBy(xpath = "//a[@ui-sref='.ke-history']")
    public WebElement performanceHistory;

    // General Information
    @FindBy(id = "firstName")
    public WebElement firstNameTbx;
    @FindBy(id = "middleName")
    public WebElement middleNameTbx;
    @FindBy(id = "lastName")
    public WebElement lastNameTbx;
    @FindBy(id = "address1")
    public WebElement address1Tbx;
    @FindBy(id = "address2")
    public WebElement address2Tbx;
    @FindBy(id = "city")
    public WebElement cityTbx;
    @FindBy(id = "state")
    public WebElement stateCbx;
    @FindBy(xpath = "//select[@id='state']/option")
    public List<WebElement> stateOptions;
    @FindBy(id = "zip")
    public WebElement zipTbx;
    @FindBy(id = "phone")
    public WebElement phoneTbx;
    @FindBy(id = "birthDate")
    public WebElement birthDateTbx;
    @FindBy(id = "email")
    public WebElement emailTbx;
    @FindBy(xpath = "//button[text()='Update']")
    public WebElement updateBtn;
    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement cancelBtn;

    // Payment History
    @FindBy(xpath = "//div[@class='ui-grid-canvas']/div")
    public List<WebElement> paymentHistoryRecords;



}
