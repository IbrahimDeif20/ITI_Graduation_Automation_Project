package Pages;

import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_CheckoutPage {
    WebDriver driver;

    private final By companyLocator = By.id("BillingNewAddress_Company");
    private final By cityLocator = By.id("BillingNewAddress_City");
    private final By address1Locator = By.id("BillingNewAddress_Address1");
    private final By address2Locator = By.id("BillingNewAddress_Address2");
    private final By postalCodeLocator = By.id("BillingNewAddress_ZipPostalCode");
    private final By phoneNumberLocator = By.id("BillingNewAddress_PhoneNumber");
    private final By faxNumberLocator = By.id("BillingNewAddress_FaxNumber");
    private final By countryLocator = By.xpath("//select[@id='BillingNewAddress_CountryId']");
    private final By addressSelectDropdown = By.cssSelector("select[id='billing-address-select']");
    public static By continueButton = By.xpath("(//input[@title='Continue'])[1]");
    public static By storePickup = By.id("PickUpInStore");
    public static By errorMsgLocator = By.className("field-validation-error");
    private final By continueButtonLocator2 = By.xpath("(//input[@title='Continue'])[2]");
    private final By continueButtonLocator3 = By.xpath("(//input[@value='Continue'])[3]");
    private final By continueButtonLocator4 = By.xpath("(//input[@value='Continue'])[4]");
    private final By continueButtonLocator5 = By.xpath("(//input[@value='Continue'])[5]");
    private final By continueButtonLocator6 = By.xpath("//input[@value='Confirm']");
    public static By orderConfirmedMsgLocator = By.xpath("//input[@value='Continue']");
    private final By nextDayAirLocator = By.id("shippingoption_1");
    private final By backButtonLocator = By.xpath("(//p[@class='back-link'])[1] //a");
    private final By orderDetailsLocator = By.linkText("Click here for order details.");
    private final By orderNumberLocator = By.className("order-number");
    public static By orderInformationTitle = By.tagName("h1");
    private final By reOrderButtonLocator = By.xpath("//input[@value='Re-order']");

    public P06_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P06_CheckoutPage selectCountry(String countryName) {
        Utilities.selectingFromDropDown(driver,countryLocator, countryName);
        return this;
    }

    public P06_CheckoutPage enterCompany(String companyName) {
        Utilities.enterText(driver, companyLocator, companyName);
        return this;
    }

    public P06_CheckoutPage enterCity(String cityName) {
        Utilities.enterText(driver, cityLocator, cityName);
        return this;
    }

    public P06_CheckoutPage enterAddress1(String address1Text) {
        Utilities.enterText(driver, address1Locator, address1Text);
        return this;
    }

    public P06_CheckoutPage enterAddress2(String address2Text) {
        Utilities.enterText(driver, address2Locator, address2Text);
        return this;
    }

    public P06_CheckoutPage enterZipCode(String postalCodeText) {
        Utilities.enterText(driver, postalCodeLocator, postalCodeText);
        return this;
    }

    public P06_CheckoutPage enterPhoneNumber(String phoneNumberText) {
        Utilities.enterText(driver, phoneNumberLocator, phoneNumberText);
        return this;
    }

    public P06_CheckoutPage enterFaxNumber(String faxNumberText) {
        Utilities.enterText(driver, faxNumberLocator, faxNumberText);
        return this;
    }

    public P06_CheckoutPage selectNewAddressOption() {
        Utilities.selectingFromDropDown(driver, addressSelectDropdown, "New Address");
        return this;
    }

    public void clickContinueButton() {
        Utilities.clickOnElement(driver, continueButton);
    }

    public P06_CheckoutPage clickContinueButton2() {
        Utilities.clickOnElement(driver, continueButtonLocator2);
        return this;
    }

    public P06_CheckoutPage clickContinueButton3() {
        Utilities.clickOnElement(driver, continueButtonLocator3);
        return this;
    }
    public P06_CheckoutPage clickContinueButton4() {
        Utilities.clickOnElement(driver, continueButtonLocator4);
        return this;
    }
    public P06_CheckoutPage clickContinueButton5() {
        Utilities.clickOnElement(driver, continueButtonLocator5);
        return this;
    }

    public P06_CheckoutPage clickContinueButton6() {
        Utilities.clickOnElement(driver, continueButtonLocator6);
        return this;
    }

    public P06_CheckoutPage selectNextDayAir() {
        Utilities.clickOnElement(driver, nextDayAirLocator);
        return this;
    }

    public void clickBackButton(){
        Utilities.clickOnElement(driver, backButtonLocator);
    }
    public P06_CheckoutPage clickOrderDetailsLink() {
        Utilities.clickOnElement(driver, orderDetailsLocator);
        return this;
    }

    public String getOrderNumber() {
        String text = Utilities.getElementText(driver, orderNumberLocator);
        return text.replaceAll("Order #", "");
    }

    public void clickReOrderButton() {
        Utilities.clickOnElement(driver, reOrderButtonLocator);
    }

}
