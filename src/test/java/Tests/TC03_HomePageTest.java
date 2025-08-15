package Tests;

import Pages.P03_HomePage;
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
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestMethodListenerClass;
import Utilities.LogUtils;
import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestMethodListenerClass.class})
public class TC03_HomePageTest {

    By laptopLocator = By.xpath("//div[@class='product-item']//img[@title='Show details for 14.1-inch Laptop']");
    By numberOnCartLocator = By.cssSelector("span[class = 'cart-qty']");
    private final By productsBox = By.className("item-box");
    private final By searchKeyWord = By.tagName("h1");

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

    @Test
    public void navigateToAccountDetails() throws IOException {
        new P03_HomePage(getDriver()).clickViewAccountDetails();
        Assert.assertEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Customer_info_page"));
        LogUtils.info("Navigated to account details page successfully.");
        Utilities.takeScreenshot(getDriver(), "AccountDetailsPage");
    }

    @Test
    public void checkLogoutButton() throws IOException {
        new P03_HomePage(getDriver()).logout();
        Assert.assertEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Home_page"));
        LogUtils.info("Logout successful, redirected to home page.");
        Utilities.takeScreenshot(getDriver(), "LogoutSuccess");
    }

    @Test
    public void chooseCategoryTC() throws IOException {
        new P03_HomePage(getDriver()).selectCategory("books");
        Assert.assertEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Home_page")+ "books");
        LogUtils.info("Selected category 'books' and verified URL.");
        Utilities.takeScreenshot(getDriver(), "BooksCategoryPage");
    }

    @Test
    public void checkSearchBar() throws IOException {
        new P03_HomePage(getDriver()).checkSearchBar("laptop");
        WebElement item = Utilities.generalWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(laptopLocator));
        LogUtils.info("Laptop item is displayed in search results");
        Assert.assertTrue(item.isDisplayed(), "Laptop item is not displayed in search results");
        Utilities.takeScreenshot(getDriver(), "LaptopSearchResults");
    }

    @Test
    public void searchUsingPartOfExistingTextTC() throws IOException {
        int counter=0;
        new P03_HomePage(getDriver()).checkSearchBar("blu");
        Utilities.generalWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(searchKeyWord));

        List<WebElement> displayedProducts = getDriver().findElements(productsBox);
        List<String> extractedText = new ArrayList<>();
        for (WebElement element : displayedProducts) {
            String extractedTextFromCurrentProduct = element.getText();
            LogUtils.info("Extracted text from product: " + extractedTextFromCurrentProduct);
            extractedText.add(extractedTextFromCurrentProduct);
        }

        for (String text : extractedText) {
            if(text.toLowerCase().contains("blu".toLowerCase()))
                counter = counter + 1;
        }
        LogUtils.info("Number of products displayed: " + displayedProducts.size());
        Assert.assertEquals(displayedProducts.size(),counter);
        Utilities.takeScreenshot(getDriver(), "SearchResultsForPartOfText");
    }

    @Test
    public void addToCartFromHomePage() throws IOException {
        String oldNumberDisplayedOnCart = Utilities.getElementText(getDriver(), numberOnCartLocator);
        LogUtils.info("the number displayed on cart before adding laptop is: " + oldNumberDisplayedOnCart);
        new P03_HomePage(getDriver()).clickAddToCartForLaptop();
        Utilities.generalWait(getDriver()).until(ExpectedConditions.not(ExpectedConditions.textToBe(numberOnCartLocator,oldNumberDisplayedOnCart)));
        LogUtils.info("the number displayed on cart after adding laptop is: " + Utilities.getElementText(getDriver(), numberOnCartLocator));
        Assert.assertEquals(Utilities.getElementText(getDriver(), numberOnCartLocator),"(1)");
        LogUtils.info("Laptop added to cart successfully.");
        Utilities.takeScreenshot(getDriver(), "LaptopAddedToCart");
        new P03_HomePage(getDriver()).removeNotificationPopUp().openCartPage().clearCart();
    }

    @Test()
    public void checkNumbersOfDisplayedItems() throws IOException {
        new P03_HomePage(getDriver()).validateNumberOfDisplayedProducts();
        int expectedNumberOfItems = 6;
        LogUtils.info("Expected number of items is " + expectedNumberOfItems + " and the actual is " +
                      new P03_HomePage(getDriver()).validateNumberOfDisplayedProducts());
        Assert.assertEquals(new P03_HomePage(getDriver()).validateNumberOfDisplayedProducts(), expectedNumberOfItems);
        Utilities.takeScreenshot(getDriver(), "NumberOfDisplayedItems");
    }

    @Test
    public void checkFooterSize() throws IOException {
        Utilities.scrollToElement(getDriver(),P03_HomePage.footerLocator);
        new P03_HomePage(getDriver()).validateFooterSize();
        LogUtils.info("Expected footer size is 22 and the actual is " + new P03_HomePage(getDriver()).validateFooterSize());
        Assert.assertEquals(new P03_HomePage(getDriver()).validateFooterSize(),22);
        Utilities.takeScreenshot(getDriver(), "FooterSize");
    }

    @Test
    public void checkRedirectionToFceBook() throws IOException {
        new P03_HomePage(getDriver()).checkFaceBookRedirection();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.facebook.com/nopCommerce");
        LogUtils.info("Redirection to Facebook successful.");
        Utilities.takeScreenshot(getDriver(), "FacebookRedirection");
    }

    @Test
    public void checkRedirectionToTwitter() throws IOException {
        new P03_HomePage(getDriver()).checkTwitterRedirection();
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://x.com/nopCommerce");
        LogUtils.info("Redirection to Twitter successful.");
        Utilities.takeScreenshot(getDriver(), "TwitterRedirection");
    }

    @AfterMethod
    public void quitBrowser() {
        quitDriver();
    }

}
