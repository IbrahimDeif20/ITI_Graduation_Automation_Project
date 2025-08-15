package Pages;

import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Utilities.LogUtils;

import java.util.List;

public class P03_HomePage {
    WebDriver driver;

   private final By booksCategoryLocator = By.xpath("(//a[@href='/books'])[1]");
   private final By computersCategoryLocator = By.xpath("(//a[@href='/computers'])[1]");
   private final By electronicsCategoryLocator = By.xpath("(//a[@href='/electronics'])[1]");
   private final By apparelCategoryLocator = By.xpath("(//a[@href='/apparel-shoes'])[1]");
   private final By digitalCategoryLocator = By.xpath("(//a[@href='/digital-downloads'])[1]");
   private final By jewelryCategoryLocator = By.xpath("(//a[@href='/jewelry'])[1]");
   private final By giftCardCategoryLocator = By.xpath("(//a[@href='/gift-cards'])[1]");
   private final By searchBoxLocator = By.name("q");
   private final By searchButtonLocator = By.xpath("//input[@value='Search']");
   private final By logoutButtonLocator = By.className("ico-logout");
   private final By viewAccountDetailsLocator = By.xpath("(//a[@href='/customer/info'])[1]");
   private final By laptopAddToCartLocator = By.xpath("(//input[@value='Add to cart'])[2]");
   private final By laptopPriceLocator = By.xpath("(//span[contains(@class,'actual-price')])[2]");
   public static By footerLocator = By.className("footer-menu-wrapper");
   private final By facebookIconLocator = By.linkText("Facebook");
   private final By twitterIconLocator = By.linkText("Twitter");
   private final By numberOfDisplayedProductsLocator = By.cssSelector("input[value='Add to cart']");
   public static By cartLocator = By.xpath("(//span[@class='cart-label'])[1]");
   private final By notificationPopUpLocator = By.xpath("//span[@title='Close']");


    public P03_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CartPage selectCategory(String category) {
        switch (category) {
            case "books" :
                driver.findElement(booksCategoryLocator).click();
                break;
            case "computers" :
                driver.findElement(computersCategoryLocator).click();
                break;
            case "electronics" :
                driver.findElement(electronicsCategoryLocator).click();
                break;
            case "apparel-shoes" :
                driver.findElement(apparelCategoryLocator).click();
                break;
            case "digital-downloads" :
                driver.findElement(digitalCategoryLocator).click();
                break;
            case "jewelry" :
                driver.findElement(jewelryCategoryLocator).click();
                break;
            case "gift-cards" :
                driver.findElement(giftCardCategoryLocator).click();
                break;
        }
        return new P04_CartPage(driver);
    }

    public void logout(){
        Utilities.clickOnElement(driver, logoutButtonLocator);
    }

    public void checkSearchBar(String text) {
        Utilities.enterText(driver, searchBoxLocator, text);
        Utilities.clickOnElement(driver, searchButtonLocator);
    }

    public void clickViewAccountDetails() {
        Utilities.clickOnElement(driver, viewAccountDetailsLocator);
    }

    public void clickAddToCartForLaptop() {
        Utilities.clickOnElement(driver, laptopAddToCartLocator);
    }

    public P04_CartPage openCartPage() {
        Utilities.clickOnElement(driver, cartLocator);
        return new P04_CartPage(driver);
    }

    public P03_HomePage removeNotificationPopUp() {
        Utilities.clickOnElement(driver, notificationPopUpLocator);
        return this;
    }

    public int validateNumberOfDisplayedProducts(){
        List<WebElement> items = driver.findElements(numberOfDisplayedProductsLocator);
        return items.size();
    }

    public int validateFooterSize(){
        WebElement footer = driver.findElement(footerLocator);
        List<WebElement> footerLinks = footer.findElements(By.tagName("a")); //22
        return footerLinks.size();
    }

    public void checkFaceBookRedirection(){
        String originalWindow = driver.getWindowHandle();
        Utilities.clickOnElement(driver, facebookIconLocator);
        LogUtils.info("The current tab URL is: " + driver.getCurrentUrl());
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        LogUtils.info("The new tab URL is: " + driver.getCurrentUrl());
    }

    public void checkTwitterRedirection(){
        String originalWindow = driver.getWindowHandle();
        Utilities.clickOnElement(driver, twitterIconLocator);
        LogUtils.info("The current tab URL is: " + driver.getCurrentUrl());
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        LogUtils.info("The new tab URL is: " + driver.getCurrentUrl());
    }

    public double retrieveLaptopPrice() {
        return Double.parseDouble(Utilities.getElementText(driver, laptopPriceLocator));
    }

}
