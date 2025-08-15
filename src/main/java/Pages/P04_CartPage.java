package Pages;

import Utilities.Utilities;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P04_CartPage {
    WebDriver driver;

    public static By emptyCartMessage = By.className("order-summary-content");
    public static By laptopInCartLocator = By.xpath("(//a[@href='/141-inch-laptop'])[3]");
    public static By productBox = By.xpath(("(//input[@type='checkbox'])[1]"));
    public static By productQuantityLocator = By.xpath("(//input[@type='text'])[2]");
    public static By productPriceLocator = By.xpath("(//span[contains(@class,'product-price')])[4]");
    private final By checkoutButtonLocator = By.id("checkout");
    public static By termsOfServiceCheckboxLocator = By.className("ui-dialog-title");
    private final By continueShoppingButtonLocator = By.xpath("//input[@name='continueshopping']");
    private final By acceptTermsCheckBox = By.xpath("(//input[@type='checkbox'])[2]");
    private final By cartLocator = By.xpath("(//span[@class='cart-label'])[1]");
    private final By addBraceletToCartButton = By.xpath("(//input[@value='Add to cart'])[1]");
    private final By braceletLocator = By.id("add-to-cart-button-71");
    private final By addNecklaceToCartButton = By.xpath("(//input[@value='Add to cart'])[2]");
    private final By addBraceletLength = By.name("product_attribute_71_10_16");
    public static By closePopUpLocator = By.xpath("//span[contains(@class,'ui-button-icon-primary')]");
    public static By closeNotificationPopUpLocator = By.xpath("//span[@title='Close']");


    public P04_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void changeProductQuantity(String quantity) {
        WebElement quantityBox = Utilities.byToWebElement(driver, productQuantityLocator);

        Utilities.generalWait(driver)
                .until(ExpectedConditions.elementToBeClickable(quantityBox));
        quantityBox.click();
        quantityBox.clear();
        quantityBox.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        quantityBox.sendKeys(quantity);
        quantityBox.sendKeys(Keys.ENTER);

    }

    public void clearCart() {
        WebElement quantityBox = Utilities.byToWebElement(driver, productBox);
        quantityBox.click();
        quantityBox.sendKeys(Keys.ENTER);
    }

    public double getProductPrice() {
        String price = Utilities.getElementText(driver, productPriceLocator);
        return Double.parseDouble(price);
    }

    public P04_CartPage clickOnAcceptTermsCheckBox() {
        Utilities.clickOnElement(driver, acceptTermsCheckBox);
        return this;
    }

    public void clickCheckoutButtonWithoutAcceptingTerms() {
        Utilities.clickOnElement(driver, checkoutButtonLocator);
    }

    public P04_CartPage openCartPage() {
        Utilities.clickOnElement(driver, cartLocator);
        return new P04_CartPage(driver);
    }

    public P04_CartPage clickContinueShoppingButton(){
        Utilities.clickOnElement(driver, continueShoppingButtonLocator);
        return this;
    }

    public P04_CartPage addNecklaceToCart(){
        Utilities.clickOnElement(driver, addNecklaceToCartButton);
        return this;
    }

    public P04_CartPage addBraceletToCart(){
        Utilities.clickOnElement(driver, addBraceletToCartButton);
        return this;
    }

    public void addBracelet(){
        Utilities.enterText(driver, addBraceletLength, "10");
        Utilities.clickOnElement(driver, braceletLocator);
    }

    public void clickCheckoutButton() {
        Utilities.clickOnElement(driver, checkoutButtonLocator);
    }

}
