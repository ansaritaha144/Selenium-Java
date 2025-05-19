package com.github.project.SeleniumJava;

import java.net.URI;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/*
 * A/B Testing
 * Add/Remove Elements
 * Basic Auth (user and pass: admin)
 * Broken Images
 * Challenging DOM
 */

public class Links_01_To_05 extends InternetHerokuAppBase{

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @Test(priority=1, description="checkHomeUI")
    public void checkHomeUI() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            List<WebElement> listElements = driver.findElements(InternetHerokuAppLocators.HomePage_LinkListElements);
            System.out.println("No. of Available Links are : " + listElements.size());
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
    
    @Test(priority=2, description="checkBrokenImages")
    public void checkBrokenImages() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_BrokenImagesLink), "HomePage_BrokenImagesLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_BrokenImagesLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.BrokenImages_Header), "BrokenImages_Header is NOT displayed!");

            List<WebElement> imageElements = driver.findElements(InternetHerokuAppLocators.BrokenImages_ImageThumbnails);
            System.out.println("Total IMG Elements: " + imageElements.size());

            for (WebElement img : imageElements) {
                String src = img.getAttribute("src");

                if (src == null || src.isEmpty()) {
                    System.out.println("Image with missing src.");
                    continue;
                }

                try {
                    // Convert relative URLs to absolute
                    URI baseUrl = new URI(driver.getCurrentUrl());
                    URI resolvedUri = baseUrl.resolve(src);
                    String imageUrl = resolvedUri.toString();

                    Response response = given()
                        .relaxedHTTPSValidation()
                        .when()
                        .get(imageUrl);

                    if (response.statusCode() != 200) {
                        System.out.println("Broken image: " + src + " | Status Code: " + response.statusCode());
                    }

                } catch (Exception e) {
                    System.out.println("Error checking image: " + src + " | Exception: " + e.getMessage());
                }
            }

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test(priority=3, description="checkChallengingDOM")
    public void checkChallengingDOM() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ChallengingDOMLink), "HomePage_ChallengingDOMLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_ChallengingDOMLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ChallengingDOM_Header), "ChallengingDOM_Header is NOT displayed!");

            // 1. Click the first (top-left) button - Blue Button
            String blueButtonTextBeforeClick = getText(InternetHerokuAppLocators.ChallengingDOM_BlueButton);
            System.out.println("blueButtonTextBeforeClick: " + blueButtonTextBeforeClick);
            clickWhenClickable(InternetHerokuAppLocators.ChallengingDOM_BlueButton);
            Thread.sleep(5000);
            String blueButtonTextAfterClick = getText(InternetHerokuAppLocators.ChallengingDOM_BlueButton);
            System.out.println("blueButtonTextAfterClick: " + blueButtonTextAfterClick);
            asrt.assertNotEquals(blueButtonTextBeforeClick, blueButtonTextAfterClick, "blueButtonTextBeforeClick AND blueButtonTextAfterClick Are Same!");

            // 2. Extract table data
            WebElement table = driver.findElement(InternetHerokuAppLocators.ChallengingDOM_Table);
            List<WebElement> headers = table.findElements(By.xpath(".//thead/tr/th"));
            List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

            // Print headers
            System.out.println("\nTable Headers:");
            for(WebElement header : headers) {
                System.out.print(header.getText() + "\t");
            }

            // Print table rows
            System.out.println("\n\nTable Data:");
            for(WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for(WebElement cell : cells) {
                    System.out.print(cell.getText() + "\t");
                }
                System.out.println();
            }

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
