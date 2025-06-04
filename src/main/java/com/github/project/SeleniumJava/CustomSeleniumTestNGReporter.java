package com.github.project.SeleniumJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.xml.XmlSuite;
import org.testng.ISuite;
import org.testng.IReporter;


public class CustomSeleniumTestNGReporter extends TestBase implements IReporter {

	public static Map<ITestResult, String> failedtests = new HashMap<>();
	public static Map<ITestResult, String> skippedtests = new HashMap<>();
	public static Map<ITestResult, String> passedtests = new HashMap();
	// This is the customize emailabel report template file path.
	private static final String emailableReportTemplateFile = System.getProperty("user.dir") + File.separator
			+ "src" + File.separator + "main" + File.separator + "java" + File.separator +  "com" + File.separator + "github" + File.separator + "project" + File.separator + "SeleniumJava" + File.separator + "customize-emailable-report-template.html";

	public static String totalTestCount = "";
	public static String totalTestPassed = "";
	public static String totalTestFailed = "";
	public static String totalTestSkipped = "";
	public static String browserName = "";

	// public static Properties config = null;
	// public static String deviceName="";
	// public static String allDevices="";
	// public static String deviceVersion="";

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		try {

			// Get content data in TestNG report template file.
			String customReportTemplateStr = this.readEmailabelReportTemplate();

			// Create custom report title.
			String customReportTitle = this.getCustomReportTitle("Custom Selenium - TestNG Report");

			// Create test suite summary data.
			String customSuiteSummary = this.getTestSuiteSummary(suites);

			// Create test methods summary data.
			String customTestMethodSummary = this.getTestMethodSummary(suites);

			// Replace report title place holder with custom title.
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$",
					customReportTitle);

			// Replace test suite place holder with custom test suite summary.
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\s", customSuiteSummary);

