package com.github.project.SeleniumJava;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class Retry extends TestBase implements IRetryAnalyzer{

	private int count = 0;
    private static int maxTry = 1;

    // Below method returns 'true' if the test method has to be retried else 'false'
    // It takes the 'result' as parameter of the test method that just ran
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) { // Check if test not succeed
            if (count < maxTry) { // Check if maxTry count is reached
                count++; // Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed and take base64Screenshot
                extendReportsFailOperations(iTestResult); // ExtentReports fail operations
                return true; // Tells TestNG to re-run the test
            } else {
                iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
            }
        }
        return false;
    }

    public String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        // Object testClass = iTestResult.getInstance();
        // WebDriver webDriver = ((Setup) testClass).initializeDriver();
        try {
            driver = (WebDriver) iTestResult.getTestClass().getRealClass().getField("driver")
                    .get(iTestResult.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String base64Screenshot = "data:image/png;base64,"
                + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed", ExtentTestManager.getTest()
                .addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }
}
