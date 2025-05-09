package com.github.project.SeleniumJava;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

public class HomePage extends InternetHerokuAppBase{

	@BeforeClass
	public void resetPage() {	
		driver.get(data.getProperty("baseURL." + productName));
	}
	
	@BeforeMethod
	public void beforeMethod() {
		driver.get(data.getProperty("baseURL." + productName));
	}
	
	@Test(priority=-1, description="checkHomeUI")
	public void checkHomeUI() {
		
		try {
			
			SoftAssert asrt = new SoftAssert();
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
			List<WebElement> listElements = driver.findElements(InternetHerokuAppLocators.HomePage_LinkListElements);
			System.out.println("No. Of Available Links are: " + listElements.size());
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FooterLink), "HomePage_FooterLink is NOT displayed!");
			asrt.assertAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test(priority=0, description="checkAddRemoveElements")
	public void checkAddRemoveElements() {
		
		try {
			
			SoftAssert asrt = new SoftAssert();
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_AddRemoveElementsLink), "HomePage_AddRemoveElementsLink is NOT displayed!");				
			clickWhenClickable(InternetHerokuAppLocators.HomePage_AddRemoveElementsLink);
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.AddRemovePage_Header), "AddRemovePage_Header is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.AddRemovePage_AddElementButton), "AddRemovePage_AddElementButton is NOT displayed!");
			
			Random random = new Random();
	        int randomNumber = random.nextInt(10) + 1; // 1 to 10
	        
	        for (int i = 0; i < randomNumber; i++) {
				clickWhenClickable(InternetHerokuAppLocators.AddRemovePage_AddElementButton);
			}
	        
	        int sizeOfDeleteButton = driver.findElements(InternetHerokuAppLocators.AddRemovePage_DeleteElementButton).size();
	        asrt.assertEquals(sizeOfDeleteButton, randomNumber, "No. Of Delete Button is NOT equal to No. Of Times Add Element was clicked!");
	        
	        for (int i = 0; i < randomNumber; i++) {
	        	if (isElementDisplayed(InternetHerokuAppLocators.AddRemovePage_DeleteElementButton)) {
	        		clickWhenClickable(InternetHerokuAppLocators.AddRemovePage_DeleteElementButton);
				}else {
					System.out.println("Delete Button Is No Longer Present!");
				}
			}
			asrt.assertAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=0, description="checkBasicAuth")
	public void checkBasicAuth() {
		
		try {
			
			SoftAssert asrt = new SoftAssert();
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_BasicAuthLink), "HomePage_BasicAuthLink is NOT displayed!");							
			
			driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
			
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.BasicAuthPage_Header), "BasicAuthPage_Header is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.BasicAuthPage_SuccessMessage), "BasicAuthPage_SuccessMessage is NOT displayed!");
			
			asrt.assertAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=0, description="checkBrokenImages")
	public void checkBrokenImages() {
		
		try {
			
			SoftAssert asrt = new SoftAssert();
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_BasicAuthLink), "HomePage_BasicAuthLink is NOT displayed!");							
			
			driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
			
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.BasicAuthPage_Header), "BasicAuthPage_Header is NOT displayed!");
			asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.BasicAuthPage_SuccessMessage), "BasicAuthPage_SuccessMessage is NOT displayed!");
			
			asrt.assertAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
