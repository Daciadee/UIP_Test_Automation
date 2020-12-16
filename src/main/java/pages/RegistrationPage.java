package pages;

import com.github.javafaker.Faker;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.*;

import java.util.ArrayList;
import java.util.List;

import static utils.GetProperties.USER_EMAIL;

public class RegistrationPage {

    private WebDriver driver;

    private FileUtilities fu;
    private GenerateRandomData grd;
    private Queries queries;
    private TestHelpers th;

    public RegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Faker faker = new Faker();

        fu = new FileUtilities();
        grd = new GenerateRandomData(driver, person, faker);
        queries = new Queries();
        th = new TestHelpers(driver);
    }

    @FindBy(id = "firstName")
    public WebElement firstNameTbx;
    @FindBy(id = "middleName")
    public WebElement middleNameTbx;
    @FindBy(id = "lastName")
    public WebElement lastNameTbx;
    @FindBy(id = "address1")
    public WebElement addressLine1Tbx;
    @FindBy(id = "address2")
    public WebElement addressLine2Tbx;
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
    @FindBy(id = "intentId")
    public WebElement intentCbx;
    @FindBy(id = "highestGradeId")
    public WebElement highestGradeCbx;
    @FindBy(id = "trainingProgramId")
    public WebElement trainingProgramCbx;
    @FindBy(id = "programOther")
    public WebElement trainingProgramOther;
    @FindBy(id = "learnSignId")
    public WebElement learnSignCbx;
    @FindBy(id = "learnSignOther")
    public WebElement learnSignOtherTbx;
    @FindBy(xpath = "//button[text()='Submit']")
    public WebElement addBtn;

    // Calendar Popup
    @FindBy(xpath = "//span[@class='input-group-btn']/button[@ng-click='open($event)']")
    public WebElement calendarBtn;
    @FindBy(xpath = "//button[@ng-click='move(-1)']")
    public WebElement backArrowBtn;
    @FindBy(xpath = "//button[@ng-click'move(1)']")
    public WebElement forwardArrowBtn;
    @FindBy(id = "datepicker-456-650-title")
    public WebElement dateBtn;
    @FindBy(xpath = "//tbody//button[@ng-click='select(dt.date)']")
    private List<WebElement> dayBtns;
    @FindBy(xpath = "//button[text()='Today']")
    public WebElement todayBtn;
    @FindBy(xpath = "//button[text()='Clear']")
    public WebElement clearBtn;
    @FindBy(xpath = "//button[text()='Close']")
    public WebElement closeBtn;


    private void selectState(String state) {
        stateCbx.click();
        th.selectComboBoxOption(stateCbx, state);
    }

    private void selectIntent(String intent) {
        intentCbx.click();
        th.selectComboBoxOption(intentCbx, intent);
    }

    private void selectHighestGrade(String grade) {
        highestGradeCbx.click();
        th.selectComboBoxOption(highestGradeCbx, grade);
    }

    private void selectTrainingProgram(String program) {
        trainingProgramCbx.click();
        th.selectComboBoxOption(trainingProgramCbx, program);
    }

    private void selectLearnSign(String learned) {
        learnSignCbx.click();
        th.selectComboBoxOption(learnSignCbx, learned);
    }

    public void registerWithRandomData(String... names) {
        List<String> userData = new ArrayList<>();
        try {
            // Add user data for registration
            if (names.length == 0) {
                userData.add("Test");
                userData.add(grd.getMiddleName());
                userData.add(grd.getLastName());
            } else if (names.length == 2) {
                userData.add(names[0]);
                userData.add("");
                userData.add(names[1]);
            } else {
                userData.add(names[0]);
                userData.add(names[1]);
                userData.add(names[2]);
            }
            userData.add(grd.getStreet()); //3
            userData.add(grd.getCity()); //4
            userData.add(grd.getState()); //5
            userData.add(grd.getZipCode()); //6
            userData.add(grd.getCellPhone()); //7
            userData.add(grd.getBirthDate()); //8
            userData.add(grd.getRandomHighestGrade()); //9

            String learnSign = grd.getRandomLearnSign(); //10
            userData.add(learnSign);

            String trainingProgram = grd.getRandomTrainingProgram(); //11
            userData.add(trainingProgram);

            userData.add(grd.getUniversity()); //12

            // Fill registration form
            Thread.sleep(1000);
            th.clearText(firstNameTbx, driver);
            firstNameTbx.sendKeys(userData.get(0));
            middleNameTbx.sendKeys(userData.get(1));
            th.clearText(lastNameTbx, driver);
            lastNameTbx.sendKeys(userData.get(2));

            addressLine1Tbx.sendKeys(userData.get(3));
            cityTbx.sendKeys(userData.get(4));
            selectState(userData.get(5));
            zipTbx.sendKeys(userData.get(6));
            phoneTbx.sendKeys(userData.get(7));
            birthDateTbx.sendKeys(userData.get(8));

            selectHighestGrade(userData.get(9));
            selectLearnSign(userData.get(10));
            if (learnSign.equals("Other")) {
                learnSignOtherTbx.sendKeys(userData.get(12));
            } else if (learnSign.equals("Interpreter Training Program")) {
                if (trainingProgram.equals("Other")) {
                    trainingProgramOther.sendKeys(userData.get(12));
                } else {
                    selectTrainingProgram(userData.get(11));
                }
            }

            fu.writeInterpreterDataToFile(userData);

            addBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfRegistered(String email) {
        boolean registered = false;
        try {
            String interpreterId = queries.getInterpreterId(email);
            if (interpreterId != null) {
                registered = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registered;
    }

    public void unregisterInterpreter(String email) {
        try {
            String interpreterId = queries.getInterpreterId(email);
            if (interpreterId != null) {
                queries.unregisterInterpreter(email);
                driver.manage().deleteAllCookies();
            } else {
                System.out.println("No interpreter associated with email address found in database, skipping record removal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
