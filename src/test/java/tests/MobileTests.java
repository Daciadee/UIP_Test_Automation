package tests;

import base.TestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static utils.GetProperties.ENVIRONMENT;

public class MobileTests {

    private AppiumDriver driver;

    @BeforeTest
    private void beforeTest() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "UIPTest");
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver(url, caps);
    }

    @Test
    public void test() {
        try {
            if (ENVIRONMENT.equalsIgnoreCase("prod")){
                driver.navigate().to("https://jobs.utah.gov/jsp/uip/");
            } else{
                driver.navigate().to("https://"+ ENVIRONMENT +".jobs.utah.gov/jsp/uip/");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
