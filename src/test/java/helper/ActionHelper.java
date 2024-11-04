package helper;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionHelper {
    protected WebDriver driver;

    public ActionHelper(WebDriver driver) {
        this.driver = driver;
    }

    protected JavascriptExecutor jsExecutor() {
        return (JavascriptExecutor) driver;
    }

    public void tapButton(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        } catch (Exception e) {
            jsExecutor().executeScript("arguments[0].click();", driver.findElement(by));
        }
    }

    public void enterText(By by, String value) {
        WebElement element = waitForElementToBeClickable(by);
        element.clear();
        element.sendKeys(value);
    }

    public WebElement waitForElementToBeClickable(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForElementToBeVisible(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public boolean waitForTextToBe(By by, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = waitForElementToBeVisible(by);
        return wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
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

    public void verifyValue(By by, String expectedValue) {
        String actualValue = driver.findElement(by).getAttribute("value");
        if (actualValue.contains(expectedValue)) {
            System.out.println("Value displays as expected: " + expectedValue);
        } else {
            System.out.println("Value does not display as expected. Expected: " + expectedValue + ", but found: " + expectedValue);
            throw new AssertionError("Value does not display as expected");
        }
    }

    public void scrollToView(By by) {
        try {
            jsExecutor().executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
        } catch (JavascriptException e) {
            WebElement element = driver.findElement(by);
            int x = element.getRect().x;
            int y = element.getRect().y;
            String js = String.format("window.scrollTo(%s, %s);", x, y);
            jsExecutor().executeScript(js);
        }
    }

    public void scrollUntilVisible(String visibleText) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))"));
    }

    public void selectOptionByVisibleText(By by, String visibleText) {
        WebElement dropdownElement = driver.findElement(by);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(visibleText);
    }
}
