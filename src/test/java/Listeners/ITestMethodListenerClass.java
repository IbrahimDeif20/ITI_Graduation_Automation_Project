package Listeners;

import Utilities.LogUtils;
import Utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class ITestMethodListenerClass implements ITestListener {
    WebDriver driver;

    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test " + result.getName() + " passed successfully.");
    }

    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test " + result.getName() + " failed with exception: " + result.getThrowable());
        try {
            Utilities.takeScreenshot(driver, result.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test " + result.getName() + " was skipped.");
    }


    public void onStart(ITestContext context) {
        //LogUtils.info("Starting test suite: " + context.getName());
    }

    public void onFinish(ITestContext context) {
        //LogUtils.info("Finished test suite: " + context.getName());
    }


}
