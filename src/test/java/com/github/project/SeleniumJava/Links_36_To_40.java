package com.github.project.SeleniumJava;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;
import io.restassured.response.Response;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/* 
 * Redirect Link
 * Secure File Download
 * ShadowDOM
 * Shifting Content
 * Slow Resources 
 * 
 * */


public class Links_36_To_40 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=0, description="checkForRedirectLink")
    public void checkForRedirectLink() {
        
    	try {
            
    		SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_RedirectLink), "HomePage_RedirectLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_RedirectLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.RedirectPage_Header), "RedirectPage_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.RedirectPage_Paragraph), "RedirectPage_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.RedirectPage_RedirectLink), "RedirectPage_RedirectLink is NOT displayed!");
            
            clickWhenClickable(InternetHerokuAppLocators.RedirectPage_RedirectLink);
            
            // Note: You can't add/remove elements from this list (it's fixed-size).
            List<String> statusCodes = Arrays.asList("200", "301", "404", "500");

            for (String code : statusCodes) {
				
            	WebElement webElement = driver.findElement(By.linkText(code));
            	
            	String href = webElement.getDomAttribute("href");
                
                // Convert relative URLs to absolute
            	String codeText = getText(webElement).trim();
                URI baseUrl = new URI(driver.getCurrentUrl());
                URI resolvedUri = baseUrl.resolve(href);
                String URL = resolvedUri.toString();
                
                Response response = given()
                    .relaxedHTTPSValidation()
                    .redirects().follow(false)
                    .when()
                    .get(URL);

                String location = response.getHeader("Location");
                System.out.println("First redirect to: " + location);
                
                // Step 2: Follow that redirect
                if (location != null) {
                    Response finalResponse = given()
                        .redirects().follow(false)
                    .when()
                        .get("https://the-internet.herokuapp.com" + location);

                    System.out.println("Final status: " + finalResponse.getStatusCode());
                    System.out.println("Final content: " + finalResponse.body().asString());
                }
                
                if (response.statusCode() != Integer.parseInt(codeText)) {
                    System.out.println("Current URL: " + URL + " | Status Code: " + response.statusCode());
                }else {
					System.out.println("Rest Assured Hit Successfull For: " + driver.getCurrentUrl() + " Response: " + response.getStatusCode());
				}
                
                webElement.click();
                if (!driver.getCurrentUrl().contains(codeText)) {
					asrt.assertTrue(false, "Current URL: " + driver.getCurrentUrl() + " Response: " + response.getStatusCode());
				}else {
					System.out.println("Redirection Successfull For: " + driver.getCurrentUrl() + " Response: " + response.getStatusCode());
				}
                
                clickWhenClickable(By.linkText("here"));
            	
			}
            
            asrt.assertAll();          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority=1, description="checkForSecureFileDownload")
    public void checkForSecureFileDownload() {
    	try {
    		
            SoftAssert asrt = new SoftAssert();
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_SecureFileDownloadLink), "HomePage_SecureFileDownloadLink is NOT displayed!");

            //clickWhenClickable(InternetHerokuAppLocators.HomePage_SecureFileDownloadLink);
                        
            // Step 1: Open the download page with basic auth
            String username = "admin";
            String password = "admin";
            String url = "https://" + username + ":" + password + "@the-internet.herokuapp.com/download_secure";
            driver.get(url);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SecureFileDownload_Header), "SecureFileDownload_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SecureFileDownload_FileLinks), "SecureFileDownload_FileLinks is NOT displayed!");
            
            // Step 2: Find download links
            List<WebElement> files = driver.findElements(InternetHerokuAppLocators.SecureFileDownload_FileLinks);

            System.out.println("Available files:");
            for (WebElement file : files) {
                System.out.println(file.getText());
            }

            // Step 3: Click first file to download (optional)
            if (!files.isEmpty()) {
                files.get(0).click(); // This will trigger browser download
            }
            
            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @Test(priority=2, description="checkForShadowDOM")
    public void checkForShadowDOM() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ShadowDOMLink), "HomePage_ShadowDOMLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_ShadowDOMLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShadowDOM_Header), "ShadowDOM_Header is NOT displayed!");
