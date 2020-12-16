package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import utils.TestHelpers;

import java.util.*;

import static utils.GetProperties.*;

public class DashboardRaterPage {

    private WebDriver driver;
    private LoginPage login;
    private TestHelpers th;

    public DashboardRaterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        login = new LoginPage(driver);
        th = new TestHelpers(driver);
    }

    // Tabs //
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']//a[text()='Other']")
    public WebElement raterTab;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']//a[text()='Assignments']")
    public WebElement assignmentsTab;
    @FindBy(xpath = "//ul[@class='nav nav-tabs ng-scope']//a[text()='Complete']")
    public WebElement completeTab;

    // Assignments Table //
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[1]")
    public WebElement firstNameFilterTbx;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[2]")
    public WebElement lastNameFilterTbx;
    @FindBy(xpath = "(//div[@ui-grid-filter]//input)[3]")
    public WebElement emailFilterTbx;
    @FindBy(xpath = "//div[@class='ui-grid-canvas']/div[@ng-repeat]")
    public List<WebElement> assignmentRows;
    @FindBy(xpath = "//div[@class='ui-grid-canvas']/div[@ng-repeat]//a")
    public List<WebElement> viewBtns;
    @FindBy(xpath = "//div[@class='ng-scope']/h4/following-sibling::div[@class='ng-scope']")
    public WebElement noAssignmentsTxt;

    //** Rating Screen **//
    // Questions //
    @FindBy(xpath = "//div[@id='raterAnswersAccordion']//input[@type='radio']")
    public List<WebElement> questions;
    @FindBy(xpath = "//div[contains(@id,'aterCollapse')]")
    public List<WebElement> categories;
    @FindBy(xpath = "//i[contains(@id,'raterAnswerFlag')]")
    public List<WebElement> flagBtns;

    // Buttons //
    @FindBy(id = "saveRateButton")
    public WebElement submitRatingBtn;
    @FindBy(id = "cancelRateButton")
    public WebElement cancelBtn;
    @FindBy(id = "rejectRateButton")
    public WebElement conflictOfInterestBtn;


    public void rateAllPerformanceExams(String lastName, String result, List<String> raters) {
        try {
            // Wait for submitted data to populate into the database
            Thread.sleep(8000);

            for (String rater : raters) {
                loginAsRater(rater);
                rateAllExamsForFilteredInterpreterWithPercentCorrect(lastName, result);
                login.logOutOfOpenSession();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginAsRater(String cookieString) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }

            Thread.sleep(500);
            org.openqa.selenium.Cookie cookie = th.createUIPCookie(cookieString);
            driver.manage().addCookie(cookie);

            login.loginLnk.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rateAllExamsForFilteredInterpreterWithPercentCorrect(String lastName, String result) {
        try {
            raterTab.click();
            assignmentsTab.click();

            if (driver.getPageSource().contains("Sorry, No Results Found.")) {
                Thread.sleep(1000);
                completeTab.click();
                Thread.sleep(1000);
                assignmentsTab.click();
            }

            lastNameFilterTbx.sendKeys(lastName);
            int exams = viewBtns.size();
            for (int i = 0; i < exams; i++) {
                if (i > 0) {
                    lastNameFilterTbx.sendKeys(lastName);
                }
                selectInterpreterFromTableByEmail();
                if (result.equals("pass")) {
                    ratePerformanceExamAsPassed();
                } else {
                    ratePerformanceExamAsFailed();
                }
//                ratePerformanceExamWithPercentCorrect(percent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectInterpreterFromTableByEmail() {
        try {
            if (viewBtns.size() > 0) {
                viewBtns.get(0).click();
            } else {
                System.out.println("No exams available to rate for interpreter with email address, '"+ USER_EMAIL +"'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectInterpreterFromTableByLastName(String lastName) {
        try {
            if (viewBtns.size() > 0) {
                emailFilterTbx.sendKeys(lastName);
                viewBtns.get(0).click();
            } else {
                System.out.println("No exams available to rate for interpreter with last name, '"+ lastName +"'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ratePerformanceExamAsPassed() {
        try {
            String questionsXPath = "";
            List<List<WebElement>> categoriesAndQuestions = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                questionsXPath = String.format("(%s)[%d]//input", th.getXPathTextFromElement(categories.get(i)), i + 1);
                categoriesAndQuestions.add(driver.findElements(By.xpath(questionsXPath)));
            }
            for (List<WebElement> questions : categoriesAndQuestions) {
                questions.get(0).click();
            }
            submitRatingBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ratePerformanceExamAsFailed() {
        try {
            String questionsXPath = "";
            List<List<WebElement>> categoriesAndQuestions = new ArrayList<>();
            for (int i = 0; i < categories.size(); i++) {
                questionsXPath = String.format("(%s)[%d]//input", th.getXPathTextFromElement(categories.get(i)), i + 1);
                categoriesAndQuestions.add(driver.findElements(By.xpath(questionsXPath)));
            }
            for (List<WebElement> questions : categoriesAndQuestions) {
                questions.get(questions.size() - 1).click();
            }
            submitRatingBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void ratePerformanceExamWithPercentCorrect(int percent) {
//        try {
//            int answersToFail = yesAnswers.size() - ((int) Math.ceil(yesAnswers.size() * ((float)percent/100F)));
//
//            for (WebElement answer : yesAnswers) {
//                answer.click();
//            }
//
//            String bodyText = driver.getPageSource();
//            int questionNum = 0;
//            Set<Integer> failedAnswers = new HashSet<>();
//            while (failedAnswers.size() < answersToFail) {
//                if (bodyText.contains("American Sign Language to Spoken English")) {
//                    if (CERT_TYPE.equals("professional")) {
//                        if (bodyText.contains("EnglishRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 8);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 8);
//                        }
//                    }
//                }
//                else if (bodyText.contains("English-bound Signing to Spoken English")) {
//                    if (CERT_TYPE.equals("professional")) {
//                        if (bodyText.contains("EnglishRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 8);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 8);
//                        }
//                    }
//                }
//                else if (bodyText.contains("Interactive Role Play")) {
//                    if (CERT_TYPE.equals("professional")) {
//                        if (bodyText.contains("DeafRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 10);
//                        } else if (bodyText.contains("EnglishRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 8);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 7 || questionNum == 16);
//                        }
//                    } else {
//                        if (bodyText.contains("DeafRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 8);
//                        } else if (bodyText.contains("EnglishRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 7);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 14);
//                        }
//                    }
//                }
//                else if (bodyText.contains("Sign to Spoken English")) {
//                    if (CERT_TYPE.equals("novice")) {
//                        if (bodyText.contains("EnglishRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6 || questionNum == 7);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 7);
//                        }
//                    }
//                }
//                else if (bodyText.contains("Spoken English to American Sign Language")) {
//                    if (CERT_TYPE.equals("professional")) {
//                        if (bodyText.contains("DeafRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 10 || questionNum == 11 || questionNum == 12);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 7);
//                        }
//                    }
//                }
//                else if (bodyText.contains("Spoken English to English-bound Signing")) {
//                    if (CERT_TYPE.equals("professional")) {
//                        if (bodyText.contains("DeafRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 8 || questionNum == 9 || questionNum == 10);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 7);
//                        }
//                    }
//                }
//                else if (bodyText.contains("Spoken English to Sign")) {
//                    if (CERT_TYPE.equals("novice")) {
//                        if (bodyText.contains("DeafRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 8);
//                        } else if (bodyText.contains("InterpreterRater")) {
//                            do {
//                                questionNum = th.getRandomNumberBetween(0, yesAnswers.size() - 1);
//                            } while (questionNum == 6);
//                        }
//                    }
//                }
//                failedAnswers.add(questionNum);
//            }
//
//            List<Integer> sortedFailedAnswers = new ArrayList<>(failedAnswers);
//            Collections.sort(sortedFailedAnswers);
//
//            Thread.sleep(500);
//            for (Integer answer : sortedFailedAnswers) {
//                noAnswers.get(answer).click();
//            }
//
//            submitRatingBtn.click();
//            driver.switchTo().alert().accept();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
