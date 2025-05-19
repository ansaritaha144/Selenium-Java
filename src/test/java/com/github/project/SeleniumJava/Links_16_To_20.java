package com.github.project.SeleniumJava;

import java.awt.Robot;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

public class Links_16_To_20 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
	
    @Test(priority=0, description="checkForExitIntent")
    public void checkForExitIntent() {
        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ExitIntentLink), "HomePage_ExitIntentLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_ExitIntentLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ExitIntent_Header), "ExitIntent_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ExitIntent_Paragraph), "ExitIntent_Paragraph is NOT displayed!");

            Thread.sleep(1000); // slight pause

            // Move mouse into browser window first
            Robot robot = new Robot();
            robot.mouseMove(600, 400); // somewhere inside browser
            Thread.sleep(2000);

            // Then move cursor out of the window (top of the screen)
            robot.mouseMove(600, 0); // simulate exiting at the top edge

            // Wait for modal to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.EntryAd_ModalWindow));

            if (modal.isDisplayed()) {
                System.out.println("Exit Intent modal appeared!");
            }

            // Optional: Close the modal
            WebElement closeButton = modal.findElement(By.cssSelector(".modal-footer p"));
            closeButton.click();

            // Wait for modal to disappear
            wait.until(ExpectedConditions.invisibilityOf(modal));
            System.out.println("Modal closed successfully.");

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ExitIntent_Header), "ExitIntent_Header is NOT displayed AGAIN!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ExitIntent_Paragraph), "ExitIntent_Paragraph is NOT displayed AGAIN!");

            asrt.assertAll();
            
