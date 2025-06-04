package com.github.project.SeleniumJava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.github.project.internetherokuapp.InternetHerokuAppBase;
import com.github.project.internetherokuapp.InternetHerokuAppLocators;


/* 
 * Sortable Data Tables
 * Typos 
 * 
 * */

public class Links_41_To_42 extends InternetHerokuAppBase{
	
	@BeforeClass
    public void resetPage() {
        driver.get(data.getProperty("baseURL_" + productName));
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(data.getProperty("baseURL_" + productName));
    }
    
    @Test(priority=0, description="checkForSortableDataTables")
    public void checkForSortableDataTables() {
        
    	try {
            
    		SoftAssert asrt = new SoftAssert();
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_SortableDataTablesLink), "HomePage_RedirectLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_SortableDataTablesLink);

            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SortableDataTables_Header), "SortableDataTables_Header is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SortableDataTables_Paragraph), "SortableDataTables_Paragraph is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SortableDataTables_Table1), "SortableDataTables_Table1 is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.SortableDataTables_Table2), "SortableDataTables_Table2 is NOT displayed!");

            List<Map<String, String>> tableData = getTableData(InternetHerokuAppLocators.SortableDataTables_Table1);
            
            for (Map<String, String> row : tableData) {
                System.out.println("First Name: " + row.get("First Name"));
                System.out.println("Last Name: " + row.get("Last Name"));
                System.out.println("Email: " + row.get("Email"));
                System.out.println("Due: " + row.get("Due"));
                System.out.println("-----");
            }
            
            tableData = getTableData(InternetHerokuAppLocators.SortableDataTables_Table2);
            
            for (Map<String, String> row : tableData) {
                System.out.println("First Name: " + row.get("First Name"));
                System.out.println("Last Name: " + row.get("Last Name"));
                System.out.println("Email: " + row.get("Email"));
                System.out.println("Due: " + row.get("Due"));
                System.out.println("-----");
            }
            
            asrt.assertAll();          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Map<String, String>> getTableData(By tableId) {
    	
        List<Map<String, String>> tableData = new ArrayList<>();

        WebElement table = driver.findElement(tableId);

        // Extract headers
        List<WebElement> headers = table.findElements(By.cssSelector("thead th"));
        List<String> headerNames = new ArrayList<>();
        for (WebElement header : headers) {
            headerNames.add(header.getText().trim());
        }

        // Extract rows
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
        for (WebElement row : rows) {
            Map<String, String> rowData = new HashMap<>();
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (int i = 0; i < headerNames.size(); i++) {
                rowData.put(headerNames.get(i), cells.get(i).getText().trim());
            }
            tableData.add(rowData);
        }

        return tableData;
    }
    
    
    @Test(priority=1, description="checkForTypos")
    public void checkForTypos() {
    	try {
    		
            SoftAssert asrt = new SoftAssert();
            
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_WelcomeText), "HomePage_WelcomeText is NOT displayed!");
            asrt.assertTrue(isElementDisplayed(InternetHerokuAppLocators.HomePage_TyposLink), "HomePage_TyposLink is NOT displayed!");

            clickWhenClickable(InternetHerokuAppLocators.HomePage_TyposLink);
            
            String actualText = getText(InternetHerokuAppLocators.Typos_Paragraph);
            String expectedText = "Sometimes you'll see a typo, other times you won't.";

            System.out.println("Extracted Text: " + actualText);

            if (!actualText.equals(expectedText)) {
                System.out.println("⚠️ Typo Detected!");
            }

            asrt.assertEquals(actualText, expectedText, "Text has a typo or was randomly changed.");
                        
            asrt.assertAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	
}
