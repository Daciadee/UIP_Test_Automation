package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestHelpers;

import java.util.List;
import java.util.Set;

import static utils.GetProperties.*;
import static utils.GetProperties.PASSWORD;
import static utils.GetProperties.USER_EMAIL;

public class LoginPage {

    private WebDriver driver;
    private TestHelpers th;
    private MockLoginPage mock;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        th = new TestHelpers(driver);
        mock = new MockLoginPage(driver);
    }

    @FindBy(xpath = "//div[@class='col-md-12 well']/a")
    public WebElement loginLnk;

    // Utah ID Login //
    @FindBy(id = "utahId")
    public WebElement usernameInputTbx;
    @FindBy(id = "password")
    public WebElement passwordInputTbx;
    @FindBy(id = "loginButton")
    public WebElement loginBtn;

    // UID Logout //
    @FindBy(id = "dashboard")
    public WebElement profileCbx;
    @FindBy(id = "logoutLink")
    public WebElement logoutBtn;

    // Google Login //
    @FindBy(xpath = "//div[@id='openid_btns']/a[@title='log in with Google']")
    public WebElement googleIdLoginBtn;
    @FindBy(id = "identifierId")
    public WebElement googleUserIdTbx;
    @FindBy(name = "password")
    public WebElement googlePasswordTbx;
    @FindBy(xpath = "//span[text()='Next']")
    public WebElement googleNextBtn;
    @FindBy(id = "identifierLink")
    public WebElement googleUseAnotherAccountBtn;
    @FindBy(xpath = "//a[@title='Log out of Google']")
    public WebElement googleLogoutBtn;

    // Google Logout //
    @FindBy(xpath = "//a[contains(@title,'Google Account')]")
    public WebElement googleProfileBtn;
    @FindBy(xpath = "//a[text()='Sign out']")
    public WebElement googleSignOutBtn;

    // Yahoo Login //
    @FindBy(xpath = "//div[@id='openid_btns']/a[@title='log in with Yahoo']")
    public WebElement yahooIdLoginBtn;
    @FindBy(id = "login-username")
    public WebElement yahooUserIdTbx;
    @FindBy(id = "login-passwd")
    public WebElement yahooPasswordTbx;
    @FindBy(name = "login-signin")
    public WebElement yahooNextBtn;

    // Facebook Login //
    @FindBy(xpath = "//div[@id='openid_btns']/a[@title='log in with Facebook']")
    public WebElement facebookIdLoginBtn;


    public void loginToUIP(String loginType) {
        try {
//            String login;
//            if (USER_EMAIL.contains("utah.gov")) {
//                login = "utah";
//            } else if (USER_EMAIL.contains("google")) {
//                login = "google";
//            } else if (USER_EMAIL.contains("yahoo")) {
//                login = "yahoo";
//            } else {
//                login = loginType;
//            }
            switch (loginType) {
                case "utah":
                    logIntoAppWithUtahId(USER_EMAIL, PASSWORD);
                    break;
                case "gmail":
                    logIntoAppWithGoogleId(USER_EMAIL, PASSWORD);
                    break;
                case "yahoo":
                    logIntoAppWithYahooId(USER_EMAIL, PASSWORD);
                    break;
                case "facebook":
                    logIntoAppWithFacebookId(USER_EMAIL, PASSWORD);
                    break;
                case "mock":
                    mock.loginToMockSingleSignOn(ADMIN_EMAIL);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logIntoAppWithUtahId(String email, String password) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            loginLnk.click();
            usernameInputTbx.sendKeys(email);
            passwordInputTbx.sendKeys(password);
            loginBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logIntoAppWithGoogleId(String username, String password) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            loginLnk.click();
            googleIdLoginBtn.click();
//            driver.switchTo().alert().accept();

            th.setImplicitWaitTime(driver, 1);
            try {
                if (googleUseAnotherAccountBtn.isDisplayed())
                    googleUseAnotherAccountBtn.click();
            } catch (Exception e) {
                System.out.println("Switch account button not displayed, skipping.");
            }
            th.restoreImplicitWaitTime(driver);

            if (driver.getCurrentUrl().contains("google")) {
                googleUserIdTbx.sendKeys(username);
                googleNextBtn.click();
                Thread.sleep(1000);
                googlePasswordTbx.sendKeys(password);
                Thread.sleep(1000);
                googleNextBtn.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logIntoAppWithYahooId(String username, String password) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            loginLnk.click();
            String originalHandle = driver.getWindowHandle();

            yahooIdLoginBtn.click();
            if (yahooUserIdTbx.getAttribute("value").length() > 0) {
                yahooUserIdTbx.clear();
            }

            yahooUserIdTbx.sendKeys(username, Keys.ENTER);
            yahooPasswordTbx.sendKeys(password, Keys.ENTER);

            while (driver.getTitle().equals("Yahoo")) {
                yahooNextBtn.click();
            }

            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(originalHandle)) {
                    driver.switchTo().window(handle);
                    driver.close();
                    driver.switchTo().window(originalHandle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logIntoAppWithFacebookId(String username, String password) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            loginLnk.click();

            facebookIdLoginBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logInToOpenSession() {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            loginLnk.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOutOfOpenSession() {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
            }
            Thread.sleep(1000);
            profileCbx.click();
            Thread.sleep(1000);
            logoutBtn.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logBackInToUIP(String loginType) {
        try {
            if (!driver.getCurrentUrl().equals(BASE_URL)) {
                driver.navigate().to(BASE_URL);
                try {
                    loginToUIP(loginType);
                } catch (Exception e) {
                    logInToOpenSession();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
