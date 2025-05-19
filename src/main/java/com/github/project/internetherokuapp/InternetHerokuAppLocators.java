package com.github.project.internetherokuapp;

import org.openqa.selenium.By;

public class InternetHerokuAppLocators {

	//HomePage Locators
	public static final By HomePage_WelcomeText = By.className("heading");
	public static final By HomePage_LinkList = By.xpath("//ul");
	public static final By HomePage_LinkListElements = By.xpath("//li");
	public static final By HomePage_AddRemoveElementsLink = By.partialLinkText("Add/Remove Elements");
	public static final By HomePage_BasicAuthLink = By.partialLinkText("Basic Auth");
	public static final By HomePage_BrokenImagesLink = By.partialLinkText("Broken Images");
	public static final By HomePage_ChallengingDOMLink = By.partialLinkText("Challenging DOM");
	public static final By HomePage_CheckboxesLink = By.partialLinkText("Checkboxes");
	public static final By HomePage_ContextMenuLink = By.partialLinkText("Context Menu");
	public static final By HomePage_DigestAuthenticationLink = By.partialLinkText("Digest Authentication");
	public static final By HomePage_DisappearingElementsLink = By.partialLinkText("Disappearing Elements");
	public static final By HomePage_DragDropLink = By.partialLinkText("Drag and Drop");
	public static final By HomePage_DropdownLink = By.partialLinkText("Dropdown");
	public static final By HomePage_DynamicContentLink = By.partialLinkText("Dynamic Content");
	public static final By HomePage_DynamicControlsLink = By.partialLinkText("Dynamic Controls");
	public static final By HomePage_DynamicLoadingLink = By.partialLinkText("Dynamic Loading");
	public static final By HomePage_EntryAdLink = By.partialLinkText("Entry Ad");
	public static final By HomePage_ExitIntentLink = By.partialLinkText("Exit Intent");
	public static final By HomePage_FileDownloadLink = By.partialLinkText("File Download");
	public static final By HomePage_FileUploadLink = By.partialLinkText("File Upload");
	public static final By HomePage_FloatingMenuLink = By.partialLinkText("Floating Menu");
	public static final By HomePage_ForgotPasswordLink = By.partialLinkText("Forgot Password");
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
	public static final By BrokenImages_ImageThumbnails = By.xpath("//h3[contains(text(),'Broken Images')]/following-sibling::img");

	//ChallengingDOM Page Locators
	public static final By ChallengingDOM_Header = By.xpath("//h3[contains(text(),'Challenging DOM')]");
	public static final By ChallengingDOM_Paragraph = By.xpath("//h3[contains(text(),'Challenging DOM')]/following-sibling::p");
	public static final By ChallengingDOM_BlueButton = By.cssSelector("div.example a.button:nth-of-type(1)");
	public static final By ChallengingDOM_RedButton = By.cssSelector("div.example a.button.alert");
	public static final By ChallengingDOM_GreenButton = By.xpath("div.example a.button.success");
	public static final By ChallengingDOM_Table = By.xpath("//div[@class='large-10 columns']/table");

	//Checkboxes Page Locators
	public static final By Checkboxes_Header = By.xpath("//h3[contains(text(),'Checkboxes')]");
	public static final By Checkboxes_Checkbox = By.cssSelector("form#checkboxes input[type='checkbox']");

	//ContextMenu Page Locators
	public static final By ContextMenu_Header = By.xpath("//h3[contains(text(),'Context Menu')]");
	public static final By ContextMenu_Paragraph = By.cssSelector("h3 + p"); //Following Sibling All Below Siblings Using cssSelector, Use '+' Instead For Following Adjacent Sibling (i.e. has to be right below it)
	public static final By ContextMenu_ContextArea = By.cssSelector("div#hot-spot");

	//DigestAuth Page Locators
	public static final By DigestAuthPage_Header = By.xpath("//h3[contains(text(),'Digest Auth')]");
	public static final By DigestAuthPage_SuccessMessage = By.cssSelector("h3 + p"); //Following Sibling Using cssSelector, Use '+' Instead For Following Adjacent Sibling (i.e. has to be right below it)

	//DisappearingElements Page Locators
	public static final By DisappearingElements_Header = By.xpath("//h3[contains(text(),'Disappearing Elements')]");
	public static final By DisappearingElements_Paragraph = By.cssSelector("h3 + p"); //Following Sibling Using cssSelector, Use '+' Instead For Following Adjacent Sibling (i.e. has to be right below it)
	public static final By DisappearingElements_NavMenuItems = By.cssSelector("ul li a");

	//DragDrop Page Locators
	public static final By DragDrop_Header = By.xpath("//h3[contains(text(),'Drag and Drop')]");
	public static final By DragDrop_FirstBox = By.cssSelector("div.column:nth-of-type(1)");
	public static final By DragDrop_SecondBox = By.cssSelector("div.column:nth-of-type(2)");

	//Dropdown Page Locators
	public static final By Dropdown_Header = By.xpath("//h3[contains(text(),'Dropdown List')]");
	public static final By Dropdown_DropdownElement = By.cssSelector("select#dropdown");
	public static final By Dropdown_Options = By.cssSelector("select#dropdown option");

