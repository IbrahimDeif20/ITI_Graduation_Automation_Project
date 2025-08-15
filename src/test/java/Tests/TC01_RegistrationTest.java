package Tests;

import Pages.P01_RegistrationPage;
import Utilities.DataUtil;
import Utilities.LogUtils;
import Utilities.Utilities;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestMethodListenerClass;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvokedMethodListenerClass.class, ITestMethodListenerClass.class})
public class TC01_RegistrationTest {

    private static String email = new Faker().internet().emailAddress();
    By continueButtonLocator = By.xpath("//input[@value='Continue']");

    @BeforeMethod
    public void browserSetup() throws IOException {

        String browser = System.getProperty("Browser") != null ? System.getProperty("Browser") : DataUtil.getPropertyValue("environments", "Browser");
        LogUtils.info(System.getProperty("Browser"));
        setUpBrowser(browser);
        LogUtils.info("Chrome is now opened");
        getDriver().get(DataUtil.getPropertyValue("environments", "Register_page"));
        LogUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(dataProvider = "RegisterDataProvider", dataProviderClass = DataUtil.class)
    public void registrationWithValidDataTC(String firstName,String lastName,String password,String confirmedPassword) throws IOException {
        new P01_RegistrationPage(getDriver())
                .chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPassword)
                .clickRegisterButton();
        LogUtils.info("Registration form filled with valid data");

        Utilities.generalWait(getDriver())
                .until(ExpectedConditions.visibilityOfElementLocated(continueButtonLocator));
        Utilities.takeScreenshot(getDriver(), "RegistrationSuccess");
        Assert.assertEquals(getDriver().getCurrentUrl(),DataUtil.getPropertyValue("environments", "Register_confirmation_page"));
        LogUtils.info("Registration successful, redirected to confirmation page");
    }

    @Test (dataProvider = "RegisterDataProvider", dataProviderClass = DataUtil.class)
    public void invalidPasswordLength(String firstName,String lastName,String password,String confirmedPassword) throws IOException {
        new P01_RegistrationPage(getDriver()).chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPassword)
                .clickRegisterButton();
        LogUtils.info("Registration form filled with invalid password length");

        String errorMessage = getDriver().findElement(P01_RegistrationPage.passwordErrorMsgLocator).getText();
        LogUtils.info("Error message displayed: " + errorMessage);
        Utilities.takeScreenshot(getDriver(), "InvalidPasswordLengthError");
        Assert.assertEquals(errorMessage,"The password should have at least 6 characters.");
        Assert.assertNotEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Register_confirmation_page"));
        LogUtils.info("User not redirected to confirmation page due to invalid password length");
    }

    @Test (dataProvider = "RegisterDataProvider", dataProviderClass = DataUtil.class)
    public void confirmedPasswordDoesNotMatch(String firstName,String lastName,String password,String confirmedPassword) throws IOException {
        new P01_RegistrationPage(getDriver()).chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPassword)
                .clickRegisterButton();
        LogUtils.info("Registration form filled with mismatched passwords");
        Utilities.takeScreenshot(getDriver(), "PasswordMismatchError");
        String errorMessage = getDriver().findElement(P01_RegistrationPage.passwordDoesNotMatchErrorMsgLocator).getText();
        Assert.assertEquals(errorMessage,"The password and confirmation password do not match.");
        Assert.assertNotEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Register_confirmation_page"));
        LogUtils.info("Error message displayed: " + errorMessage);
    }

    @Test (dataProvider = "RegisterDataProvider", dataProviderClass = DataUtil.class)
    public void leaveFirstNameFieldEmpty(String firstName,String lastName,String password,String confirmedPassword) throws IOException {
        new P01_RegistrationPage(getDriver()).chooseGender("f")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPassword)
                .clickRegisterButton();
        LogUtils.info("Registration form submitted with empty first name field");

        String errorMessage = getDriver().findElement(P01_RegistrationPage.firstNameAsBlankErrorMsgLocator).getText();
        Utilities.takeScreenshot(getDriver(), "EmptyFirstNameError");
        Assert.assertEquals(errorMessage,"First name is required.");
        Assert.assertNotEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Register_confirmation_page"));
        LogUtils.info("Error message displayed: " + errorMessage);
    }

    @Test (dataProvider = "RegisterDataProvider", dataProviderClass = DataUtil.class)
    public void wrongFormattedEmail(String firstName,String lastName,String password,String confirmedPassword) throws IOException {
        new P01_RegistrationPage(getDriver()).chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail("user@@yahoo.com")
                .enterPassword(password)
                .enterConfirmPassword(confirmedPassword)
                .clickRegisterButton();
        LogUtils.info("Registration form filled with wrong formatted email");

        String errorMessage = getDriver().findElement(P01_RegistrationPage.emailMsgLocator).getText();
        Utilities.takeScreenshot(getDriver(), "WrongEmailFormatError");
        Assert.assertEquals(errorMessage,"Wrong email");
        Assert.assertNotEquals(getDriver().getCurrentUrl()
                ,DataUtil.getPropertyValue("environments", "Register_confirmation_page"));
        LogUtils.info("Error message displayed: " + errorMessage);
    }

    @AfterMethod
    public void quitBrowser() {
        quitDriver();
        LogUtils.info("Browser closed successfully.");
    }

}