//
//            WebElement shadowHost = driver.findElements(By.cssSelector("my-paragraph")).get(0);
//            SearchContext shadowRoot = shadowHost.getShadowRoot();     
//            // Step 4: Find elements inside Shadow DOM
//            WebElement span = shadowRoot.findElement(By.cssSelector("span"));
//            System.out.println("Text inside <span>: " + span.getText());
//            List<WebElement> items = shadowRoot.findElements(By.cssSelector("ul > li"));
//            for (WebElement li : items) {
//                System.out.println("List item: " + li.getText());
//            }

	        // Get first shadow host
	        WebElement shadowHost = driver.findElements(By.tagName("my-paragraph")).get(0);
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        // Get the span slotted inside the shadow DOM
	        WebElement span = (WebElement) js.executeScript(
	                "let shadow = arguments[0].shadowRoot;" +
	                "let slot = shadow.querySelector('slot[name=\"my-text\"]');" +
	                "return slot.assignedNodes().find(n => n.nodeType === Node.ELEMENT_NODE);",
	                shadowHost
	        );

	        System.out.println("Span Text: " + span.getText());

	        // Get second shadow host (with <ul> instead of <span>)
	        WebElement shadowHost2 = driver.findElements(By.tagName("my-paragraph")).get(1);

	        List<WebElement> liItems = (List<WebElement>) js.executeScript(
	                "let shadow = arguments[0].shadowRoot;" +
	                "let slot = shadow.querySelector('slot[name=\"my-text\"]');" +
	                "return slot.assignedNodes().filter(n => n.nodeName === 'UL')[0].querySelectorAll('li');",
	                shadowHost2
	        );

	        for (WebElement li : liItems) {
	            System.out.println("List item: " + li.getText());
	        }
	        
			/*
			 * Inside the shadow DOM, this slot is rendered via <slot name="my-text">, which
			 * is not directly queryable using normal CSS selectors.
			 * 
			 * ❗ Problem Recap You tried this (or something similar):
			 * 
			 * WebElement span = shadowRoot.findElement(By.cssSelector("span")); But in this
			 * case, shadowRoot only has:
			 * 
			 * <p><slot name="my-text">My default text</slot></p>
			 * 
			 * Which does not include the assigned light DOM content (e.g., <span
			 * slot="my-text">...).
			 * 
			 * 🎯 Goal You want to get the slotted content inside <my-paragraph> — like the
			 * <span> or <ul> — from Selenium.
			 * 
			 * 🧠 Shadow DOM Slot Limitations ❌ Selenium cannot access the flattened slot
			 * content (projected light DOM) inside a shadow root via native getShadowRoot()
			 * or even JS directly.
			 * 
			 * This is a browser rendering behavior, not Selenium’s fault.
			 * 
			 * To access the actual slotted nodes, you must use JavaScript like this:
			 * shadowRoot.querySelector('slot[name="my-text"]').assignedNodes()
			 */
	        
	        asrt.assertAll();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=3, description="checkForShiftingContent")
    public void checkForShiftingContent() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	    	asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ShiftingContentLink), "HomePage_ShiftingContentLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_ShiftingContentLink);
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_Header), "ShiftingContent_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_Paragraph), "ShiftingContent_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_MenuLink), "ShiftingContent_MenuLink is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_ImageLink), "ShiftingContent_ImageLink is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_ListLink), "ShiftingContent_ListLink is NOT displayed!");

            //-----------------Check For Shifting Content: Menu Element-----------------------------------------------------------------------------------------------------------------------------
            clickWhenClickable(InternetHerokuAppLocators.ShiftingContent_MenuLink);
            for (int i = 1; i <= 3; i++) {
            	// Take screenshot
                File beforeScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage actualImageBefore = ImageIO.read(beforeScreen);
                
                driver.navigate().refresh();
                Thread.sleep(3000);
                
                // Take screenshot again
                File afterScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage actualImageAfter = ImageIO.read(afterScreen);
                
                // Compare
                boolean result = compareImages(actualImageBefore, actualImageAfter);

                if (result) {
                    System.out.println("✅ Images match!");
                    asrt.assertTrue(false, "Before and After Screenshot ✅ Images match! No Changes After Refresh!");
                } else {
                    System.out.println("❌ Images are different! - Menu Element Shiting");
                }    
            }
            
            driver.navigate().back();
            Thread.sleep(3000);
            
            //-----------------Check For Shifting Content: Image Element-----------------------------------------------------------------------------------------------------------------------------
            clickWhenClickable(InternetHerokuAppLocators.ShiftingContent_ImageLink);
            for (int i = 1; i <= 3; i++) {
            	// Take screenshot
                File beforeScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage actualImageBefore = ImageIO.read(beforeScreen);
                
                driver.navigate().refresh();
                Thread.sleep(3000);
                
                // Take screenshot again
                File afterScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage actualImageAfter = ImageIO.read(afterScreen);
                
                // Compare
                boolean result = compareImages(actualImageBefore, actualImageAfter);

                if (result) {
                    System.out.println("✅ Images match!");
                    asrt.assertTrue(false, "Before and After Screenshot ✅ Images match! No Changes After Refresh!");
                } else {
                    System.out.println("❌ Images are different! - For Image Shifting");
                }    
            }
            
            driver.navigate().back();
            Thread.sleep(3000);
            
            //-----------------Check For Shifting Content: List Element-----------------------------------------------------------------------------------------------------------------------------
            clickWhenClickable(InternetHerokuAppLocators.ShiftingContent_ListLink);
            for (int i = 1; i <= 3; i++) {
            	
            	WebElement div = driver.findElement(InternetHerokuAppLocators.ShiftingContent_ListItems_TextContainer);
                String fullText = div.getText(); // All content in one string
                List<String> strArrayBefore  = Arrays.stream(fullText.split("\n"))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toList());
                
                driver.navigate().refresh();
                Thread.sleep(3000);
                
                div = driver.findElement(InternetHerokuAppLocators.ShiftingContent_ListItems_TextContainer);
                fullText = div.getText(); // All content in one string
                List<String> strArrayAfter  = Arrays.stream(fullText.split("\n"))
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toList());
               
               System.out.println("1st Line Before Refresh: " + strArrayBefore.get(0) + " - " + "1st Line After Refresh: " + strArrayAfter.get(0));
               asrt.assertNotEquals(strArrayBefore.get(0), strArrayAfter.get(0), "Text is same even after page refresh!");
               
            }
            
            driver.navigate().back();
            Thread.sleep(3000);
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ShiftingContent_Header), "ShiftingContent_Header is NOT displayed Again!");
            
            asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // Basic dimension check
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        // Pixel-by-pixel check
        for (int y = 0; y < imgA.getHeight(); y++) {
            for (int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    
    @Test(priority=4, description="checkForSlowResources")
    public void checkForSlowResources() {
        
    	try {
                     
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_SlowResourcesLink), "HomePage_SlowResourcesLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_SlowResourcesLink);
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SlowResources_Header), "SlowResources_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SlowResources_Paragraph), "SlowResources_Paragraph is NOT displayed!");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
            WebElement paragraph = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.SlowResources_MessageParagraph));
            String message = paragraph.getText();
            
            asrt.assertTrue(message.contains("It takes some time"), "Expected slow-load message not found.");

            /*
             * GET request that takes 30 seconds to complete, is giving 503 error as of now, hence this test is failing.
             * 
             * */
            
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
