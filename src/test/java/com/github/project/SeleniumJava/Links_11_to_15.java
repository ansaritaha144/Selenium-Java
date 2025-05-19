package com.github.project.SeleniumJava;

import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/* 
 * Dropdown
 * Dynamic Content
 * Dynamic Controls
 * Dynamic Loading
 * Entry Ad
 * 
 * */

public class Links_11_to_15 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=8, description="checkForDropdown")
    public void checkForDropdown() {

        try {

            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DropdownLink), "HomePage_DropdownLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_DropdownLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Dropdown_Header), "Dropdown_Header is NOT displayed!");

            selectByIndex(InternetHerokuAppLocators.Dropdown_DropdownElement, 1);
            Thread.sleep(3000);
            selectByValue(InternetHerokuAppLocators.Dropdown_DropdownElement, "2");
            Thread.sleep(3000);
            selectByVisibleText(InternetHerokuAppLocators.Dropdown_DropdownElement, "Option 1");
            Thread.sleep(3000);
            selectCustomDropdown(InternetHerokuAppLocators.Dropdown_DropdownElement, InternetHerokuAppLocators.Dropdown_Options, "Option 1");
            Thread.sleep(3000);
            
            asrt.assertAll();
            
        }catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test(priority=1, description="checkDynamicContent")
    public void checkDynamicContent() {

        try {

            SoftAssert asrt = new SoftAssert();

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DynamicContentLink), "HomePage_DynamicContentLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_DynamicContentLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicContent_Header), "DynamicContent_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicContent_Paragraph), "DynamicContent_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicContent_RowTexts), "DynamicContent_RowTexts is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicContent_RowImages), "DynamicContent_RowImages is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicContent_StaticContentLink), "DynamicContent_StaticContentLink is NOT displayed!");

            StringBuilder initialString = new StringBuilder();

            // Capture and print the initial content
            System.out.println("Initial Page Content:");
            List<WebElement> initialContent = driver.findElements(InternetHerokuAppLocators.DynamicContent_RowTexts);
            for (int i = 0; i < initialContent.size(); i++) {
                String currentText = initialContent.get(i).getText();
                initialString.append(currentText);
                System.out.println("Content " + (i + 1) + ": " + currentText);
            }

            // Refresh the page
            driver.navigate().refresh();
            Thread.sleep(3000);

            StringBuilder afterRefreshString = new StringBuilder();

            // Capture and print the content again after refresh
            System.out.println("\nContent After Refresh:");
            List<WebElement> refreshedContent = driver.findElements(InternetHerokuAppLocators.DynamicContent_RowTexts);
            for (int i = 0; i < refreshedContent.size(); i++) {
                String currentText = refreshedContent.get(i).getText();
                afterRefreshString.append(currentText);
                System.out.println("Content " + (i + 1) + ": " + refreshedContent.get(i).getText());
            }

            asrt.assertNotEquals(initialString, afterRefreshString, "Content Is NOT Updated After Refresh!");

            // Same Validation Can be Done For Images As Well

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=2, description="checkDynamicControls")
    public void checkDynamicControls() {

        try {

            SoftAssert asrt = new SoftAssert();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DynamicControlsLink), "HomePage_DynamicControlsLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_DynamicControlsLink);

            // --- PART 1: Checkbox ---
            // Click "Remove" button
            clickWhenClickable(InternetHerokuAppLocators.DynamicControls_AddRemoveButton);

            // Wait until checkbox disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_Checkbox));

            // Wait for message "It's gone!"
            WebElement messageGone = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_MessageLabel));
            System.out.println("Checkbox message: " + messageGone.getText());

            // Click "Add" button
            clickWhenClickable(InternetHerokuAppLocators.DynamicControls_AddRemoveButton);

            // Wait until checkbox appears
            wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_Checkbox));

            // Wait for message "It's back!"
            WebElement messageBack = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_MessageLabel));
            System.out.println("Checkbox message after add: " + messageBack.getText());

            // --- PART 2: Text Input ---
            clickWhenClickable(InternetHerokuAppLocators.DynamicControls_EnableDisableButton);

            // Wait until input is enabled
            wait.until(ExpectedConditions.elementToBeClickable(InternetHerokuAppLocators.DynamicControls_InputTextBox));
            sendKeys(InternetHerokuAppLocators.DynamicControls_InputTextBox, "Selenium works!");

            // Wait for message "It's enabled!"
            WebElement enabledMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_MessageLabel));
            System.out.println("Input message: " + enabledMessage.getText());

            // Click "Disable" button
            clickWhenClickable(InternetHerokuAppLocators.DynamicControls_EnableDisableButton);

            // Wait until input is disabled
            wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(InternetHerokuAppLocators.DynamicControls_InputTextBox)));

            // Wait for message "It's disabled!"
            WebElement disabledMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicControls_MessageLabel));
            System.out.println("Input message after disable: " + disabledMessage.getText());

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=3, description="checkDynamicLoading")
    public void checkDynamicLoading() {

        try {

            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DynamicLoadingLink), "HomePage_DynamicLoadingLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_DynamicLoadingLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicLoading_Header), "DynamicLoading_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicLoading_Paragraph), "DynamicLoading_Paragraph is NOT displayed!");

            // Click on Hidden Element Link
            clickWhenClickable(InternetHerokuAppLocators.DynamicLoading_HiddenElementLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicLoading_SubHeader), "DynamicLoading_SubHeader is NOT displayed!");

            // Click the Start button
            clickWhenClickable(InternetHerokuAppLocators.DynamicLoading_StartButton);

            // Wait until the loading finishes and element is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement helloElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicLoading_HiddenElementLabel)
            );

            // Print the text
            System.out.println("Hidden text: " + helloElement.getText());

            // Back to Dynamic Loading Link Page
            driver.navigate().back();

            // Click on After Fact Element Link
            clickWhenClickable(InternetHerokuAppLocators.DynamicLoading_AfterFactElementLink);
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DynamicLoading_SubHeader), "DynamicLoading_SubHeader is NOT displayed!");

            // Click the Start button
            clickWhenClickable(InternetHerokuAppLocators.DynamicLoading_StartButton);

            // Wait until the loading finishes and element is visible
            helloElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.DynamicLoading_HiddenElementLabel)
            );

            // Print the text
            System.out.println("Loaded text: " + helloElement.getText());

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=4, description="checkEntryAd")
    public void checkEntryAd() {
        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_EntryAdLink), "HomePage_EntryAdLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_EntryAdLink);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.EntryAd_ModalWindow)
            );

            // Optional: Print modal title or body text
            String modalTitle = modal.findElement(By.className("modal-title")).getText();
            String modalBody = modal.findElement(By.className("modal-body")).getText();
            System.out.println("Modal title: " + modalTitle);
            System.out.println("Modal body: " + modalBody);

            // Click the Close button
            WebElement closeButton = modal.findElement(By.cssSelector(".modal-footer p"));
            closeButton.click();

            // Wait until the modal disappears
            wait.until(ExpectedConditions.invisibilityOf(modal));
            System.out.println("Modal closed successfully.");

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.EntryAd_Header), "EntryAd_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.EntryAd_Paragraph), "EntryAd_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.EntryAd_RestartAdLink), "EntryAd_RestartAdLink is NOT displayed!");

            // Define FluentWait
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))		// max wait time
                .pollingEvery(Duration.ofSeconds(1))		// check every second
                .ignoring(NoSuchElementException.class);	// ignore if found

            // Wait until target element appears while clicking
            boolean targetElementFound = fluentWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        WebElement restartAd = driver.findElement(InternetHerokuAppLocators.EntryAd_RestartAdLink);
                        js.executeScript("arguments[0].click();", restartAd);
                        
                    try {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(InternetHerokuAppLocators.EntryAd_ModalWindow));
                        return isElementDisplayed(InternetHerokuAppLocators.EntryAd_ModalWindow);
                    } catch (Exception e) {
                        return false;
                    }
                }
            });

            if (targetElementFound) {
                System.out.println("Modal reopened successfully!");
                asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.EntryAd_ModalWindow), "EntryAd_ModalWindow is NOT displayed!");
                clickWhenClickable(InternetHerokuAppLocators.EntryAd_ModalWindowCloseButton);
                System.out.println("Modal closed successfully again!");
            } else {
                System.out.println("Modal Window NOT opened even after trying for 30 seconds!");
            }

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
}
