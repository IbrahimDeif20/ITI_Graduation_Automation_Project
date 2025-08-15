package Pages;

import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P02_LoginPage {

    WebDriver driver;

    private final By emailLocator = By.id("Email");
    private final By passwordLocator = By.id("Password");
    private final By clickLoginButtonLocator = By.xpath("//input[@value='Log in']");

    public P02_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P02_LoginPage enterEmail(String email) {
        Utilities.enterText(driver, emailLocator, email);
        return this;
    }

    public P02_LoginPage enterPassword(String password) {
        Utilities.enterText(driver, passwordLocator, password);
        return this;
    }

    public void clickLogin() {
        Utilities.clickOnElement(driver, clickLoginButtonLocator);
    }
}
