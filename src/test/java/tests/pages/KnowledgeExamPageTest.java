package tests.pages;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.KnowledgeExamPage;

import static utils.GetProperties.KNOWLEDGE_EXAM_URL;

public class KnowledgeExamPageTest {

    private KnowledgeExamPage kep;

    public KnowledgeExamPageTest(WebDriver driver) {
        kep = new KnowledgeExamPage(driver);
        driver.navigate().to(KNOWLEDGE_EXAM_URL);
    }

    @Test
    public void answerAllKnowledgeExamQuestionsCorrectlyTest() {
        kep.scoreExamWithCorrectPercentage(100);
    }
}
