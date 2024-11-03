package pageObjects;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagentoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MagentoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static final By btnCreateContact = AppiumBy.accessibilityId("Create contact");
    public static final By btnSave = AppiumBy.id("com.google.android.contacts:id/toolbar_button");
    public static final By btnClosePopup = AppiumBy.accessibilityId("Close Popup Window");
    public static final By btnBack = AppiumBy.accessibilityId("Navigate up");
    public static final By lbCreatedContact = AppiumBy.xpath("//android.widget.TextView[@content-desc='Ngoc Bui']");
    public static final By btnMoreOptions = AppiumBy.accessibilityId("More options");
    public static final By btnDelete = AppiumBy.xpath("//android.widget.TextView[@text='Delete']");
    public static final By btnConfirmDelete = AppiumBy.id("android:id/button1");
    public static final By btnSetCalendar = AppiumBy.id("android:id/button1");
    public static final By tbFirstName = AppiumBy.xpath("//android.widget.EditText[@text='First name']");
    public static final By tbLastName = AppiumBy.xpath("//android.widget.EditText[@text='Last name']");
    public static final By tbCompany = AppiumBy.xpath("//android.widget.EditText[@text='Company']");
    public static final By tbPhone = AppiumBy.xpath("//android.widget.EditText[@text='Phone']");
    public static final By tbEmail = AppiumBy.xpath("//android.widget.EditText[@text='Email']");
    public static final By tbSignificantDate = AppiumBy.xpath("//android.widget.EditText[@text='Significant date']");
    public static final By lbContactName = AppiumBy.id("com.google.android.contacts:id/large_title");
    public static final By lbOrganization = AppiumBy.id("com.google.android.contacts:id/organization_name");
    public static final By txtMobilePhone = AppiumBy.xpath("//android.widget.RelativeLayout[contains(@content-desc,'Call Mobile')]//android.widget.TextView[@resource-id='com.google.android.contacts:id/header']");
    public static final By txtEmail = AppiumBy.xpath("//android.widget.RelativeLayout[contains(@content-desc,'Email Home')]//android.widget.TextView[@resource-id='com.google.android.contacts:id/header']");
    public static final By txtContactList = AppiumBy.xpath("//android.widget.ListView[@resource-id='android:id/list']//android.view.ViewGroup");

    public void tapButton(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    public void enterText(By by, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).sendKeys(value);
    }

    public void scrollUntilVisible(String visibleText) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))"));
    }

    public void verifyText(By locator, String expectedText) {
        WebElement element = driver.findElement(locator);
        String actualText = element.getText();
        if (actualText.contains(expectedText)) {
            System.out.println("Text displays as expected: " + actualText);
        } else {
            System.out.println("Text does not display as expected. Expected: " + expectedText + ", but found: " + actualText);
            throw new AssertionError("Text does not display as expected");
        }
    }

    public void verifyContactIsDeleted(By locator) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> elements = driver.findElements(locator);
        if (elements.size() != 0) {
            throw new AssertionError("Expected list to be empty, but found " + elements.size() + " elements.");
        } else {
            System.out.println("List is empty as expected.");
        }
    }

    public void selectDate(String type, String date) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        By lastMonth = By.xpath("//android.widget.Button[@text='Oct']");
        By lastDay = By.xpath("//android.widget.Button[@text='" + String.format("%02d", yesterday.getDayOfMonth()) + "']");
        By lastYear = By.xpath("//android.widget.Button[@text='2023']");
        By selectDate = By.xpath("//android.widget.EditText[@resource-id='android:id/numberpicker_input' and @text='" + date + "']");
        WebElement element;

        if (type.equals("month")) {
            element = driver.findElement(lastMonth);
        } else if (type.equals("day")) {
            element = driver.findElement(lastDay);
        } else {
            element = driver.findElement(lastYear);
        }

        int monthX = element.getLocation().getX();
        int monthY = element.getLocation().getY();

        while (driver.findElements(selectDate).isEmpty()) {
            Map<String, Object> tapParams = new HashMap<>();
            tapParams.put("x", monthX);
            tapParams.put("y", monthY);
            tapParams.put("count", 1);

            ((JavascriptExecutor) driver).executeScript("mobile: clickGesture", tapParams);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Selected: " + type + " = " + date);
    }
}
