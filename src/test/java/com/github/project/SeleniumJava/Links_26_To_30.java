package com.github.project.SeleniumJava;

import java.util.List;
import java.util.logging.Level;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;
import org.openqa.selenium.logging.*;

/* 
 * Infinite Scroll
 * Inputs
 * JQueryUIMenus
 * Javascript Alerts
 * Javascript  On Load Event Error 
 * 
 * */


public class Links_26_To_30 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=0, description="checkForInfiniteScroll")
    public void checkForInfiniteScroll() {
        
    	try {
            
    		SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_InfiniteScrollLink), "HomePage_InfiniteScrollLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_InfiniteScrollLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.InfiniteScroll_Header), "FormAuthentication_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.InfiniteScroll_Paragraph), "FormAuthentication_Paragraph is NOT displayed!");
            
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Initial count of paragraphs
            List<WebElement> paragraphs = driver.findElements(InternetHerokuAppLocators.InfiniteScroll_Paragraph);
            int previousCount = paragraphs.size();

            System.out.println("Initial paragraph count: " + previousCount);

            int scrolls = 5;
            for (int i = 1; i <= scrolls; i++) {
                // Scroll to bottom
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000); // wait for content to load

                // Check if new paragraphs have loaded
                paragraphs = driver.findElements(By.className("jscroll-added"));
                int currentCount = paragraphs.size();

                System.out.println("Scroll #" + i + " — Paragraph count: " + currentCount);

                if (currentCount == previousCount) {
                    System.out.println("No new content loaded. Stopping scroll.");
                    break;
                }
                previousCount = currentCount;
            }
            
            asrt.assertAll();          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=1, description="checkForInputs")
    public void checkForInputs() {
    	try {
    		
            SoftAssert asrt = new SoftAssert();
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_InputsLink), "HomePage_InputsLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_InputsLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Inputs_Header), "Inputs_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Inputs_Paragraph), "Inputs_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Inputs_InputBox), "Inputs_InputBox is NOT displayed!");
            
            WebElement numberInput = driver.findElement(InternetHerokuAppLocators.Inputs_InputBox);

            // Clear and enter a number
            numberInput.clear();
            numberInput.sendKeys("42");

            Thread.sleep(1000);

            // Increment number using arrow up key
            numberInput.sendKeys(Keys.ARROW_UP);
            numberInput.sendKeys(Keys.ARROW_UP);

            Thread.sleep(1000);

            // Decrement number using arrow down key
            numberInput.sendKeys(Keys.ARROW_DOWN);

            Thread.sleep(1000);

            // Print current value -> NOT working because no such attribute is there with input element
