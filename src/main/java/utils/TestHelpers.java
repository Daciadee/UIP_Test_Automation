package utils;

import dws.sso.encryption.SSOEncryption;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.GetProperties.INTERPRETER_RATER_2_COOKIE;
import static utils.GetProperties.OUTPUT_FILE;
import static utils.GetProperties.USER_EMAIL;

public class TestHelpers {

    private WebDriver driver;
    private FileUtilities fu;
    private Queries query;

    public TestHelpers(WebDriver driver) {
        this.driver = driver;
        fu = new FileUtilities();
        query = new Queries();
    }

    public void selectComboBoxOption(WebElement element, String option) {
        Select selector = new Select(element);
        selector.selectByVisibleText(option);
    }

    public void clearText(WebElement element, WebDriver driver) {
        Actions action = new Actions(driver);
        try {
            action.clickAndHold(element);
//            element.click();
            action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            action.sendKeys(Keys.DELETE).perform();
            action.release(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastName() {
        return query.getInterpreterLastName();
    }

    public int getRandomNumberBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void restoreImplicitWaitTime(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void setImplicitWaitTime(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public org.openqa.selenium.Cookie createUIPCookie(String cookieString) throws UnsupportedEncodingException {
        driver.manage().deleteAllCookies();
        Cookie cookie = new Cookie("DWSSSO", URLEncoder.encode(SSOEncryption.getInstance().getSSOCipherText(cookieString, 200), "UTF-8"));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return new org.openqa.selenium.Cookie("DWSSSO", cookie.getValue());
    }

    public String getTodaysDatePlusDays(int days) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        Date newDate = calendar.getTime();

        return dateFormat.format(newDate);
    }

    public String getTodaysDatePlusYears(int years) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, years);

        Date newDate = calendar.getTime();

        return dateFormat.format(newDate);
    }

    public String getXPathTextFromElement(WebElement element) {
        String xpath="";
        String elemString = element.toString();
        String regex = "(\\/\\/.*'\\)\\]|\\]^]])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(elemString);
        if (matcher.find())
            xpath = matcher.group(0);
        return xpath;
    }

}
