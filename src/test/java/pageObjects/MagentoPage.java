package pageObjects;

import helper.ActionHelper;
import org.openqa.selenium.*;

public class MagentoPage extends ActionHelper {
    public MagentoPage(WebDriver driver) {
        super(driver);
    }

    public static final By btnMenu = By.xpath("//span[@data-action='toggle-nav']");
    public static final By btnAddToCartInProductPage = By.id("product-addtocart-button");
    public static final By btnMyCart = By.xpath("//a[@href='https://magento.softwaretestingboard.com/checkout/cart/']");
    public static final By btnProceedToCheckout = By.xpath("//ul//button[@title='Proceed to Checkout']");
    public static final By tbEmail = By.id("customer-email");
    public static final By tbFirstName = By.xpath("//input[@name='firstname']");
    public static final By tbLastName = By.xpath("//input[@name='lastname']");
    public static final By tbStreetDress = By.xpath("//input[@name='street[0]']");
    public static final By tbCity = By.xpath("//input[@name='city']");
    public static final By tbPostCode = By.xpath("//input[@name='postcode']");
    public static final By drdCountry = By.xpath("//select[@name='country_id']");
    public static final By drdState = By.xpath("//select[@name='region_id']");
    public static final By tbPhone = By.xpath("//input[@name='telephone']");
    public static final By btnNext = By.xpath("//span[text()='Next']//parent::button");
    public static final By btnPlaceOrder = By.xpath("//button[@title='Place Order']");
    public static final By lbEmail = By.xpath("//span[@data-bind='text: getEmailAddress()']");
    public static final By cbShippingMethod = By.xpath("//input[@value='flatrate_flatrate']");
    public static final By btnViewCart = By.xpath("//a[contains(.,'View and Edit Cart')]");
    public static final By lbProductTitle = By.xpath("//tr[@class='item-info']//div//a");
    public static final By txQty = By.xpath("//input[@title='Qty']");
    public static String btnAddToCart = "//a[contains(text(),'%s')]//ancestor::div[@class='product-item-info']//button";
    public static String lbProductName = "//span[contains(.,'%s')]";
    public static String productOptions = "//div[@attribute-code='%s']//div[@option-label='%s']";
    public static String productDetails = "//dl[@class='item-options']//dt[text()='%s']//following-sibling::dd[1]";
    public static String btnGender = "//span[text()='%s']//parent::a";
    public static String btnProductType = "//span[text()='%s']//parent::a//following-sibling::ul//li//a[contains(.,'%s')]";

    public void navigateToProductPage(String gender, String productType){
        tapButton(btnMenu);
        tapButton(By.xpath(String.format(btnGender, gender)));
        tapButton(By.xpath(String.format(btnProductType, gender, productType)));
    }

    public void addProductToCart(String productName, String size, String color, String qty) {
        tapButton(By.xpath(String.format(btnAddToCart, productName)));
        waitForElementToBeVisible(By.xpath(String.format(lbProductName, productName)));
        tapButton(By.xpath(String.format(productOptions, "size", size)));
        tapButton(By.xpath(String.format(productOptions, "color", color)));
        enterText(By.id("qty"), qty);
        tapButton(btnAddToCartInProductPage);
    }

    public void verifyProductsInCart(String productName, String size, String color, String qty){
        tapButton(btnMyCart);
        tapButton(btnViewCart);
        scrollToView(lbProductTitle);
        waitForTextToBe(lbProductTitle, "Neve Studio Dance Jacket");
        verifyText(By.xpath(String.format(productDetails,"Size")), size);
        verifyText(By.xpath(String.format(productDetails,"Color")), color);
        verifyValue(txQty, qty);
    }

    public void enterShippingAddress(String email, String firstName, String lastName, String streetAddress, String city, String state, String postcode, String country, String phoneNumber) {
        waitForElementToBeVisible(By.xpath("//div[text()='Shipping Address']"));
        scrollToView(tbEmail);
        enterText(tbEmail, email);
        enterText(tbFirstName, firstName);
        enterText(tbLastName, lastName);
        enterText(tbStreetDress, streetAddress);
        enterText(tbCity, city);
        selectOptionByVisibleText(drdState, state);
        enterText(tbPostCode, postcode);
        selectOptionByVisibleText(drdCountry, country);
        enterText(tbPhone, phoneNumber);
    }
}
