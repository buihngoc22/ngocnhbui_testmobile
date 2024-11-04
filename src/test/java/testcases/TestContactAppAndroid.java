package testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.ContactPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static pageObjects.ContactPage.*;

public class TestContactAppAndroid {
    protected final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    protected AppiumDriver driver;
    private ContactPage contactPage;

    @BeforeClass
    public void setup() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .setNewCommandTimeout(Duration.ofSeconds(30))
                .setAppPackage("com.google.android.contacts")
                .setAppActivity("com.android.contacts.activities.PeopleActivity");

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        contactPage = new ContactPage(driver);
    }

    @Test(priority = 1)
    public void createANewContact() throws InterruptedException {
        // Q.1-4: Create a new contact
        contactPage.tapButton(btnCreateContact);
        contactPage.enterText(tbFirstName, "Ngoc");
        contactPage.enterText(tbLastName, "Bui");
        contactPage.enterText(tbCompany, "KMS Company");
        contactPage.enterText(tbPhone, "0902999999");
        contactPage.enterText(tbEmail, "buihngoc22@gmail.com");
        contactPage.tapButton(btnSave);

        // Q.5: Find and verify the contact
        contactPage.tapButton(btnClosePopup);
        contactPage.tapButton(btnBack);
        contactPage.tapButton(lbCreatedContact);
        contactPage.verifyText(lbContactName, "Ngoc Bui");
        contactPage.verifyText(lbOrganization, "KMS Company");
        contactPage.verifyText(txtMobilePhone, "0902999999");
        contactPage.verifyText(txtEmail, "buihngoc22@gmail.com");

        // Q.6: Delete the contact then verify
        contactPage.tapButton(btnMoreOptions);
        contactPage.tapButton(btnDelete);
        contactPage.tapButton(btnConfirmDelete);
        contactPage.verifyContactIsDeleted(txtContactList);

        // Advanced
        contactPage.tapButton(btnCreateContact);
        contactPage.scrollUntilVisible("Significant date");
        contactPage.tapButton(tbSignificantDate);
        contactPage.selectDate("month","Sep");
        contactPage.selectDate("day","06");
        contactPage.selectDate("year", "2023");
        contactPage.tapButton(btnSetCalendar);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
