package Tests;

import Pages.P03_HomePage;
import Pages.P04_CartPage;
import Pages.P05_BooksPage;
import Utilities.DataUtil;
import Utilities.LogUtils;
import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestMethodListenerClass;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestMethodListenerClass.class})
public class TC05_BooksTest {


    public static By priceOfProduct = By.xpath("//span[contains(@class,'actual-price')]");
    public static By productsBox = By.className("item-box");
    public static By productsGrid = By.className("product-grid");
    public static By productsList = By.className("product-list");
    public static By notificationBar = By.id("bar-notification");

    @BeforeMethod
    public void browserSetup() throws IOException {
        String browser = System.getProperty("Browser") != null ? System.getProperty("Browser") : DataUtil.getPropertyValue("environments", "Browser");
        LogUtils.info(System.getProperty("Browser"));
        setUpBrowser(browser);
        LogUtils.info("Chrome is now opened");
        getDriver().get(DataUtil.getPropertyValue("environments", "Login_page"));
        LogUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Utilities.login(getDriver());
    }

    @Test(priority = 2)
    public void checkPricesAfterSortingTC() throws InterruptedException, IOException {
        new P03_HomePage(getDriver()).selectCategory("books");
        new P05_BooksPage(getDriver()).selectSortBy("Price: High to Low");
        List<WebElement> priceElementsAfterSelect = getDriver().findElements(priceOfProduct);
        //Extracting actual prices of elements
        List<Double> actualPrices = new ArrayList<>();
        for(WebElement priceElement : priceElementsAfterSelect) {
            String priceText = priceElement.getText();
            actualPrices.add(Double.parseDouble(priceText));
        }
        //make a copy of actual prices in another list and sort the copy then comparing them
        List<Double> expectedPrices= new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder());
        LogUtils.info("Actual Prices after sorting: " + actualPrices);
        LogUtils.info("Expected Prices after sorting: " + expectedPrices);
        Utilities.takeScreenshot(getDriver(), "PricesAfterSorting");
        Assert.assertEquals(expectedPrices,actualPrices);
    }

    @Test(priority = 3)
    public void checkProductsCountAfterSortingTC() throws IOException {
        new P03_HomePage(getDriver()).selectCategory("books");
        new P05_BooksPage(getDriver()).selectDisplayPerPage("4");
        List<WebElement> displayedProducts = getDriver().findElements(productsBox);
        LogUtils.info("Number of products displayed: " + displayedProducts.size());
        Utilities.takeScreenshot(getDriver(), "ProductsCountAfterSorting");
        Assert.assertEquals(displayedProducts.size(), 4);
    }

    @Test(priority = 4)
    public void viewAsFeatureTC() throws IOException {
        WebElement grid = Utilities.byToWebElement(getDriver(),productsGrid);
        new P03_HomePage(getDriver()).selectCategory("books");
        new P05_BooksPage(getDriver()).selectViewAs("List");
        boolean gridNotVisible = Utilities.generalWait(getDriver()).until(ExpectedConditions.invisibilityOf(grid));
        LogUtils.info("Grid view is not visible: " + gridNotVisible);
        Utilities.takeScreenshot(getDriver(), "ViewAsFeatureGridNotVisible");
        Assert.assertTrue(gridNotVisible);

        WebElement list = Utilities.byToWebElement(getDriver(),productsList);
        new P05_BooksPage(getDriver()).selectViewAs("Grid");
        boolean listNotVisible = new WebDriverWait(getDriver(),Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOf(list));
        LogUtils.info("List view is not visible: " + listNotVisible);
        Utilities.takeScreenshot(getDriver(), "ViewAsFeatureListNotVisible");
        Assert.assertTrue(listNotVisible);
    }

    @Test(priority = 1)
    public void addProductToCartTC() throws IOException {
        new P03_HomePage(getDriver()).selectCategory("books");
        new P05_BooksPage(getDriver()).clickAddToCartButton().getNumberOnCartIcon();
        LogUtils.info("Number on cart icon before adding items is: " + new P05_BooksPage(getDriver()).getNumberOnCartIcon());
        Utilities.generalWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(notificationBar));
        String notificationText = Utilities.getElementText(getDriver(),notificationBar);
        LogUtils.info("Notification text: " + notificationText);
        LogUtils.info("Number on cart icon after adding items is: "
                + new P05_BooksPage(getDriver()).getNumberOnCartIcon());
        Utilities.takeScreenshot(getDriver(), "ProductAddedToCart");
        Assert.assertTrue(notificationText.contains("The product has been added to your shopping cart"));

        // clear cart
        getDriver().get(DataUtil.getPropertyValue("environments", "Cart_page"));
        new P04_CartPage(getDriver()).clearCart();
    }

    @AfterMethod
    public void quitBrowser() {
        quitDriver();
    }
}
