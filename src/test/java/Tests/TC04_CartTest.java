package Tests;

import Pages.P03_HomePage;
import Pages.P04_CartPage;
import Utilities.DataUtil;
import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Utilities.LogUtils;
import java.io.IOException;
import java.time.Duration;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestMethodListenerClass;
import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestMethodListenerClass.class})
public class TC04_CartTest {

    By numberOnCartLocator = By.cssSelector("span[class = 'cart-qty']");

    @BeforeMethod
    public void browserSetup() throws IOException {
        String browser = System.getProperty("Browser") != null ? System.getProperty("Browser") : DataUtil.getPropertyValue("environments", "Browser");
        LogUtils.info(System.getProperty("Browser"));
        setUpBrowser(browser);
        LogUtils.info("Chrome is now opened");
        getDriver().get(DataUtil.getPropertyValue("environments", "Login_page"));
        LogUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Utilities.login(getDriver());
    }

    @Test(priority = 1)
    public void checkLaptopExistsInCartAfterAdding() throws IOException {
        new Pages.P03_HomePage(getDriver()).clickAddToCartForLaptop();
        Utilities.clickOnElement(getDriver(), P04_CartPage.closeNotificationPopUpLocator);
        Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P03_HomePage.cartLocator));
        new Pages.P03_HomePage(getDriver()).openCartPage();
        WebElement item = Utilities.generalWait(getDriver())
                .until(ExpectedConditions.visibilityOfElementLocated(P04_CartPage.laptopInCartLocator));
        LogUtils.info("Laptop item is displayed in cart");
        Utilities.takeScreenshot(getDriver(), "LaptopItemInCart");
        Assert.assertTrue(item.isDisplayed(), "Laptop item is not displayed in cart");
    }

    @Test(priority = 2)
    public void changeItemQuantityInCart() throws IOException {
        new Pages.P03_HomePage(getDriver()).openCartPage().changeProductQuantity("5");
        LogUtils.info("Quantity changed to 5");
        Utilities.generalWait(getDriver())
                .until(ExpectedConditions.visibilityOfElementLocated(P04_CartPage.productQuantityLocator));

        Utilities.generalWait(getDriver()).until(driver ->
                Utilities.byToWebElement(driver, P04_CartPage.productQuantityLocator)
                        .getAttribute("value").equals("5"));

        String actualValue = Utilities.byToWebElement(getDriver(),
                P04_CartPage.productQuantityLocator).getAttribute("value");
        LogUtils.info("Actual quantity value: " + actualValue);
        Assert.assertEquals(actualValue, "5", "Quantity is not updated correctly");
        Utilities.takeScreenshot(getDriver(), "QuantityChangedTo5");
    }

    @Test(priority = 3)
    public void clearCartTC() throws IOException {
        new Pages.P03_HomePage(getDriver()).openCartPage();

        if (Utilities.getElementText(getDriver(), P04_CartPage.emptyCartMessage).equals("Your Shopping Cart is empty!")) {
                LogUtils.info("Cart is already empty, no need to clear it again");
        } else {
            new P04_CartPage(getDriver()).clearCart();
            LogUtils.info("Cart cleared successfully");
            Utilities.generalWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(P04_CartPage.emptyCartMessage));
            String actualMessage = Utilities.getElementText(getDriver(), P04_CartPage.emptyCartMessage);
            LogUtils.info("Actual message: " + actualMessage);
            Assert.assertEquals(actualMessage, "Your Shopping Cart is empty!"
                    , "Cart is not empty after clearing");
            LogUtils.info("Cart is empty after clearing");
            Utilities.takeScreenshot(getDriver(), "CartCleared");
        }
    }

    @Test(priority = 4)
    public void checkProductPriceInCartEqualsToHomePageTC() throws IOException {
        double homePagePrice = new Pages.P03_HomePage(getDriver()).retrieveLaptopPrice();
        new Pages.P03_HomePage(getDriver()).clickAddToCartForLaptop();
        Utilities.closePopUp(getDriver(), P04_CartPage.closeNotificationPopUpLocator);
        Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P03_HomePage.cartLocator));
        new Pages.P03_HomePage(getDriver()).openCartPage();
        double cartPrice = new Pages.P04_CartPage(getDriver()).getProductPrice();
        LogUtils.info("Home page price: " + homePagePrice);
        LogUtils.info("Cart price: " + cartPrice);
        Utilities.takeScreenshot(getDriver(), "ProductPriceInCart");
        Assert.assertEquals(homePagePrice, cartPrice, "Product price in cart does not match home page price");
        new P04_CartPage(getDriver()).clearCart(); // Clear cart after checking price
    }

    @Test(priority = 5)
    public void ContinueShoppingWithoutAcceptingTermsTC() throws IOException {
        new P03_HomePage(getDriver()).clickAddToCartForLaptop();
        Utilities.closePopUp(getDriver(), P04_CartPage.closeNotificationPopUpLocator);
        Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P03_HomePage.cartLocator));
        new P03_HomePage(getDriver())
                .openCartPage()
                .clickCheckoutButtonWithoutAcceptingTerms();
        LogUtils.info("Clicked on Checkout button without accepting terms");
        Assert.assertEquals(Utilities.getElementText(getDriver(), P04_CartPage.termsOfServiceCheckboxLocator),
                "Terms of service", "Terms of service checkbox is not displayed");
        LogUtils.info("Terms of service checkbox is displayed");
        Utilities.closePopUp(getDriver(), P04_CartPage.closePopUpLocator);
        LogUtils.info("Closed the terms of service popup");
        Utilities.takeScreenshot(getDriver(), "TermsOfServiceAccepted");
        new P04_CartPage(getDriver()).clearCart();
    }

    @Test(priority = 6)
    public void checkContinueShoppingButton() throws IOException {
        new P03_HomePage(getDriver()).selectCategory("jewelry").addBraceletToCart()
                .addBracelet();
        LogUtils.info("Bracelet added to cart");
        Utilities.clickOnElement(getDriver(),P04_CartPage.closeNotificationPopUpLocator);
        Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P03_HomePage.cartLocator));
        new P03_HomePage(getDriver())
                .openCartPage().clickContinueShoppingButton()
                 .addNecklaceToCart();
        LogUtils.info("bracelet added to cart");
        LogUtils.info("cart opened");
        LogUtils.info("Necklace added to cart");
        String NumberDisplayedOnCart = Utilities.getElementText(getDriver(), numberOnCartLocator);
        LogUtils.info("the number displayed on cart is: " + NumberDisplayedOnCart);
        Utilities.generalWait(getDriver())
                .until(ExpectedConditions.textToBe(numberOnCartLocator, "(2)"));
        Utilities.takeScreenshot(getDriver(), "ContinueShoppingButtonClicked");
        Assert.assertEquals(Utilities.getElementText(getDriver(), numberOnCartLocator), "(2)");

        for(int i = 0; i < 2; i++) {
            new P04_CartPage(getDriver()).openCartPage();
            Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P04_CartPage.productBox));
            new P04_CartPage(getDriver()).clearCart(); // Clear cart after checking continue shopping
            LogUtils.info("deleted item " + i + " from cart");
        }
    }
    @Test(priority = 7)
    public void checkout() throws IOException {
        new P03_HomePage(getDriver()).clickAddToCartForLaptop();
        Utilities.closePopUp(getDriver(), P04_CartPage.closeNotificationPopUpLocator);
        Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P03_HomePage.cartLocator));
        new P03_HomePage(getDriver())
                .openCartPage()
                .clickOnAcceptTermsCheckBox()
                .clickCheckoutButton();
        LogUtils.info("Clicked on Checkout button after accepting terms");
        Utilities.takeScreenshot(getDriver(), "CheckoutButtonClicked");
        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environments", "checkout_page"));
        LogUtils.info("User is redirected to the checkout page");
        Utilities.clearCart(getDriver());
    }

    @AfterMethod
    public void quitBrowser() {
        quitDriver();
    }
}
