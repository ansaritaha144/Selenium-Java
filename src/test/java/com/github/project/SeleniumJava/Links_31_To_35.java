package com.github.project.SeleniumJava;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

/* 
 * Key Presses
 * Large And Deep DOMs
 * Multiple Windows
 * Nested Frames
 * Notification Messages 
 * 
 * */


public class Links_31_To_35 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=0, description="checkForKeyPresses", dataProvider = "keyData")
    public void checkForKeyPresses(CharSequence key, String expectedKey) {
        
    	try {
            
    		SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_KeyPressesLink), "HomePage_KeyPressesLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_KeyPressesLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.KeyPresses_Header), "KeyPresses_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.KeyPresses_Paragraph), "KeyPresses_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.KeyPresses_InputBox), "KeyPresses_InputBox is NOT displayed!");
            
            WebElement input = driver.findElement(InternetHerokuAppLocators.KeyPresses_InputBox);
            input.click();
            new Actions(driver).sendKeys(key).perform();
            
            asrt.assertTrue(getText(InternetHerokuAppLocators.KeyPresses_ResultText).contains(expectedKey),
                    "Expected key press not found in result: " + getText(InternetHerokuAppLocators.KeyPresses_ResultText)
                );
            
            
            asrt.assertAll();          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] keyData() {
        return new Object[][] {
            { "A", "A" },
            { "B", "B" },
            { "\uE007", "ENTER" }, // Keys.ENTER
            { "\uE008", "SHIFT" },  // Keys.SHIFT
            { "\uE00C", "ESCAPE" },  // Keys.ESCAPE
            { "\uE009", "CONTROL" },  // Keys.CONTROL
            { "\uE00A", "ALT" }  		// Keys.ALT
        };
    }
    
    
    @Test(priority=1, description="checkForLargeAndDeepDOMs")
    public void checkForLargeAndDeepDOMs() {
    	try {
    		
            SoftAssert asrt = new SoftAssert();
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_LargeAndDeepDOMLink), "HomePage_LargeAndDeepDOMLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_LargeAndDeepDOMLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.LargeAndDeepDOM_Header), "Inputs_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.LargeAndDeepDOM_Paragraph), "Inputs_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.LargeAndDeepDOM_Siblings), "LargeAndDeepDOM_Siblings is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.LargeAndDeepDOM_Table), "LargeAndDeepDOM_Table is NOT displayed!");

            /*
            // Locate a cell deep in the table
            // Example: Row 50, Column 3 -> XPath: //tr[50]/td[3]
            WebElement cell = driver.findElement(By.xpath("//table//tr[50]/td[3]"));

            // Scroll the cell into view using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
            Thread.sleep(1000); // Optional wait

            System.out.println("Cell text (Row 50, Col 3): " + cell.getText());

            // Optional: Validate expected value
            if (cell.getText() != null && !cell.getText().isEmpty()) {
                System.out.println("✅ Cell is visible and has content.");
            } else {
                System.out.println("❌ Cell is empty or not rendered properly.");
            }
           
            //✅ Loop Through Rows & Columns:
            for (int i = 1; i <= 50; i++) {
                WebElement row = driver.findElement(By.xpath("//table//tr[" + i + "]"));
                System.out.println("Row " + i + ": " + row.getText());
            }

            List<WebElement> allRows = driver.findElements(By.xpath("//table//tr"));
            System.out.println("Total Rows: " + allRows.size());

            //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			*/
			
            for (int i = 1; i <= 50; i++) {
            	System.out.print("Row " + i + ": ");
            	for(int j=1; j<=3; j++) {
            		WebElement row = driver.findElement(By.cssSelector("div#siblings div#sibling-" + i + "\\." + j));
                    System.out.print(row.getText());	
            	}
            	System.out.println("\n");
            }
            
            
            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @Test(priority=2, description="checkForMultipleWindows")
    public void checkForMultipleWindows() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_MultipleWindowsLink), "HomePage_MultipleWindowsLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_MultipleWindowsLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.MultipleWindows_Header), "MultipleWindows_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.MultipleWindows_NewWindowLink), "MultipleWindows_NewWindowLink is NOT displayed!");

	        // Store original window	
	        String originalWindow = driver.getWindowHandle();
	        
	        // Click the link to open new window
	        clickWhenClickable(InternetHerokuAppLocators.MultipleWindows_NewWindowLink);
	        
	        // Wait for new window and switch
	        Set<String> allWindows = driver.getWindowHandles();
	        for (String winHandle : allWindows) {
	            if (!winHandle.equals(originalWindow)) {
	                driver.switchTo().window(winHandle);
	                break;
	            }
	        }

	        // Validate new window content
	        String newWindowHeader = getText(InternetHerokuAppLocators.MultipleWindows_NewWindowHeader);
	        asrt.assertEquals(newWindowHeader, "New Window", "New Window Header is NOT equal to 'New Window'");

	        // Close new window
	        driver.close();

	        // Switch back to original window
	        driver.switchTo().window(originalWindow);

	        // Validate original page content
	        String originalHeader = getText(InternetHerokuAppLocators.MultipleWindows_Header);
	        asrt.assertEquals(originalHeader, "Opening a new window", "New Window Header is NOT equal to 'Open a new window'");
	        
	        asrt.assertAll();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=3, description="checkForNestedFrames")
    public void checkForNestedFrames() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_NestedFramesLink), "HomePage_NestedFramesLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_NestedFramesLink);
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NestedFrames_TopFrame), "NestedFrames_TopFrame is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NestedFrames_BottomFrame), "NestedFrames_BottomFrame is NOT displayed!");
            
            // Switch to top frame,  in order to check display for left, right and middle frame
            driver.switchTo().frame("frame-top");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NestedFrames_LeftFrame), "NestedFrames_LeftFrame is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NestedFrames_RightFrame), "NestedFrames_RightFrame is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NestedFrames_MiddleFrame), "NestedFrames_MiddleFrame is NOT displayed!");

            // Switch back to original content
            driver.switchTo().defaultContent();
            
            // Switch to top frame, then to left frame
            driver.switchTo().frame("frame-top");
            driver.switchTo().frame("frame-left");
            String leftText = driver.findElement(By.tagName("body")).getText();
            System.out.println("Left Frame Text: " + leftText);

            // Switch back to parent (top) and then to middle
            driver.switchTo().parentFrame();
            driver.switchTo().frame("frame-middle");
            String middleText = driver.findElement(By.id("content")).getText();
            System.out.println("Middle Frame Text: " + middleText);

            // Switch to parent and then to right frame
            driver.switchTo().parentFrame();
            driver.switchTo().frame("frame-right");
            String rightText = driver.findElement(By.tagName("body")).getText();
            System.out.println("Right Frame Text: " + rightText);

            // Switch back to default content before accessing bottom frame
            driver.switchTo().defaultContent();
            driver.switchTo().frame("frame-bottom");
            String bottomText = driver.findElement(By.tagName("body")).getText();
            System.out.println("Bottom Frame Text: " + bottomText);

            // Go back to Frames main page
            driver.switchTo().defaultContent();
            
            asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority=4, description="checkForNotificationMessages")
    public void checkForNotificationMessages() {
        
    	try {
                     
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_NotificationMessagesLink), "HomePage_NotificationMessagesLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_NotificationMessagesLink);
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NotificationMessages_Header), "NotificationMessages_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NotificationMessages_Paragraph), "NotificationMessages_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NotificationMessages_NewNotificationLink), "NotificationMessages_NewNotificationLink is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NotificationMessages_NotificationText), "NotificationMessages_NotificationText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.NotificationMessages_NotificationCloseButton), "NotificationMessages_NotificationCloseButton is NOT displayed!");

            // Click the "Click here" link
	        clickWhenClickable(InternetHerokuAppLocators.NotificationMessages_NewNotificationLink);
	        
	        // Wait briefly to allow notification to appear
	        WebElement messageDiv = driver.findElement(InternetHerokuAppLocators.NotificationMessages_NotificationText);
	        String message = messageDiv.getText().trim().replace("\n×", ""); // Clean up trailing characters

	        System.out.println("Notification Message: " + message);

	        // Validate message is one of the expected outcomes
	        asrt.assertTrue(
	            message.equals("Action successful") ||
	            message.equals("Action unsuccessful, please try again") ||
	            message.equals("Action Unsuccessful"),
	            "Unexpected notification message: " + message
	        );
            
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
