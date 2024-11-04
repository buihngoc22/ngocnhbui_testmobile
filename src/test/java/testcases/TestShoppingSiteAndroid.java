package testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.MagentoPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static pageObjects.MagentoPage.*;

public class TestShoppingSiteAndroid {
    protected final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    protected AppiumDriver driver;
    private MagentoPage magentoPage;

    @BeforeClass
    public void setup() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .setNewCommandTimeout(Duration.ofSeconds(30))
                .withBrowserName("Chrome");

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        magentoPage = new MagentoPage(driver);
    }

    @Test
    public void goShopping() {
        // Q.1 Visit page
        driver.get("https://magento.softwaretestingboard.com/");

        // Q.2 Add one item to my cart
        magentoPage.navigateToProductPage("Women", "Jackets");
        magentoPage.addProductToCart("Neve Studio Dance Jacket","XS","Blue","2");

        // Q.3 Navigate to my cart and verify items
        magentoPage.tapButton(btnMyCart);
        magentoPage.verifyProductsInCart("Neve Studio Dance Jacket","XS","Blue","2");

        // Q.4 Proceed to check out
        magentoPage.scrollToView(btnProceedToCheckout);
        magentoPage.tapButton(btnProceedToCheckout);

        // Q.5 Enter the shipping address
        magentoPage.enterShippingAddress("buihngoc22@gmail.com", "Ngoc", "Bui", "LTT", "Da Nang", "Alaska","12345","United States", "09203484");
        magentoPage.tapButton(cbShippingMethod);
        magentoPage.tapButton(btnNext);
        magentoPage.tapButton(btnPlaceOrder);

        // Q.6 Verify the email on Thank You page
        magentoPage.verifyText(lbEmail,"buihngoc22@gmail.com");

        // Advanced
        magentoPage.navigateToProductPage("Women", "Jackets");
        magentoPage.addProductToCart("Neve Studio Dance Jacket","XS","Blue","2");
        magentoPage.navigateToProductPage("Men", "Tees");
        magentoPage.addProductToCart("Strike Endurance Tee","M","Black","1");
        magentoPage.navigateToProductPage("Men", "Pants");
        magentoPage.addProductToCart("Aether Gym Pant","34","Brown","1");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
