package com.github.project.SeleniumJava;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class TestListeners extends TestBase implements ITestListener{

	public static Map<String, String> testStatistics = new HashMap<>();
	public static Map<String, String> failedtests = new HashMap<>();
	public static Map<String, String> skippedtests = new HashMap<>();
	public static Map<String, String> passedtests = new HashMap<>();
	public static Map<String, String> suiteName = new HashMap<>();
	public static String testSuiteName = "";
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		Log.info(getTestMethodName(result) + " test is starting.");
		ExtentTestManager.startTest(getTestMethodName(result), result.getMethod().getDescription());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		Log.info(getTestMethodName(result) + " test is succeed.");
		ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
		
		testStatistics.put(result.getMethod().getMethodName().toString(), "PASSED");
		passedtests.put(result.getMethod().getMethodName().toString(), "PASSED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		Log.info(getTestMethodName(result) + " test is failed.");
		
		//Giving life to a driver - driver is a field attached to class and not the method.
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String url = driver.getCurrentUrl().replaceAll("[\\/:*\\?\"<>\\/]", "_");
		String ext = ".png";
		String path = getScreenshotSavePath() + File.separator + date + "_" + result.getName() + "_" + "SKIP_" + url + ext;
		String filePath = null;
		
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver, path);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//Take base64Screenshot screenshot for extent reports
		String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull (driver)).getScreenshotAs(OutputType.BASE64);
		
		if (testStatistics.containsKey(result.getMethod().getMethodName().toString())) {
			if (testStatistics.get(result.getMethod().getMethodName().toString()).contentEquals("PASSED")) {
				
			}else {
				testStatistics.put(result.getMethod().getMethodName().toString(), "FAILED");
				passedtests.put(result.getMethod().getMethodName().toString(), "FAILED");
			}
		}else {
			testStatistics.put(result.getMethod().getMethodName().toString(), "FAILED");
			passedtests.put(result.getMethod().getMethodName().toString(), "FAILED");
		}
		
		ExtentTestManager.getTest().log(Status.PASS, "Test Failed", ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
		Log.info(getTestMethodName(result) + " test is skipped.");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
		
		if (!testStatistics.containsKey(result.getMethod().getMethodName().toString())) {
			testStatistics.put(result.getMethod().getMethodName().toString(), "SKIPPED");
			passedtests.put(result.getMethod().getMethodName().toString(), "SKIPPED");
		}
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
		Log.info("I am in onStart method " + context.getName());
		//context.setAttribute("WebDriver", this.driver);
		String testName = context.getCurrentXmlTest().getName();
		String threadId = String.valueOf(Thread.currentThread().getId());
		testSuiteName = context.getSuite().getXmlSuite().getName();
		suiteName.put(testName, threadId);
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		Log.info("I am in onFinish method " + context.getName());
		ExtentManager.extentReports.flush();
	}

	
	
}
