package Tests;

import Pages.P02_LoginPage;
import Utilities.DataUtil;
import Utilities.LogUtils;
import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import Listeners.IInvoked;
import Listeners.ITest;

import static DriverFactory.DriverFactory.*;

@Listeners({IInvoked.class, ITest.class})
public class TC02_LoginTest {
    WebDriver driver;

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
    }

    @Test
    public void loginWithValidCredentials() throws IOException {
        new P02_LoginPage(getDriver())
                .enterEmail(DataUtil.getJsonData("LoginData","email"))
                .enterPassword(DataUtil.getJsonData("LoginData","password")).clickLogin();
        LogUtils.info("Email entered successfully");
        LogUtils.info("Password entered successfully");
        Assert.assertEquals(getDriver().getCurrentUrl(),DataUtil.getPropertyValue("environments", "Home_page"));
        LogUtils.info("Login successful, redirected to home page");
        Utilities.takeScreenshot(getDriver(), "LoginSuccess");
    }

    @Test
    public void loginWithInvalidEmail() throws IOException {
        new P02_LoginPage(getDriver())
                .enterEmail(DataUtil.getJsonData("LoginData", "invalidEmail"))
                .enterPassword(DataUtil.getJsonData("LoginData", "password"))
                .clickLogin();
        LogUtils.info("Invalid email entered");
        LogUtils.info("Password entered successfully");
        Assert.assertNotEquals(getDriver().getCurrentUrl(),DataUtil.getPropertyValue("environments", "Home_page"));
        LogUtils.info("Login failed with invalid email, user not redirected to home page");
        Utilities.takeScreenshot(getDriver(), "LoginFailureInvalidEmail");
    }

    @Test
    public void loginWithInvalidPassword() throws IOException {
        new P02_LoginPage(getDriver())
                .enterEmail(DataUtil.getJsonData("LoginData", "email"))
                .enterPassword(DataUtil.getJsonData("LoginData", "invalidPassword"))
                .clickLogin();
        LogUtils.info("Email entered successfully");
        LogUtils.info("Invalid password entered");
        Assert.assertNotEquals(getDriver().getCurrentUrl(),DataUtil.getPropertyValue("environments", "Home_page"));
        LogUtils.info("Login failed with invalid password, user not redirected to home page");
        Utilities.takeScreenshot(getDriver(), "LoginFailureInvalidPassword");
    }

    @AfterMethod
    public void quitBrowser() {
        quitDriver();
    }

}
