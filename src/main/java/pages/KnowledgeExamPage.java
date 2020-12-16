package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Queries;
import utils.TestHelpers;

import java.util.*;

import static utils.GetProperties.KNOWLEDGE_EXAM_URL;

public class KnowledgeExamPage {

    private WebDriver driver;
    private LoginPage login;
    private TestHelpers th;
    private Queries queries;

    public KnowledgeExamPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        login = new LoginPage(driver);
        th = new TestHelpers(driver);
        queries = new Queries();
    }

    @FindBy(xpath = "//button[@ui-sref='^.questions']")
    public WebElement startExamBtn;
    @FindBy(xpath = "//button[@ng-click='scoreExam()']")
    public WebElement scoreExamBtn;
    @FindBy(xpath = "//div[@class='col-md-12 well ng-scope']//input")
    public List<WebElement> examAnswersRdo;
    @FindBy(xpath = "//div[@class='col-md-12 well ng-scope']//input/following-sibling::span")
    public List<WebElement> examAnswersTxt;
    @FindBy(xpath = "//div[@class='col-md-12 well ng-scope']//input/following-sibling::span[contains(text(),'correct')]")
    public List<WebElement> correctAnswers;
    @FindBy(xpath = "//div[@class='col-md-12 well ng-scope']//input/following-sibling::span[contains(text(),'wrong')]")
    public List<WebElement> wrongAnswersLst;
    @FindBy(xpath = "//h2/div")
    public WebElement examResultsTxt;

    @FindBy(xpath = "//div[@class='col-md-12 well ng-scope']//label[contains(@class,'control')]")
    public List<WebElement> questions;


    public void scoreExamWithCorrectPercentageByIds(String email, int percent){
        try {
            Thread.sleep(1000);
            driver.navigate().to(KNOWLEDGE_EXAM_URL);
            startExamBtn.click();

            List<WebElement> options = driver.findElements(By.xpath("//div[@class='col-md-12 well ng-scope']//input"));
            Integer keId =  queries.getKnowledgeExam(email);
            List<Integer> correctIdList = queries.getCorrectAnswers(keId);

            Map<String, WebElement> wrongAnswerMap = new HashMap<>();

            Set<Integer> correctSet = new HashSet<>(correctIdList);
            for (WebElement element:options){
                String iValue = element.getAttribute("value");
                String iChoice = element.getAttribute("name");
                System.out.println(iValue);
                if (correctSet.contains(Integer.parseInt(iValue))){
                    element.click();
                } else {
                    wrongAnswerMap.put(iChoice,element);
                }
            }

            List <WebElement> wrongList = new ArrayList (wrongAnswerMap.values());

            int wrongCnt = 100 - percent;
            for (int i=0;i<wrongCnt;i++){
                WebElement element = wrongList.get(i);
                element.click();
            }

            scoreExamBtn.click();

            System.out.println("Results: " + examResultsTxt.getText());

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scoreExamWithCorrectPercentage(int percent) {
        try {
            Thread.sleep(1000);
            driver.navigate().to(KNOWLEDGE_EXAM_URL);
            startExamBtn.click();

            for (WebElement answer : correctAnswers) {
                answer.click();
            }

            Set<Integer> wrongAnswers = new HashSet<>();
            while (wrongAnswers.size() < (100 - percent)) {
                int number = th.getRandomNumberBetween(2,198);
                if (number % 2 == 0) {
                    wrongAnswers.add(number);
                }
            }
            List<Integer> sortedWrongAnswers = new ArrayList<>(wrongAnswers);
            Collections.sort(sortedWrongAnswers);

            for (Integer answer : sortedWrongAnswers) {
                wrongAnswersLst.get(answer).click();
            }
            scoreExamBtn.click();

            List<Integer> wrongAnswersFromDb = new ArrayList<>();
            wrongAnswersFromDb = queries.getQuestionNumbersAnsweredIncorrectly();
            Thread.sleep(1000);
            Collections.sort(wrongAnswersFromDb);

            List<Integer> sortedQuestions = new ArrayList<>();
            for (Integer answer : sortedWrongAnswers) {
                sortedQuestions.add((answer / 2) + 1);
            }

            System.out.println("Wrong answers clicked: " + sortedWrongAnswers.size());
            System.out.println("Incorrect Answers from DB: " + wrongAnswersFromDb.size() + "\n" + wrongAnswersFromDb);
            System.out.println("Questions Answered Incorrectly on Test: " + sortedQuestions.size() + "\n" + sortedQuestions);
            sortedQuestions.removeAll(wrongAnswersFromDb);

            Thread.sleep(2000);
            System.out.println("Results: " + examResultsTxt.getText());
//
//            Assert.assertTrue(examResultsTxt.getText().contains(String.valueOf(percent)));
//            System.out.println("Knowledge Exam passed with " + percent + "% accuracy.");

            login.logInToOpenSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