	//DynamicContent Page Locators
	public static final By DynamicContent_Header = By.xpath("//h3[contains(text(),'Dynamic Content')]");
	public static final By DynamicContent_Paragraph = By.cssSelector("h3 + p");
	public static final By DynamicContent_RowImages = By.cssSelector("div.large-2 img");
	public static final By DynamicContent_RowTexts = By.cssSelector("div.large-10.columns");
	public static final By DynamicContent_StaticContentLink = By.cssSelector("div.example a");

	//DynamicControls Page Locators
	public static final By DynamicControls_Header = By.xpath("//h3[contains(text(),'Dynamic Content')]");
	public static final By DynamicControls_Paragraph = By.cssSelector("h4 + p");
	public static final By DynamicControls_Checkbox = By.cssSelector("input[type='checkbox']");
	public static final By DynamicControls_AddRemoveButton = By.xpath("//form[@id='checkbox-example']//button");
	public static final By DynamicControls_InputTextBox = By.cssSelector("input[type='text']");
	public static final By DynamicControls_EnableDisableButton = By.xpath("//form[@id='input-example']//button");
	public static final By DynamicControls_MessageLabel = By.cssSelector("#message");

	//DynamicLoading Page Locators
	public static final By DynamicLoading_Header = By.xpath("//h3[contains(text(),'Dynamically Loaded Page Elements')]");
	public static final By DynamicLoading_Paragraph = By.cssSelector("h3 ~ p");
	public static final By DynamicLoading_HiddenElementLink = By.cssSelector("div.example a[href*='1']");
	public static final By DynamicLoading_AfterFactElementLink = By.cssSelector("div.example a[href*='2']");
	public static final By DynamicLoading_StartButton = By.cssSelector("div#start button");
	public static final By DynamicLoading_SubHeader = By.cssSelector("h3 + h4");
	public static final By DynamicLoading_HiddenElementLabel = By.cssSelector("div#finish h4");

	//EntryAd Page Locators
	public static final By EntryAd_Header = By.xpath("//h3[contains(text(),'Entry Ad')]");
	public static final By EntryAd_Paragraph = By.cssSelector("h3 ~ p");
	public static final By EntryAd_RestartAdLink = By.cssSelector("#restart-ad");
	public static final By EntryAd_ModalWindow = By.cssSelector("div.modal");
	public static final By EntryAd_ModalWindowTitle = By.cssSelector("div.modal-title h3");
	public static final By EntryAd_ModalWindowBody = By.cssSelector("div.modal-body p");
	public static final By EntryAd_ModalWindowCloseButton = By.cssSelector("div.modal-footer p");

	//ExitIntent Page Locators
	public static final By ExitIntent_Header = By.xpath("//h3[contains(text(),'Exit Intent')]");
	public static final By ExitIntent_Paragraph = By.cssSelector("h3 ~ p");
	public static final By ExitIntent_ModalWindow = By.cssSelector("div.modal");
	public static final By ExitIntent_ModalWindowTitle = By.cssSelector("div.modal-title h3");
	public static final By ExitIntent_ModalWindowBody = By.cssSelector("div.modal-body p");
	public static final By ExitIntent_ModalWindowCloseButton = By.cssSelector("div.modal-footer p");

	//FileDownload Page Locators
	public static final By FileDownload_Header = By.xpath("//h3[contains(text(),'File Downloader')]");
	public static final By FileDownload_DownloadLinks = By.cssSelector("h3 ~ a");
	public static final By FileDownload_ModalWindow = By.cssSelector("div.modal");
	public static final By FileDownload_ModalWindowTitle = By.cssSelector("div.modal-title h3");
	public static final By FileDownload_ModalWindowBody = By.cssSelector("div.modal-body p");
	public static final By FileDownload_ModalWindowCloseButton = By.cssSelector("div.modal-footer p");

	//FileUpload Page Locators
	public static final By FileUpload_Header = By.xpath("//h3[contains(text(),'File Uploader')]");
	public static final By FileUpload_Paragraph = By.cssSelector("h3 + p");
	public static final By FileUpload_UploadInput = By.cssSelector("input#file-upload");
	public static final By FileUpload_UploadButton = By.cssSelector("input#file-submit");
	public static final By FileUpload_UploadedMessage = By.xpath("//h3[contains(text(),'File Uploaded!')]");
	public static final By FileUpload_UploadedFileName = By.cssSelector("div#uploaded-files.panel");

	//FloatingMenu Page Locators
	public static final By FloatingMenu_Header = By.xpath("//h3[contains(text(),'Floating Menu')]");
	public static final By FloatingMenu_Paragraph = By.cssSelector("div.row p");
	public static final By FloatingMenu_MenuTabs = By.cssSelector("div#menu a");
		
	//ForgotPassword Page Locators
	public static final By ForgotPassword_Header = By.xpath("//h3[contains(text(),'Forgot Password')]");
	public static final By ForgotPassword_EmailInput = By.cssSelector("input#email");
	public static final By ForgotPassword_RetrievePasswordButton = By.cssSelector("button#form_submit");
	
}
