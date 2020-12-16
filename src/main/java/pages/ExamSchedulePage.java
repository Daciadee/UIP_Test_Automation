package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ExamSchedulePage {

    private WebDriver driver;

    public ExamSchedulePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[text()='Today']")
    public WebElement calendarTodayLnk;
    @FindBy(xpath = "//a[text()='Next ']")
    public WebElement calendarNextLnk;
    @FindBy(xpath = "//a[text()='Prev']")
    public WebElement calendarPreviousLnk;
    @FindBy(xpath = "//div[@class='dwscal-event future available knowledge']")
    public List<WebElement> availableKnowledgeExams;
    @FindBy(xpath = "//div[@class='dwscal-event future available knowledge selected']")
    public List<WebElement> selectedKnowledgeExams;
    @FindBy(xpath = "//div[@class='dwscal-event future']")
    public List<WebElement> reservedKnowledgeExams;
    @FindBy(xpath = "//button[@class='btn btn-primary']")
    public WebElement yesBtn;
    @FindBy(xpath = "//button[@class='btn btn-default']")
    public WebElement noBtn;


}