			// Replace test methods place holder with custom test method summary.
			customTestMethodSummary = customTestMethodSummary.replaceAll("\\$", "");
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Detail\\S",
					customTestMethodSummary);

			// Write replaced test report content to custom-emailable-report.html.
			outputDirectory = System.getProperty("user.dir") + File.separator + "reports" + File.separator
					+ "WebReports" + File.separator + "/custom-emailable-report.html";

			File targetFile = new File(outputDirectory);

			// File targetFile= new File(outputDirectory + "/custom-emailable-report.html");
			FileWriter fw = new FileWriter(targetFile);
			fw.write(customReportTemplateStr);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/* Read template content. */
	private String readEmailabelReportTemplate() {

		StringBuffer retBuf = new StringBuffer();

		try {
			File file = new File(this.emailableReportTemplateFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while (line != null) {
				retBuf.append(line);
				line = br.readLine();
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();

		} finally {
			return retBuf.toString();
		}
	}

	/* Build custom report title. */
	private String getCustomReportTitle(String title) {

		StringBuffer retBuf = new StringBuffer();
		retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
		return retBuf.toString();
	}

	/* Build test suite summary data. */
	private String getTestSuiteSummary (List<ISuite> suites) {

		StringBuffer retBuf = new StringBuffer();
		try {
			for (ISuite tempSuite : suites) {
				retBuf.append("<tr><td colspan=11><center><b>" + tempSuite.getName() + "</b></center></td></tr>");
				Map<String, ISuiteResult> testResults = tempSuite.getResults();
				
				for (ISuiteResult result: testResults.values()) {

					retBuf.append("<tr>");
					ITestContext testObj = result.getTestContext();

					//System.out.println(testobj.getPassedTests().size());
					//totalTest Passed = String.valueOf(Collections.frequency (Listeners.testStatistics.values(), "PASSED")); totalTestFailed = String.valueOf(Collections.frequency (Listeners.testStatistics.values(), "FAILED"));
					//totalTestSkipped = String.valueOf(Collections.frequency (Listeners.testStatistics.values(), "SKIPPED"));
					//int total = Integer.parseInt(totalTestPassed)+Integer.parseInt(totalTestFailed)+Integer.parseInt(totalTestSkipped);
					//totalTestCount = String.valueOf(total);
						
					IResultMap testFailedResult = testObj.getFailedTests();
					IResultMap testSkippedResult = testObj.getSkippedTests();
					IResultMap testPassedResult = testObj.getPassedTests();

					for (ITestNGMethod method : testPassedResult.getAllMethods()) {
						if (testFailedResult.getAllMethods().contains(method)) { 
							testFailedResult.removeResult (method);
						}
					}

					for (ITestNGMethod method : testSkippedResult.getAllMethods()) {
						if (testFailedResult.getAllMethods().contains(method)) { 
							testSkippedResult.removeResult (method);
						}
					}

//					System.out.println(testFailedResult.size());
//					System.out.println(testPassedResult.size());
//					System.out.println(testSkippedResult.size());

					totalTestPassed = testPassedResult.size() + "";
					totalTestFailed = testFailedResult.size() + "";
					totalTestSkipped = testSkippedResult.size() + "";
					totalTestCount = (Integer.parseInt(totalTestPassed) + Integer.parseInt(totalTestFailed)
					+ Integer.parseInt(totalTestSkipped)) + "";

					/* Test name. */
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(testObj.getName());
					// retBuf.append(deviceName);
					retBuf.append("</td>");

					/* Total method count. */
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(totalTestCount);
					retBuf.append("</td>");

					/* Passed method count. */
					if (Integer.parseInt(totalTestPassed) > 0) {
						// retBuf.append("<td bgcolor=9ACD32>");
						retBuf.append("<td bgcolor=C5F594 style='text-align:center'>");
						retBuf.append(totalTestPassed);
						retBuf.append("</td>");
					} else {
						retBuf.append("<td> style='text-align:center'");
						retBuf.append(totalTestPassed);
						retBuf.append("</td>");
					}
					
					/*Skipped method count*/
					if (Integer.parseInt(totalTestSkipped) == 0) {
						retBuf.append("<td style='text-align:center'>");
						retBuf.append(totalTestSkipped);
						retBuf.append("</td>");
					}else {
						retBuf.append("<td bgcolor=#CCC style='text-align:center'>");
						retBuf.append(totalTestSkipped);
						retBuf.append("</td>");
					}
					
					/*Failed method count*/
					// retBuf.append("<td bgcolor=#FF7A58>");
					if (Integer.parseInt(totalTestFailed) == 0) {
						retBuf.append("<td style='text-align:center'>");
						retBuf.append(totalTestFailed);
						retBuf.append("</td>");
					}else {
						retBuf.append("<td bgcolor=#F39F86 style='text-align:center'>");
						retBuf.append(totalTestFailed);
						retBuf.append("</td>");
					}
					
					/*Get Browser Type*/
					String browserType = tempSuite.getParameter("browser");
					if (browserType == null || browserType.trim().length() == 0) {
						browserType = config.getProperty("browser").toUpperCase();
					}
					
					/* Start Time */
					Date startDate = testObj.getStartDate();
					String startTimeOfTest = testObj.getStartDate().toString();
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(this.getDateInStringFormat(startDate));
					retBuf.append("</td>");

					/* End Date */
					Date endDate = testObj.getEndDate();
					String endTimeOfTest = testObj.getEndDate().toString();
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(this.getDateInStringFormat(endDate));
					retBuf.append("</td>");

					/* Execute Time */
					long deltaTime = endDate.getTime() - startDate.getTime();
					String deltaTimeStr = this.convertDeltaTimeToString(deltaTime);
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(deltaTimeStr);
					retBuf.append("</td>");

					/* Include groups. */
					String includedGroups = this.stringArrayToString(testObj.getIncludedGroups());
					if (includedGroups == "") {
					    includedGroups = "NA";
					}
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(includedGroups);
					retBuf.append("</td>");

					/* Exclude groups. */
					String excludedGroups = this.stringArrayToString(testObj.getExcludedGroups());
					if (excludedGroups == "") {
					    excludedGroups = "NA";
					}
					retBuf.append("<td style='text-align:center'>");
					retBuf.append(excludedGroups);
					retBuf.append("</td>");

					retBuf.append("</tr>");

				}
			}
		
		}catch(Exception ex) {
					ex.printStackTrace();
		}finally {
			return retBuf.toString();
		}
	}

	/* Get date string format value */
	private String getDateInStringFormat(Date date) {

		StringBuffer retBuf = new StringBuffer();
		if (date == null) {
			date = new Date();
		}
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		// DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		retBuf.append(df.format(date));
		return retBuf.toString();

	}

	/* Get test method summary info. */
	private String getTestMethodSummary(List<ISuite> suites) {
		StringBuffer retBuf = new StringBuffer();

		try {
			for (ISuite tempSuite : suites) {
				retBuf.append("<tr><td colspan=7><center><b>" + tempSuite.getName() + "</b></center></td></tr>");

				Map<String, ISuiteResult> testResults = tempSuite.getResults();

				for (ISuiteResult result : testResults.values()) {
					ITestContext testObj = result.getTestContext();

					String testName = testObj.getName();

					IResultMap testFailedResult = testObj.getFailedTests();
					IResultMap testSkippedResult = testObj.getSkippedTests();
					IResultMap testPassedResult = testObj.getPassedTests();

					for (ITestNGMethod method : testPassedResult.getAllMethods()) {
						if (testFailedResult.getAllMethods().contains(method)) {
							testFailedResult.removeResult(method);
						}
					}

					for (ITestNGMethod method : testSkippedResult.getAllMethods()) {
						if (testFailedResult.getAllMethods().contains(method)) {
							testSkippedResult.removeResult(method);
						}
					}

					System.out.println(testFailedResult);
					System.out.println(testPassedResult);
					System.out.println(testSkippedResult);

					if (!(testPassedResult.size() == 0)) {
						String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);
						retBuf.append(passedTestMethodInfo);
					}

					if (!(testFailedResult.size() == 0)) {
						String failedTestMethodInfo = this.getTestMethodReport(testName, testFailedResult, false,
								false);
						retBuf.append(failedTestMethodInfo);
					}

					if (!(testSkippedResult.size() == 0)) {
						String skippedTestMethodInfo = this.getTestMethodReport(testName, testSkippedResult, false,
								true);
						retBuf.append(skippedTestMethodInfo);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return retBuf.toString();
		}
	}

	/* Get failed, passed or skipped test methods report. */
	private String getTestMethodReport(String testName, IResultMap testResultMap, boolean passedReault, boolean skippedResult) {
	    
		StringBuffer retStrBuf = new StringBuffer();
	    String resultTitle = testName;

	    String color = "9ACD32";

	    if (skippedResult) {
	        resultTitle += " - Skipped ";
	        color = "#CCC";
	    } else {
	        if (!passedReault) {
	            resultTitle += " - Failed ";
	            color = "F39F86";
	        } else {
	            resultTitle += " - Passed ";
	            // color = "lime";
	            color = "C5F594";
	        }
	    }

	    retStrBuf.append("<tr bgcolor=" + color + "><td colspan=7><center><b>" + resultTitle + "</b></center></td></tr>");

	    Set<ITestResult> testResultSet = testResultMap.getAllResults();

	    for (ITestResult testResult : testResultSet) {
	        String testClassName = "";
	        String testMethodName = "";
	        String startDateStr = "";
	        String executeTimeStr = "";
	        String paramStr = "";
	        String reporterMessage = "";
	        String exceptionMessage = "";

	        // Get testClassName
	        testClassName = testResult.getTestClass().getName();

	        // Get testMethodName
	        testMethodName = testResult.getMethod().getMethodName();

	        // Get startDateStr
	        long startTimeMillis = testResult.getStartMillis();
	        startDateStr = this.getDateInStringFormat(new Date(startTimeMillis));

	        // Get Execute time.
	        long deltaMillis = testResult.getEndMillis() - testResult.getStartMillis();
	        executeTimeStr = this.convertDeltaTimeToString(deltaMillis);

	        // Get parameter list.
	        Object paramObjArr[] = testResult.getParameters();
	        for (Object paramObj : paramObjArr) {
	            paramStr += (String) paramObj;
	            paramStr += " ";
	        }

	        // Get reporter message list.
	        List<String> repoterMessageList = Reporter.getOutput(testResult);
	        for (String tmpMsg : repoterMessageList) {
	            reporterMessage += tmpMsg;
	            reporterMessage += " ";
	        }

	        // Get exception message.
	        Throwable exception = testResult.getThrowable();
	        if (exception != null) {
	            StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            exception.printStackTrace(pw);

	            exceptionMessage = sw.toString();
	        }

	        retStrBuf.append("<tr bgcolor=" + color + ">");

	        /* Add test class name. */
	        retStrBuf.append("<td>");
	        retStrBuf.append(testClassName);
	        retStrBuf.append("</td>");

	        /* Add test method name. */
	        retStrBuf.append("<td style='text-align:center'>");
	        retStrBuf.append(testMethodName);
	        retStrBuf.append("</td>");

	        /* Add start time. */
	        retStrBuf.append("<td style='text-align:center'>");
	        retStrBuf.append(startDateStr);
	        retStrBuf.append("</td>");

	        /* Add execution time. */
	        retStrBuf.append("<td style='text-align:center'>");
	        retStrBuf.append(executeTimeStr);
	        retStrBuf.append("</td>");

	        /* Add parameter. */
	        retStrBuf.append("<td>");
	        retStrBuf.append(paramStr);
	        retStrBuf.append("</td>");

	        /* Add reporter message. */
	        retStrBuf.append("<td>");
	        retStrBuf.append(reporterMessage);
	        retStrBuf.append("</td>");

	        /* Add exception message. */
	        retStrBuf.append("<td>");
	        if (exceptionMessage != "") {
	            retStrBuf.append(exceptionMessage);
	        } else {
	            retStrBuf.append("Test Passed Successfully !!!");
	        }
	        retStrBuf.append("</td>");

	        retStrBuf.append("</tr>");
	    }
	    
	    return retStrBuf.toString();
	}
	
	/* Convert long type deltaTime to string with format hh:mm:ss. */
	private String convertDeltaTimeToString(long deltaTime) {
		StringBuffer retBuf = new StringBuffer();
		String exe_time = "";

		long milli = deltaTime;
		long minutes = 0, hours = 0, seconds = 0;

		if (milli < 1000) {
			exe_time = String.format(milli + " ms");
		} else {
			seconds = deltaTime / 1000;
			exe_time = seconds == 1 ? String.format(seconds + " sec") : String.format(seconds + " secs");
		}

		if (seconds > 59) {
			minutes = seconds / 60;
			long remaining_sec = seconds % 60;
			exe_time = minutes == 1 ? String.format(minutes + " min " + remaining_sec + " secs")
					: String.format(minutes + " mins " + remaining_sec + " secs");
		}

		if (minutes > 59) {
			hours = minutes / 60;
			long remaining_min = minutes % 60;
			exe_time = String.format(hours + ":" + remaining_min + " hrs");
		}

		// String exe_time = String.format("%02d", Math.round(deltaTime / 60000));
		// retBuf.append(hours + ":" + minutes + ":" + seconds + ":" + milli);
		// retBuf.append("00:"+exe_time);

		retBuf.append(exe_time);
		return retBuf.toString();
	}

	/* Convert a string array elements to a string. */
	private String stringArrayToString(String strArr[]) {

		StringBuffer retStrBuf = new StringBuffer();
		if (strArr != null) {
			for (String str : strArr) {
				retStrBuf.append(str);
				retStrBuf.append(" ");
			}
		}
		return retStrBuf.toString();
	}
	
	public static void main(String[] args) {
		
		CustomSeleniumTestNGReporter custom = new CustomSeleniumTestNGReporter();
		String output = custom.convertDeltaTimeToString(113545);
		System.out.println(output);
		System.out.println(System.getProperty("os.name"));
	
	}
	
	

}
