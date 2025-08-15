package Utilities;

import Pages.P02_LoginPage;
import Pages.P04_CartPage;
import Pages.P06_CheckoutPage;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import static DriverFactory.DriverFactory.getDriver;

public class Utilities {

    private static final String screenshotPath = "src/test/Test-outputs/ScreenShots/";

    public static void clickOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public static void enterText(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }

    public static String getElementText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    public static String getTimeStamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
    }

    public static void takeScreenshot(WebDriver driver, String ScreenshotName) throws IOException {

        try {
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File ScreenshotDest = new File(screenshotPath + ScreenshotName + "-" + getTimeStamp() + ".png");
        FileUtils.copyFile(srcFile, ScreenshotDest);
        Allure.addAttachment(ScreenshotName, Files.newInputStream(ScreenshotDest.toPath()));
            }
        catch (Exception e) {
        LogUtils.error(e.getMessage());
        }
    }

    public static void scrollToElement(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(locator));
    }

    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static WebElement byToWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static void selectingFromDropDown(WebDriver driver, By locator, String option) {
        new Select(byToWebElement(driver, locator)).selectByVisibleText(option);
    }

    public static void closePopUp(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
        LogUtils.info("Pop-up closed successfully.");
    }

    public static File getLastFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public static void clearCart(WebDriver driver) throws IOException {
        getDriver().get(DataUtil.getPropertyValue("environments", "Cart_page"));
        new P04_CartPage(getDriver()).clearCart();
    }

    public static void login(WebDriver driver) throws FileNotFoundException {
        new P02_LoginPage(getDriver())
                .enterEmail(DataUtil.getJsonData("LoginData","email"))
                .enterPassword(DataUtil.getJsonData("LoginData","password")).clickLogin();
        LogUtils.info("Browser setup completed and user logged in successfully.");
    }

    public static P06_CheckoutPage fillingDataWithSelectNewAddress(WebDriver driver) throws FileNotFoundException {
        new Pages.P06_CheckoutPage(getDriver())
                .selectNewAddressOption()
                .enterCompany(DataUtil.getJsonData("CustomerData", "company"))
                .selectCountry(DataUtil.getJsonData("CustomerData", "country"))
                .enterCity(DataUtil.getJsonData("CustomerData", "city"))
                .enterAddress1(DataUtil.getJsonData("CustomerData", "address1"))
                .enterAddress2(DataUtil.getJsonData("CustomerData", "address2"))
                .enterZipCode(DataUtil.getJsonData("CustomerData", "zipCode"))
                .enterPhoneNumber(DataUtil.getJsonData("CustomerData", "phoneNumber"))
                .enterFaxNumber(DataUtil.getJsonData("CustomerData", "faxNumber"))
                .clickContinueButton();
        return new P06_CheckoutPage(driver);
    }

    public static void fillingDataWithoutSelectNewAddress(WebDriver driver) throws FileNotFoundException {
        new Pages.P06_CheckoutPage(getDriver())
                .enterCompany(DataUtil.getJsonData("CustomerData", "company"))
                .selectCountry(DataUtil.getJsonData("CustomerData", "country"))
                .enterCity(DataUtil.getJsonData("CustomerData", "city"))
                .enterAddress1(DataUtil.getJsonData("CustomerData", "address1"))
                .enterAddress2(DataUtil.getJsonData("CustomerData", "address2"))
                .enterZipCode(DataUtil.getJsonData("CustomerData", "zipCode"))
                .enterPhoneNumber(DataUtil.getJsonData("CustomerData", "phoneNumber"))
                .enterFaxNumber(DataUtil.getJsonData("CustomerData", "faxNumber"))
                .clickContinueButton();
    }

}
