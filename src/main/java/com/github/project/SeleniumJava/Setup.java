package com.github.project.SeleniumJava;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.compress.harmony.pack200.NewAttribute;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Setup extends TestBase{

	@BeforeSuite(alwaysRun = true)
	@Parameters(value = { "projectName" })
	public void assignProduct(@Optional String value) throws Exception {
		
		startTime = System.currentTimeMillis();
		try {
			productName = value;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@BeforeClass(alwaysRun = true)
	@Parameters(value= { "projectName" })
	public void assignDriver(@Optional String value) throws Exception {
		
		try {
			productName = value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		initializeDriver();
		if (productName == null) {
			productName = config.getProperty("productName");
		}
		
		driver.get(data.getProperty("baseURL_" + productName));
	}
	
	@AfterClass(alwaysRun = true)
	public void quitDriver() {
		
		if (driver != null) {
			driver.quit();
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void checkForNullDriverBeforeMethod(Method method) throws Exception {
		if (driver == null) {
			initializeDriver();
			driver.get(data.getProperty("baseURL." + productName));
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void checkStatusAfterMethod(ITestResult result) {
		
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String date = sdf.format(new Date());
				String url = driver.getCurrentUrl().replaceAll("[\\/:*\\?\"<>\\|]", "_");
				String ext = ".png";
				String path = getScreenshotSavePath() + File.separator + date + "_" + result.getName() + "_" + "FAIL_" + url + ext;
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(scrFile, new File(path));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			
			}else if(result.getStatus() == ITestResult.SKIP) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String date = sdf.format(new Date());
				String url = driver.getCurrentUrl().replaceAll("[\\/:*\\?\"<>\\|]", "_");
				String ext = ".png";
				String path = getScreenshotSavePath() + File.separator + date + "_" + result.getName() + "_" + "SKIP_" + url + ext;
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(scrFile, new File(path));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else {
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		
	}
	
	@AfterTest(alwaysRun = true)
	public void afterTest() {
		
	}
	
	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		
		endTime = System.currentTimeMillis();
		try {
			ConfigLoader.config_loader();
			String xmlTitle = TestListeners.testSuiteName;
			CreatePieChart piechart = new CreatePieChart(xmlTitle, ConfigLoader.passed, ConfigLoader.failed, ConfigLoader.skipped);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.info("Total Execution Duration: " + ConfigLoader.exe_time);
	}
	
	
	
	
	
}
