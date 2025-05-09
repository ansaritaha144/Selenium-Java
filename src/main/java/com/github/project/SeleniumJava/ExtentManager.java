package com.github.project.SeleniumJava;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	public static final ExtentReports extentReports = new ExtentReports();
	
	public static ExtentReports createExtentReports() {
		
		final String osName = System.getProperty("os.name");
		final String osArchitechture = System.getProperty("os.arch");
		final String path = System.getProperty("user.dir") + "//reports//WebReports//extent-report.html";
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", "Taha Ansari");
		extentReports.setSystemInfo("OS: ", osName);
		extentReports.setSystemInfo("OS Architechture", osArchitechture);
		
		extentReports.addTestRunnerOutput(System.getProperty("user.dir") + "//reports//WebReports//pieChart.jpeg");
				
		return extentReports;
		
	}
	
}