//            | Use Case                         | Solution                                  |
//            | --------------------------------| ----------------------------------------- |
//            | Real browser interaction        | ✅ `Robot` mouseMove                      |
//            | Headless mode or CI testing     | ✅ JavaScript `dispatchEvent`            |
//            | Accurate UX simulation          | ✅ `Robot`                                |
//
//            Actions actions = new Actions(driver);
//            actions.moveByOffset(0, 0).perform(); // top-left
//            actions.moveByOffset(0, -100).perform(); // this will throw MoveTargetOutOfBoundsException
//
//            ❌ This fails if the offset goes outside the browser  
//            ❌ Not reliable for exit-intent triggers
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=1, description="checkFileDownload")
    public void checkFileDownload() {
        try {
            SoftAssert asrt = new SoftAssert();

            driver.quit();

            // Set your download folder path
            String downloadFilepath = System.getProperty("user.dir") + File.separator + "downloads";

            // Create download folder if it doesn't exist
            File downloadDir = new File(downloadFilepath);
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }

            if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
                // Set Chrome preferences
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadFilepath);
                prefs.put("download.prompt_for_download", false);
                prefs.put("download.directory_upgrade", true);
                prefs.put("safebrowsing.enabled", true); // avoid Safe Browsing blocking

                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", prefs);

                // Launch Chrome with configured options
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().window().fullscreen();
            }
            else if(config.getProperty("browser").equalsIgnoreCase("firefox")) {

                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.folderList", 2); // use custom location
                profile.setPreference("browser.download.dir", downloadFilepath);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,text/plain"); // add MIME types
                profile.setPreference("pdfjs.disabled", true); // disable PDF preview

                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);

                driver = new FirefoxDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));    
                driver.manage().window().fullscreen();
                
            } else if(config.getProperty("browser").equalsIgnoreCase("edge") || config.getProperty("browser").equalsIgnoreCase("msedge")) {

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadFilepath);
                prefs.put("download.prompt_for_download", false);
                prefs.put("safebrowsing.enabled", true);

                EdgeOptions options = new EdgeOptions();
                options.setExperimentalOption("prefs", prefs);

                driver = new EdgeDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().window().fullscreen();

            } else if(config.getProperty("browser").equalsIgnoreCase("headless")) {

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadFilepath);
                prefs.put("download.prompt_for_download", false);
                prefs.put("safebrowsing.enabled", true);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new"); // use the modern headless mode
                options.addArguments("--window-size=1920,1080");
                options.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(options);

                // Enable downloads in headless mode using DevTools
                // Enable headless download with unversioned CDP
                DevTools devTools = ((HasDevTools) driver).getDevTools();
                devTools.createSession();

                Map<String, Object> params = new HashMap<>();
                params.put("behavior", "allow");
                params.put("downloadPath", downloadFilepath);
                ((HasCdp) driver).executeCdpCommand("Page.setDownloadBehavior", params);
            }

            driver.get(data.getProperty("baseURL_" + productName));
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FileDownloadLink), "HomePage_FileDownloadLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_FileDownloadLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileDownload_Header), "FileDownload_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileDownload_DownloadLinks), "FileDownload_DownloadLinks is NOT displayed!");

            // Click the first file in the list (you can filter by extension if needed)
            WebElement firstFile = driver.findElement(InternetHerokuAppLocators.FileDownload_DownloadLinks);
            String fileName = firstFile.getText();
            firstFile.click();

            // Wait a bit to allow download to complete (or use smarter file polling)
            // Thread.sleep(3000);

            // Wait loop: max 20 seconds
            File targetFile = new File(downloadFilepath + File.separator + fileName);
            File tempFile = new File(downloadFilepath + File.separator + fileName + ".crdownload");

            int attempts = 0;
            int maxAttempts = 40; // 40 x 500ms = 20 seconds

            while ((tempFile.exists() || !targetFile.exists()) && attempts < maxAttempts) {
                Thread.sleep(500);
                attempts++;
            }

            if (targetFile.exists()) {
                System.out.println("✅ File downloaded successfully: " + targetFile.getAbsolutePath());
            } else {
                System.out.println("❌ File download failed or timed out.");
            }

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @Test(priority=2, description="checkForFileUpload")
    public void checkForFileUpload() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FileUploadLink), "HomePage_FileUploadLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_FileUploadLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileUpload_Header), "FileUpload_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileUpload_Paragraph), "FileUpload_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileUpload_UploadInput), "FileUpload_UploadInput is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FileUpload_UploadButton), "FileUpload_UploadButton is NOT displayed!");
	
	        // Locate the file input element & Provide the full file path to upload
	        sendKeys(InternetHerokuAppLocators.FileUpload_UploadInput, System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "SampleText.txt");
	        Thread.sleep(3000);
	        
	        // Click the 'Upload' button
	        clickWhenClickable(InternetHerokuAppLocators.FileUpload_UploadButton);
	        
	        // Optional: Verify upload success
	        String uploadedMessage = getText(InternetHerokuAppLocators.FileUpload_UploadedMessage);
	        if (uploadedMessage.equalsIgnoreCase("File Uploaded!")) {
	            System.out.println("File uploaded successfully!");
	        } else {
	            System.out.println("Upload failed!");
	        }
	        Thread.sleep(3000);
	        
	        asrt.assertAll();
                        
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=3, description="checkForFloatingMenu")
    public void checkForFloatingMenu() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_FloatingMenuLink), "HomePage_FloatingMenuLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_FloatingMenuLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FloatingMenu_Header), "FloatingMenu_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FloatingMenu_Paragraph), "FloatingMenu_Paragraph is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.FloatingMenu_MenuTabs), "FloatingMenu_MenuTabs is NOT displayed!");
	
	        // Scroll down using JavaScript to simulate user scrolling
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Pause to observe the floating menu (optional)
            Thread.sleep(2000);

            // Check if floating menu is displayed
            boolean floatingMenuVisible = isElementDisplayed(InternetHerokuAppLocators.FloatingMenu_MenuTabs);
            if (floatingMenuVisible) {
                System.out.println("Floating menu is visible.");
            } else {
                System.out.println("Floating menu is not visible!");
            }

            // Click on one of the floating menu links, e.g., 'About'
            WebElement aboutLink = driver.findElement(By.linkText("About"));
            aboutLink.click();

            // Wait to observe navigation (optional)
            Thread.sleep(2000);
            
            String currentURL = driver.getCurrentUrl();
            
            if (currentURL.contains("about")) {
                System.out.println("Clicked on About link.");
			}else {
				System.out.println("NOT clicked on About link!");
            }
	        
	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority=4, description="checkForForgotPassword")
    public void checkForForgotPassword() {
        
    	try {
            
	    	SoftAssert asrt = new SoftAssert();
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ForgotPasswordLink), "HomePage_ForgotPasswordLink is NOT displayed!");
	
	        clickWhenClickable(InternetHerokuAppLocators.HomePage_ForgotPasswordLink);
	
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ForgotPassword_Header), "ForgotPassword_Header is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ForgotPassword_EmailInput), "ForgotPassword_EmailInput is NOT displayed!");
	        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ForgotPassword_RetrievePasswordButton), "ForgotPassword_RetrievePasswordButton is NOT displayed!");
	
	        // Locate the email input field and enter an email
	        sendKeys(InternetHerokuAppLocators.ForgotPassword_EmailInput, "test@example.com");

            // Click the 'Retrieve password' button
            clickWhenClickable(InternetHerokuAppLocators.ForgotPassword_RetrievePasswordButton);

            // Optional: Verify that we navigated to the correct confirmation page
            boolean confirmation = isElementDisplayed(InternetHerokuAppLocators.ForgotPassword_Header);
            if (confirmation) {
            	System.out.println("Form submitted successfully.");
            } else {
                System.out.println("Submission went through, but server responded with an error (expected on this demo site).");
            }

	        asrt.assertAll();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
