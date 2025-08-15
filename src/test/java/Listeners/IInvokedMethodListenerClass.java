package Listeners;

import Utilities.LogUtils;
import Utilities.Utilities;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        //LogUtils.info("Before invocation of method: " + method.getTestMethod().getMethodName());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        LogUtils.info("After invocation of method: " + method.getTestMethod().getMethodName());
        File logFile = Utilities.getLastFile(LogUtils.Log_Path);
        try {
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogUtils.info("TC" + testResult.getName() + " has been failed");
            try {
                Utilities.takeScreenshot(getDriver(), testResult.getName());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
