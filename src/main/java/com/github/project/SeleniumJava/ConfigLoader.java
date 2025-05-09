package com.github.project.SeleniumJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;

public class ConfigLoader {

	public static Properties configBase = null;
	public static String passed="0", skipped="0", failed="0", total_TC="0", link, projectName="", exe_time="0";
	
	public static void config_loader() throws Exception {
		
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		String dateToStr = format.format(curDate);
		
		curDate = new Date();
		format = new SimpleDateFormat("yyyymmdd_HHmmss");
		String linkDate = format.format(curDate);
		
		configBase = new Properties();
		String config_fileName = "config.properties";
		String config_path = System.getProperty("user.dir") + File.separator + "config" + File.separator + config_fileName;
		FileInputStream config_ip = new FileInputStream(config_path);
		configBase.load(config_ip);
		
		passed = String.valueOf(Collections.frequency(TestListeners.testStatistics.values(), "PASSED"));
		failed = String.valueOf(Collections.frequency(TestListeners.testStatistics.values(), "FAILED"));
		skipped = String.valueOf(Collections.frequency(TestListeners.testStatistics.values(), "SKIPPED"));
		total_TC = String.valueOf((Integer.parseInt(passed)) + Integer.parseInt(failed) + Integer.parseInt(skipped));
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String percent = df.format((Float.parseFloat(passed) / Float.parseFloat(total_TC) * 100));
		long exetime = TestBase.endTime - TestBase.startTime;
		exe_time = df.format(Math.round(exetime / 60000));
		exe_time = String.format("%02d", Math.round(exetime / 60000));
		
		File propertiesFile = new File(config_path);
		
		PropertiesConfiguration config = new PropertiesConfiguration();
		PropertiesConfigurationLayout layout = config.getLayout();
		
		/* Writing Into Config File */
		layout.load(config, new InputStreamReader(new FileInputStream(propertiesFile)));
		layout.setHeaderComment(dateToStr);
		
		config.setProperty("passed", passed);
		config.setProperty("failed", failed);
		config.setProperty("skipped", skipped);
		config.setProperty("passingperct", percent);
		config.setProperty("totaltc", passed);
		config.setProperty("executiontime", exe_time);
		config.setProperty("runType", "Sanity");
		
		layout.save(config, new FileWriter(config_path, false));
		
	}
	
}
