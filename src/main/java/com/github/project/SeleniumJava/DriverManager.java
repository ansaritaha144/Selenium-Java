package com.github.project.SeleniumJava;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

	public static WebDriver driver = null;
	
	public static WebDriver getDriver() {
		
		driver = new ChromeDriver();
		
		
		return driver;
		
	}
	
	
	
	
}