//            String value = numberInput.getDomAttribute("value");
//            System.out.println("Final input value: " + value);

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @Test(priority=2, description="checkForJQueryUIMenus")
    public void checkForJQueryUIMenus() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_JQueryUIMenusLink), "HomePage_JQueryUIMenusLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_JQueryUIMenusLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_Header), "JQueryUIMenus_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_Paragraph), "JQueryUIMenus_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_EnabledMenu), "JQueryUIMenus_EnabledMenu is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_DisabledMenu), "JQueryUIMenus_DisabledMenu is NOT displayed!");

            WebElement enabledMenu = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_EnabledMenu); // "Enabled"
            WebElement downloadsMenu = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_DownloadsMenu); // "Downloads"
            WebElement backToJQueryUIOption = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_BackToJQueryUI_Option); // "Back To JQuery UI Option"
            WebElement pdfOption = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_PDFOption); // "PDF"

            // Hover using Actions
            Actions actions = new Actions(driver);
            actions.moveToElement(enabledMenu).pause(2000)
            		.moveToElement(downloadsMenu).pause(2000)
            		.moveToElement(pdfOption).click().pause(2000)
            		.perform();

            System.out.println("Clicked on PDF download option.");
            
            // Optional: add a wait to observe result
            Thread.sleep(2000);
            
			/*
			 * After clicking PDF, the jQuery menu collapses. When you try to reuse the same
			 * WebElement enabledMenu, Selenium throws an error like:
			 * 
			 * ElementNotInteractableException: element not interactable
			 * 
			 * This happens because:
			 * 
			 * The DOM refreshes or re-renders the menu items after each click.
			 * 
			 * You're referencing the same WebElement again, but that reference is no longer
			 * valid or visible.
			 */
            
            driver.navigate().refresh();
            enabledMenu = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_EnabledMenu); // "Enabled"
            backToJQueryUIOption = driver.findElement(InternetHerokuAppLocators.JQueryUIMenus_BackToJQueryUI_Option); // "Back To JQuery UI Option"
            
            actions.moveToElement(enabledMenu).pause(2000)
    				.moveToElement(backToJQueryUIOption).click().pause(2000)
    				.perform();
            System.out.println("Clicked on Back To JQuery Page option.");
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIPage_Header), "JQueryUIPage_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIPage_Paragraph), "JQueryUIPage_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIPage_MenuLink), "JQueryUIPage_MenuLink is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIPage_JQueryOfficialLink), "JQueryUIPage_JQueryOfficialLink is NOT displayed!");

	        clickWhenClickable(InternetHerokuAppLocators.JQueryUIPage_JQueryOfficialLink);
	        System.out.println("Clicked on official JQuery Link!");
	        asrt.assertTrue(driver.getCurrentUrl().contains("jqueryui.com"), "Current URL does not contain jqueryui.com!");
	        Thread.sleep(3000);
	        driver.navigate().back();
	        
	        clickWhenClickable(InternetHerokuAppLocators.JQueryUIPage_MenuLink);
	        System.out.println("Clicked on JQuery UI Menu Link.");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_Header), "JQueryUIMenus_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_Paragraph), "JQueryUIMenus_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_EnabledMenu), "JQueryUIMenus_EnabledMenu is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JQueryUIMenus_DisabledMenu), "JQueryUIMenus_DisabledMenu is NOT displayed!");
	        
	        asrt.assertAll();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=3, description="checkForJavascriptAlerts", dataProvider = "promptInputs")
    public void checkForJavascriptAlerts(String inputText, String expectedResult) {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_JavascriptAlertsLink), "HomePage_JavascriptAlertsLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_JavascriptAlertsLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavascriptAlerts_Header), "Inputs_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavascriptAlerts_Paragraph), "JavascriptAlerts_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavascriptAlerts_JSAlert_Button), "JavascriptAlerts_JSAlert_Button is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavascriptAlerts_JSConfirm_Button), "JavascriptAlerts_JSConfirm_Button is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavascriptAlerts_JSPrompt_Button), "JavascriptAlerts_JSPrompt_Button is NOT displayed!");

            // 1️⃣ JS Alert – Simple OK
            clickWhenClickable(InternetHerokuAppLocators.JavascriptAlerts_JSAlert_Button);
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert Text: " + alert.getText());
            asrt.assertEquals(alert.getText(), "I am a JS Alert");
            alert.accept();  // Click OK
            // Optional: Validate result text
            WebElement resultText = driver.findElement(InternetHerokuAppLocators.JavascriptAlerts_ResultText);
            System.out.println("Result: " + resultText.getText());
            asrt.assertEquals(resultText.getText(), "You successfully clicked an alert");
            
            Thread.sleep(1000);

            // 2️⃣ JS Confirm – OK and Cancel
            clickWhenClickable(InternetHerokuAppLocators.JavascriptAlerts_JSConfirm_Button);
            alert = driver.switchTo().alert();
            System.out.println("Confirm Text: " + alert.getText());
            asrt.assertEquals(alert.getText(), "I am a JS Confirm");
            alert.dismiss();  // Click Cancel
            resultText = driver.findElement(InternetHerokuAppLocators.JavascriptAlerts_ResultText);
            System.out.println("Result after Cancel: " + resultText.getText());
            asrt.assertEquals(resultText.getText(), "You clicked: Cancel");
            Thread.sleep(1000);
            
            // 3️⃣ JS Prompt – Accept input
            clickWhenClickable(InternetHerokuAppLocators.JavascriptAlerts_JSPrompt_Button);
            alert = driver.switchTo().alert();
            System.out.println("Prompt Text: " + alert.getText());
            asrt.assertEquals(alert.getText(), "I am a JS prompt");
            alert.sendKeys(inputText);
            alert.accept();
            resultText = driver.findElement(InternetHerokuAppLocators.JavascriptAlerts_ResultText);
            System.out.println("Prompt Result: " + resultText.getText());
            asrt.assertEquals(resultText.getText(), expectedResult);
            
            asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @DataProvider
    public Object[][] promptInputs() {
        return new Object[][] {
            {"Selenium", "You entered: Selenium"},
            {"", "You entered:"}
            //{null, "You entered: null"}  // Optional: skip or handle `null` case separately
        };
    }
    
    
    @Test(priority=4, description="checkForJavaScriptOnloadEventError")
    public void checkForJavaScriptOnloadEventError() {
        
    	try {
            
    		driver.quit();
    		
    		// Enable logging preferences
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);

            ChromeOptions options = new ChromeOptions();
            options.setCapability("goog:loggingPrefs", logPrefs);

            driver = new ChromeDriver(options);
            driver.manage().window().fullscreen();
            driver.get(data.getProperty("baseURL_" + productName));
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_JavascriptOnloadEventErrorLink), "HomePage_JavascriptOnloadEventErrorLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_JavascriptOnloadEventErrorLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.JavaScriptOnloadEventError_Parapraph), "JavaScriptOnloadEventError_Parapraph is NOT displayed!");
	
	        // Capture browser logs
	        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
	        boolean errorFound = false;

	        System.out.println("=== JavaScript Console Logs ===");
	        for (LogEntry entry : logEntries) {
	            System.out.println(entry.getLevel() + ": " + entry.getMessage());

	            // Look for JavaScript errors
	            if (entry.getLevel() == Level.SEVERE && entry.getMessage().contains("Cannot read properties")) {
	                errorFound = true;
	            }
	        }

	        if (errorFound) {
	            System.out.println("\n✅ JavaScript error was detected in console.");
	        } else {
	            System.out.println("\n❌ No JavaScript error was found.");
	        }
	        
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
