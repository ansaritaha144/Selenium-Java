package com.github.project.SeleniumJava;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

/* 
 * Form Authentication
 * iFrames
 * Geolocation
 * Horizontal Slider
 * Hovers
 * 
 * */

public class Links_21_To_25 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
	
    @Test(priority=0, description="checkForFormAuthentication")
    public void checkForFormAuthentication() {
        
    	try {
            
    		SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FormAuthenticationLink), "HomePage_FormAuthenticationLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_FormAuthenticationLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_Header), "FormAuthentication_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_Paragraph), "FormAuthentication_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_UsernameInput), "FormAuthentication_UsernameInput is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_PasswordInput), "FormAuthentication_PasswordInput is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_LoginButton), "FormAuthentication_LoginButton is NOT displayed!");

            sendKeys(InternetHerokuAppLocators.FormAuthentication_UsernameInput, "tomsmith");
            sendKeys(InternetHerokuAppLocators.FormAuthentication_PasswordInput, "SuperSecretPassword!");
            clickWhenClickable(InternetHerokuAppLocators.FormAuthentication_LoginButton);
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_SecureAreaHeader), "FormAuthentication_SecureAreaHeader is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_SecureAreaParagraph), "FormAuthentication_SecureAreaParagraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_SecureAreaLogoutButton), "FormAuthentication_SecureAreaLogoutButton is NOT displayed!");
            
            if (isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_SecureAreaLogoutButton)) {
				clickWhenClickable(InternetHerokuAppLocators.FormAuthentication_SecureAreaLogoutButton);
			}
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FormAuthentication_Header), "FormAuthentication_Header is NOT displayed After Logout From Secure Area!");

            asrt.assertAll();          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=1, description="checkiFrames")
    public void checkiFrames() {
    	try {
    		
            SoftAssert asrt = new SoftAssert();
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FramesLink), "HomePage_FramesLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_FramesLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Frames_Header), "Frames_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Frames_NestedFramesLink), "Frames_NestedFramesLink is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Frames_iFrameLink), "Frames_iFrameLink is NOT displayed!");
            clickWhenClickable(InternetHerokuAppLocators.Frames_NestedFramesLink);
            
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
            driver.navigate().back();
            
            // --------- iFRAME (WYSIWYG Editor) ----------
            clickWhenClickable(InternetHerokuAppLocators.Frames_iFrameLink);

            // Switch to iframe
            WebElement iframe = driver.findElement(InternetHerokuAppLocators.Frames_Editor_iFrame);
            driver.switchTo().frame(iframe);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Frames_Editor_iFrame), "Frames_Editor_iFrame is NOT displayed!");
            
            // Clear and enter text
            WebElement editorBody = driver.findElement(InternetHerokuAppLocators.Frames_Editor);
            editorBody.clear();
            editorBody.sendKeys("Hello from Selenium!");
            
            Thread.sleep(2000);
            System.out.println("Entered text in iFrame editor.");
            
            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @Test(priority=2, description="checkForGeolocation")
    public void checkForGeolocation() {
        
    	try {
            
    		driver.quit();
    		 // Set Chrome options to mock geolocation
            ChromeOptions options = new ChromeOptions();

            // Set geolocation permission to "granted"
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.geolocation", 1); // 1 = allow
            options.setExperimentalOption("prefs", prefs);

            // Enable DevTools Protocol for overriding geolocation
            driver = new ChromeDriver(options);
            driver.get(data.getProperty("baseURL_" + productName));

	    	SoftAssert asrt = new SoftAssert();
	    	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_GeolocationLink), "HomePage_GeolocationLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_GeolocationLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Header), "Geolocation_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Paragraph), "Geolocation_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_WhereAmI_Button), "Geolocation_WhereAmI_Button is NOT displayed!");
	        
	        clickWhenClickable(InternetHerokuAppLocators.Geolocation_WhereAmI_Button);
	        Thread.sleep(3000);
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Latitude), "Geolocation_Latitude is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Longitude), "Geolocation_Longitude is NOT displayed!");
	        
	        String latitude = getText(InternetHerokuAppLocators.Geolocation_Latitude);
	        String longitude = getText(InternetHerokuAppLocators.Geolocation_Longitude);
	        System.out.println("Latitude Text Is: " + latitude + " AND " + "Longitude Text Is: " + longitude);
	        
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_SeeItOnGoogle_Link), "Geolocation_SeeItOnGoogle_Link is NOT displayed!");
	        clickWhenClickable(InternetHerokuAppLocators.Geolocation_SeeItOnGoogle_Link);
	        String currentURL = driver.getCurrentUrl();
	        if(currentURL.contains("maps")) {
	        	System.out.println("Navigated To Google Maps Successfully!");
	        }else {
	        	System.out.println(currentURL + " does not contain maps!");
	        }
	        
	        driver.navigate().back();
	        Thread.sleep(3000);
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Latitude), "Geolocation_Latitude is NOT displayed again!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Geolocation_Longitude), "Geolocation_Longitude is NOT displayed again!");
	        
	        asrt.assertAll();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=3, description="checkForHorizontalSlider")
    public void checkForHorizontalSlider() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_HorizontalSliderLink), "HomePage_HorizontalSliderLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_HorizontalSliderLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HorizontalSlider_Header), "HorizontalSlider_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HorizontalSlider_Paragraph), "HorizontalSlider_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HorizontalSlider_SliderInput), "HorizontalSlider_SliderInput is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HorizontalSlider_SliderValue), "HorizontalSlider_SliderValue is NOT displayed!");

	        WebElement slider = driver.findElement(InternetHerokuAppLocators.HorizontalSlider_SliderInput);
            WebElement valueDisplay = driver.findElement(InternetHerokuAppLocators.HorizontalSlider_SliderValue);
	        
	        // Target value
            double targetValue = 3.5;
            double stepSize = 0.5;
            String finalValue = ""; Actions actions;
            int steps = (int) (targetValue / stepSize); // 3.5 / 0.5 = 7
	        
            //slider.sendKeys(Keys.TAB);
            actions = new Actions(driver);
            actions.moveToElement(slider, -5, 0).perform(); // Focuses or hovers over the element
            
            // Use arrow keys to increase slider value
            for (int i = 0; i < steps; i++) {
            	slider.sendKeys("\uE014"); 	// ARROW_RIGHT = \uE014
                Thread.sleep(2000); // Small delay to simulate user interaction
            }

            // Check and print the result
            finalValue = valueDisplay.getText();
            System.out.println("Slider moved to: " + finalValue);
            asrt.assertEquals(finalValue, Double.toString(targetValue), "targetValue is: " + targetValue + " and finalValue is: " + finalValue);
            
            //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            //	Mouse Drag Approach
            // 	Total slider range: 0 to 5 with step 0.5 → 10 steps
            // 	We'll calculate pixel offset for each step
            
            driver.navigate().refresh();
            Thread.sleep(3000);
            
            int totalSteps = 10;
            int targetSteps = (int)(targetValue / 0.5);

            //	Update slider element or else results into StaleElementException
            slider = driver.findElement(InternetHerokuAppLocators.HorizontalSlider_SliderInput);
            // Get slider width
            int sliderWidth = slider.getSize().getWidth();

            //	 Calculate pixels per step (approximate)
            int pixelsPerStep = sliderWidth / totalSteps;

            // Calculate total pixel movement needed
            int moveBy = pixelsPerStep * targetSteps;

            // Perform the drag
            actions = new Actions(driver);
            actions.moveToElement(slider, -sliderWidth/2, 0)
            		.clickAndHold()
            		.moveByOffset(moveBy , 0)
            		.release()
            		.perform();

            // Wait for value to update
            Thread.sleep(1000);

            // Print final value
            // Update value element or else results into StaleElementException
            valueDisplay = driver.findElement(InternetHerokuAppLocators.HorizontalSlider_SliderValue);
            finalValue = valueDisplay.getText();
            System.out.println("Slider moved to: " + finalValue + " Using Mouse Drag Approach");
            asrt.assertEquals(finalValue, Double.toString(targetValue), "targetValue is: " + targetValue + " and finalValue is: " + finalValue);
            
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority=4, description="checkForHovers")
    public void checkForHovers() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_HoversLink), "HomePage_HoversLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_HoversLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Hovers_Header), "Hovers_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Hovers_Paragraph), "Hovers_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Hovers_Images), "Hovers_Images is NOT displayed!");
	
	        // Get all image containers
            List<WebElement> figures = driver.findElements(InternetHerokuAppLocators.Hovers_Images);
            Actions actions = new Actions(driver);

            // Iterate over each figure
            for (int i = 0; i < figures.size(); i++) {
                WebElement figure = figures.get(i);

                // Hover over the image
                actions.moveToElement(figure).perform();
                Thread.sleep(1000); // Just to visually confirm hover (optional)

                // Get the figcaption (text on hover)
                WebElement caption = figure.findElement(By.className("figcaption"));

                // Check if it's displayed
                if (caption.isDisplayed()) {
                    String userName = caption.findElement(By.tagName("h5")).getText();
                    String profileLink = caption.findElement(By.tagName("a")).getDomAttribute("href");

                    System.out.println("Hovered on user: " + userName);
                    System.out.println("Profile link: " + profileLink);
                } else {
                    System.out.println("Hover caption not displayed for image #" + (i + 1));
                }
            }
	        
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
