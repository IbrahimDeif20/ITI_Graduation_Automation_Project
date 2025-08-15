package Tests;

import Pages.P06_CheckoutPage;
import Utilities.DataUtil;
import Utilities.Utilities;
import org.openqa.selenium.NoSuchElementException;
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
public class TC06_CheckoutTest {

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
        new Pages.P03_HomePage(getDriver())
                .selectCategory("jewelry")
                .addNecklaceToCart()
                .openCartPage()
                .clickOnAcceptTermsCheckBox()
                .clickCheckoutButton();
        LogUtils.info("Browser setup completed and user logged in successfully");
        LogUtils.info("Navigated to Jewelry category and added Necklace to cart");
    }

    @Test(priority = 1)
    public void fillingDataTest() throws IOException {
        try {
            Utilities.fillingDataWithSelectNewAddress(getDriver());
            LogUtils.info("Filled in shipping address details");
            LogUtils.info("Continuing to next step in checkout process");
        }
        catch (NoSuchElementException e){
            Utilities.fillingDataWithoutSelectNewAddress(getDriver());
            LogUtils.info("Filled in shipping address details");
            LogUtils.info("Continuing to next step in checkout process");
        }
        Boolean shippingAddressDisplayed = Utilities.byToWebElement(getDriver(), P06_CheckoutPage.storePickup)
                .isDisplayed();
        Assert.assertTrue(shippingAddressDisplayed, "Back button is not displayed after checkout");
        LogUtils.info("Back button is displayed after checkout");
        Utilities.takeScreenshot(getDriver(), "ShippingAddressDetailsScreenshot");
        // Clear cart after checking out
        Utilities.clearCart(getDriver());
    }

    @Test(priority = 2)
    public void clickContinueButtonWithoutFillingData() throws IOException {
        new Pages.P06_CheckoutPage(getDriver()).selectNewAddressOption()
                .clickContinueButton();
        LogUtils.info("Clicked continue button without filling data");
        Boolean errorMsgDisplayed = Utilities.byToWebElement(getDriver(), P06_CheckoutPage.errorMsgLocator)
                .isDisplayed();
        LogUtils.info("Error message displayed: " + errorMsgDisplayed);
        Assert.assertTrue(errorMsgDisplayed);
        Utilities.takeScreenshot(getDriver(), "ErrorMessageCheckoutPageScreenshot");
        // Clear cart after checking out
        Utilities.clearCart(getDriver());
    }

    @Test(priority = 3)
    public void checkoutTest() throws IOException {

        Utilities.fillingDataWithSelectNewAddress(getDriver())
                .clickContinueButton2()
                .selectNextDayAir()
                .clickContinueButton3()
                .clickContinueButton4()
                .clickContinueButton5()
                .clickContinueButton6();
        LogUtils.info("Filled in shipping address details");
        LogUtils.info("Completed all steps in checkout process");
        LogUtils.info("Order confirmation message should be displayed now");

            Utilities.generalWait(getDriver()).until(ExpectedConditions.elementToBeClickable(P06_CheckoutPage.orderConfirmedMsgLocator));
        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environments",
                "checkOut_Complete_page"));
        LogUtils.info("Checkout completed successfully, user is on the order confirmation page");
        Utilities.takeScreenshot(getDriver(), "Checkout complete page screenshot");
    }

    @Test(priority = 4)
    public void checkBackButtonWhileCheckingOutTest() throws IOException {
        Utilities.fillingDataWithSelectNewAddress(getDriver())
                .clickBackButton();
        LogUtils.info("Filled in shipping address details");
        LogUtils.info("Clicked back button while checking out");
        Boolean buttonIsDisplayed = Utilities.byToWebElement(getDriver(),P06_CheckoutPage.continueButton).isDisplayed();
        LogUtils.info("Continue button is displayed after clicking back: " + buttonIsDisplayed);
        Assert.assertTrue(buttonIsDisplayed);
        Utilities.takeScreenshot(getDriver(), "BackButtonCheckoutPageScreenshot");
    }

    @Test(priority = 5)
    public void viewOrderDetailsTC() throws IOException {
        Utilities.fillingDataWithSelectNewAddress(getDriver())
                .clickContinueButton2()
                .selectNextDayAir()
                .clickContinueButton3()
                .clickContinueButton4()
                .clickContinueButton5()
                .clickContinueButton6()
                .clickOrderDetailsLink();
        LogUtils.info("Filled in shipping address details");
        LogUtils.info("Clicked on order details link after completing checkout");

        String orderNumber = new P06_CheckoutPage(getDriver()).getOrderNumber();
        LogUtils.info("Order number retrieved: " + orderNumber);

        boolean isDisplayed = Utilities.byToWebElement(getDriver(), P06_CheckoutPage.orderInformationTitle)
                .isDisplayed();
        LogUtils.info("Order information title is displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed, "Order information title is not visible");

        Assert.assertEquals(getDriver().getCurrentUrl(),
                DataUtil.getPropertyValue("environments", "Order_details_page")+ orderNumber);
        LogUtils.info("User is redirected to order details page successfully");
        Utilities.takeScreenshot(getDriver(), "Order details page screenshot");
    }

    @Test(priority = 6)
    public void hitReOrder() throws IOException {
        Utilities.fillingDataWithSelectNewAddress(getDriver())
                .clickContinueButton2()
                .selectNextDayAir()
                .clickContinueButton3()
                .clickContinueButton4()
                .clickContinueButton5()
                .clickContinueButton6()
                .clickOrderDetailsLink()
                .clickReOrderButton();
        LogUtils.info("Filled in shipping address details");
        LogUtils.info("Clicked on Re-order button after viewing order details");

        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtil.getPropertyValue("environments", "Cart_page"));
        LogUtils.info("Re-order button clicked successfully, user is redirected to cart page");
        Utilities.takeScreenshot(getDriver(), "Re-order page screenshot");
        // Clear cart after checking out
        Utilities.clearCart(getDriver());
    }

    @AfterMethod
    public void quitBrowser(){
        quitDriver();
    }
}
