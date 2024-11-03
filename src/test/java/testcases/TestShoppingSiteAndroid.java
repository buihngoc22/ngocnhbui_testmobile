package testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.MagentoPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestShoppingSiteAndroid {
    protected final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    protected AppiumDriver driver;
    private MagentoPage magentoPage;

    @BeforeClass
    public void setup() throws MalformedURLException {
//        WebDriverManager.chromedriver().setup();

        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .setNewCommandTimeout(Duration.ofSeconds(30))
                .withBrowserName("Chrome");

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        magentoPage = new MagentoPage(driver);
    }

    @Test
    public void test() {
        driver.get("https://magento.softwaretestingboard.com/");
        // Add additional test steps here
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
