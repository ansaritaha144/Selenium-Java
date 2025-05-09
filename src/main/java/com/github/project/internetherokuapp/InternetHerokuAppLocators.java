package com.github.project.internetherokuapp;

import org.openqa.selenium.By;

public class InternetHerokuAppLocators {

	//HomePage Locators
	public static final By HomePage_WelcomeText = By.className("heading");
	public static final By HomePage_LinkList = By.xpath("//ul");
	public static final By HomePage_LinkListElements = By.xpath("//li");
	public static final By HomePage_AddRemoveElementsLink = By.partialLinkText("Add/Remove Elements");
	public static final By HomePage_BasicAuthLink = By.partialLinkText("Basic Auth");
	public static final By HomePage_FooterLink = By.xpath("//div[@id='page-footer']//div/a");
	
	//AddRemove Page Locators
	public static final By AddRemovePage_Header = By.xpath("//h3[contains(text(),'Add/Remove Elements')]");
	public static final By AddRemovePage_AddElementButton = By.xpath("//button[contains(text(),'Add Element')]");
	public static final By AddRemovePage_DeleteElementButton = By.className("added-manually");

	//BasicAuth Page Locators
	public static final By BasicAuthPage_Header = By.xpath("//h3[contains(text(),'Basic Auth')]");
	public static final By BasicAuthPage_SuccessMessage = By.xpath("//p[contains(text(),'Congratulations!')]");

	//BrokenImages Page Locators
	public static final By BrokenImages_Header = By.xpath("//h3[contains(text(),'Broken Images')]");

	
	
	
	
}
