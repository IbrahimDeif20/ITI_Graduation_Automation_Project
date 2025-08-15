package Pages;

import Utilities.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_RegistrationPage {

    WebDriver driver;

    private final By maleLocator = By.id("gender-male");
    private final By femaleLocator = By.id("gender-female");
    private final By firstNameLocator = By.id("FirstName");
    private final By lastNameLocator = By.id("LastName");
    private final By emailLocator = By.id("Email");
    private final By passwordLocator = By.id("Password");
    private final By confirmPasswordLocator = By.id("ConfirmPassword");
    private final By registerButtonLocator = By.id("register-button");
    public static By passwordErrorMsgLocator = By.cssSelector("span.field-validation-error");
    public static By passwordDoesNotMatchErrorMsgLocator = By.cssSelector(".field-validation-error");
    public static By firstNameAsBlankErrorMsgLocator = By.className("field-validation-error");
    public static By emailMsgLocator = By.className("field-validation-error");



    public P01_RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_RegistrationPage chooseGender(String gender){
        if(gender.equals("m"))
             Utilities.clickOnElement(driver, maleLocator);
        else
             Utilities.clickOnElement(driver, femaleLocator);
        return this;
    }

    public P01_RegistrationPage enterFirstName(String firstName){
        Utilities.enterText(driver, firstNameLocator, firstName);
        return this;
    }

    public P01_RegistrationPage enterLastName(String lastName){
        Utilities.enterText(driver, lastNameLocator, lastName);
        return this;
    }

    public P01_RegistrationPage enterEmail(String email){
        Utilities.enterText(driver, emailLocator, email);
        return this;
    }

    public P01_RegistrationPage enterPassword(String password){
        Utilities.enterText(driver, passwordLocator, password);
        return this;
    }

    public P01_RegistrationPage enterConfirmPassword(String password){
        Utilities.enterText(driver, confirmPasswordLocator, password);
        return this;
    }

    public void clickRegisterButton(){
        Utilities.clickOnElement(driver, registerButtonLocator);
    }
}
