package com.github.project.SeleniumJava;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/*
 * Checkboxes
 * Context Menu
 * Digest Authentication (user and pass: admin)
 * Disappearing Elements
 * Drag and Drop
 */

public class Links_06_To_10 extends InternetHerokuAppBase {

	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=0, description="checkForCheckboxes")
    public void checkForCheckboxes() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_CheckboxesLink), "HomePage_CheckboxesLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_CheckboxesLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.Checkboxes_Header), "Checkboxes_Header is NOT displayed!");

            List<WebElement> checkboxes = driver.findElements(InternetHerokuAppLocators.Checkboxes_Checkbox);

            for (WebElement checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    System.out.println("Unchecking the checkbox!");
                    checkbox.click();
                } else {
                    System.out.println("Checking the checkbox!");
                    checkbox.click();
                }
            }

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=1, description="checkContextMenu")
    public void checkContextMenu() {

        try {
            SoftAssert asrt = new SoftAssert();

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_ContextMenuLink), "HomePage_ContextMenuLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_ContextMenuLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ContextMenu_Header), "ContextMenu_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ContextMenu_Paragraph), "ContextMenu_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.ContextMenu_ContextArea), "ContextMenu_ContextArea is NOT displayed!");

            // Locate the box element
            WebElement box = driver.findElement(InternetHerokuAppLocators.ContextMenu_ContextArea);

            // Right-click on the box using Actions class
            Actions actions = new Actions(driver);
            actions.contextClick(box).perform();

            Thread.sleep(2000);

            // Switch to alert and get the message
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());

            // Accept the alert
            alert.accept();
            Thread.sleep(2000);

            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=2, description="checkDigestAuth")
    public void checkDigestAuth() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DigestAuthenticationLink), "HomePage_DigestAuthenticationLink is NOT displayed!");

            // Digest Auth credentials
            String user = "admin";
            String pass = "admin";

            // This line tells REST Assured to skip SSL certificate validation
            RestAssured.useRelaxedHTTPSValidation();

            // Make digest-auth request
            Response response = RestAssured.given()
                .auth().digest(user, pass)
                .when()
                .get("https://the-internet.herokuapp.com/digest_auth");

            System.out.println("Status Code: " + response.getStatusCode());
            // System.out.println("Response: " + response.body().asString());

            if (response.getStatusCode() == 200) {
                System.out.println("Digest Auth Success ✅");
            } else {
                System.out.println("Digest Auth Failed ❌");
            }

            // Get session cookie/cookies
            String sessionId = response.getCookie("rack.session"); // or whatever cookie the server sets
            System.out.println("Session ID: " + sessionId);
            System.out.println(response.getCookies().toString());

            // Inject session cookie into browser
            Cookie cookie = new Cookie("rack.session", sessionId);
            driver.manage().addCookie(cookie);

            // Attempt to open the page with the session cookie
            driver.get("https://the-internet.herokuapp.com/digest_auth");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DigestAuthPage_Header), "DigestAuthPage_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DigestAuthPage_SuccessMessage), "DigestAuthPage_SuccessMessage is NOT displayed!");

            /*
            ⚠️ Explanation Note:
            Digest Authentication **cannot be carried over** from REST Assured to Selenium.

            ✔ Why?
              - Digest Auth is **per-connection**, **not cookie-based**.
              - Browsers (e.g., Chrome) handle digest auth **internally**.
              - REST Assured does it programmatically, while Selenium spins up a **separate browser process**.
              - The page `https://the-internet.herokuapp.com/digest_auth` doesn't use reusable session cookies.

           */
            
            // even after doing digest auth using rest assured still it is not opening the page
            /* You're absolutely right to expect that—but here's the key limitation:

            🔴 Digest Authentication cannot be "carried over" from REST Assured to Selenium.

            Even if you successfully authenticate via REST Assured and get a 200 OK response, that session or authentication state is not shared with Selenium, because:

            ❌ Why it Doesn't Work
            -> Digest Auth is per-connection, not cookie-based.

            -> Browsers (like Chrome) handle digest authentication internally; you can't inject digest auth state from outside.

            -> REST Assured authenticates the request programmatically, but Selenium opens a separate browser process which will again prompt for digest auth.

            -> This specific site (https://the-internet.herokuapp.com/digest_auth) doesn't use session cookies or tokens you can pass between clients.
            */


            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority=3, description="checkDisappearingReappearingElements")
    public void checkDisappearingReappearingElements() {

        try {
            SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DisappearingElementsLink), "HomePage_DisappearingElementsLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_DisappearingElementsLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DisappearingElements_Header), "DisappearingElements_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DisappearingElements_Paragraph), "DisappearingElements_Paragraph is NOT displayed!");

            // Try to find all menu items
            List<WebElement> menuItems = driver.findElements(InternetHerokuAppLocators.DisappearingElements_NavMenuItems);

            // Optionally, print menu items
            System.out.println("\nNavigation Menu Items:");
            for (WebElement item : menuItems) {
                System.out.println(" - " + item.getText());
            }
            
            // Optional: Keep refreshing until "Gallery" appears
            boolean galleryFound = false;
            int attempts = 0;

            while (!galleryFound && attempts < 10) {
                driver.navigate().refresh();
                Thread.sleep(2000);
                menuItems = driver.findElements(InternetHerokuAppLocators.DisappearingElements_NavMenuItems);

                for (WebElement item : menuItems) {
                    if (item.getText().equals("Gallery")) {
                        galleryFound = true;
                        break;
                    }
                }
                attempts++;
            }

            if (galleryFound) {
                System.out.println("✔ Gallery appeared after " + attempts + " refreshes.");
            } else {
                System.out.println("✘ Gallery did not appear after 10 attempts.");
            }
            
            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority=4, description="checkDragAndDrop")
    public void checkDragAndDrop() {

    try {

        SoftAssert asrt = new SoftAssert();
        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_DragDropLink), "HomePage_DragDropLink is NOT displayed!");

        clickWhenClickable(InternetHerokuAppLocators.HomePage_DragDropLink);

        asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.DragDrop_Header), "DragDrop_Header is NOT displayed!");

        // WebElement on which drag and drop operation needs to be performed
        WebElement firstBox = driver.findElement(InternetHerokuAppLocators.DragDrop_FirstBox);
        // WebElement to which the above object is dropped
        WebElement secondBox = driver.findElement(InternetHerokuAppLocators.DragDrop_SecondBox);

        String firstBoxText_BeforeDragAndDrop = firstBox.getText().trim();
        String secondBoxText_BeforeDragAndDrop = secondBox.getText().trim();
        System.out.println("firstBoxText_BeforeDragAndDrop: " + firstBoxText_BeforeDragAndDrop);
        System.out.println("secondBoxText_BeforeDragAndDrop: " + secondBoxText_BeforeDragAndDrop);

        // Creating object of Actions class to build composite actions
        Actions builder = new Actions(driver);
        // Building a drag and drop action
        Action dragAndDrop = builder.clickAndHold(firstBox)
                                    .moveToElement(secondBox)
                                    .release(secondBox)
                                    .build();
        // Performing the drag and drop action
        dragAndDrop.perform();

        Thread.sleep(3000);

        String firstBoxText_AfterDragAndDrop = firstBox.getText().trim();
        String secondBoxText_AfterDragAndDrop = secondBox.getText().trim();
        System.out.println("firstBoxText_AfterDragAndDrop: " + firstBoxText_AfterDragAndDrop);
        System.out.println("secondBoxText_AfterDragAndDrop: " + secondBoxText_AfterDragAndDrop);

        asrt.assertNotEquals(firstBoxText_BeforeDragAndDrop, firstBoxText_AfterDragAndDrop, "First Box Text Is Same Even After Drag And Drop!");
        asrt.assertNotEquals(secondBoxText_BeforeDragAndDrop, secondBoxText_AfterDragAndDrop, "Second Box Text Is Same Even After Drag And Drop!");

        asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
