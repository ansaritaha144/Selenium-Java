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
	public static final By HomePage_FormAuthenticationLink = By.partialLinkText("Form Authentication");
	public static final By HomePage_FramesLink = By.partialLinkText("Frames");
	public static final By HomePage_GeolocationLink = By.partialLinkText("Geolocation");
	public static final By HomePage_HorizontalSliderLink = By.partialLinkText("Horizontal Slider");
	public static final By HomePage_HoversLink = By.partialLinkText("Hovers");
	public static final By HomePage_InputsLink = By.partialLinkText("Inputs");
	public static final By HomePage_InfiniteScrollLink = By.partialLinkText("Infinite Scroll");
	public static final By HomePage_JQueryUIMenusLink = By.partialLinkText("JQuery UI Menus");
	public static final By HomePage_JavascriptAlertsLink = By.partialLinkText("JavaScript Alerts");
	public static final By HomePage_JavascriptOnloadEventErrorLink = By.partialLinkText("JavaScript onload event error");
	public static final By HomePage_KeyPressesLink = By.partialLinkText("Key Presses");
	public static final By HomePage_LargeAndDeepDOMLink = By.partialLinkText("Large & Deep DOM");
	public static final By HomePage_MultipleWindowsLink = By.partialLinkText("Multiple Windows");
	public static final By HomePage_NestedFramesLink = By.partialLinkText("Nested Frames");
	public static final By HomePage_NotificationMessagesLink = By.partialLinkText("Notification Messages");
	public static final By HomePage_RedirectLink = By.partialLinkText("Redirect Link");
	public static final By HomePage_SecureFileDownloadLink = By.partialLinkText("Secure File Download");
	public static final By HomePage_ShadowDOMLink = By.partialLinkText("Shadow DOM");
	public static final By HomePage_ShiftingContentLink = By.partialLinkText("Shifting Content");
	public static final By HomePage_SlowResourcesLink = By.partialLinkText("Slow Resources");
	public static final By HomePage_SortableDataTablesLink = By.partialLinkText("Sortable Data Tables");
	public static final By HomePage_TyposLink = By.partialLinkText("Typos");

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
	
	//FormAuthentication Page Locators
	public static final By FormAuthentication_Header = By.xpath("//h2[contains(text(),'Login Page')]");
	public static final By FormAuthentication_Paragraph = By.cssSelector("h4.subheader");
	public static final By FormAuthentication_UsernameInput = By.cssSelector("input#username");
	public static final By FormAuthentication_PasswordInput = By.cssSelector("input#password");
	public static final By FormAuthentication_LoginButton = By.cssSelector("button.radius");
	public static final By FormAuthentication_SecureAreaHeader = By.cssSelector("//h2[contains(text(),'Secure Area')]");
	public static final By FormAuthentication_SecureAreaParagraph = By.cssSelector("h4.subheader");
	public static final By FormAuthentication_SecureAreaLogoutButton = By.cssSelector("a.secondary.radius");

	//Frames Page Locators
	public static final By Frames_Header = By.xpath("//h3[contains(text(),'Frames')]");
	public static final By Frames_NestedFramesLink = By.partialLinkText("Nested Frames");
	public static final By Frames_iFrameLink = By.partialLinkText("iFrame");
	public static final By Frames_NestedFrames_LeftFrame = By.cssSelector("frame[name='frame-left']");
	public static final By Frames_NestedFrames_RightFrame = By.cssSelector("frame[name='frame-right']");
	public static final By Frames_NestedFrames_MiddleFrame = By.cssSelector("frame[name='frame-middle']");
	public static final By Frames_NestedFrames_BottomFrame = By.cssSelector("frame[name='frame-bottom']");
	public static final By Frames_NestedFrames_TopFrame = By.cssSelector("frame[name='frame-top']");
	public static final By Frames_Editor_iFrame = By.id("mce_0_ifr");
	public static final By Frames_Editor = By.id("tinymce");
	
	//GeolocationPage Locators
	public static final By Geolocation_Header = By.xpath("//h3[contains(text(),'Geolocation')]");
	public static final By Geolocation_Paragraph = By.cssSelector("h3 + p");
	public static final By Geolocation_WhereAmI_Button = By.cssSelector("h3 ~ button");
	public static final By Geolocation_Latitude = By.cssSelector("div#lat-value");
	public static final By Geolocation_Longitude = By.cssSelector("div#long-value");
	public static final By Geolocation_SeeItOnGoogle_Link = By.cssSelector("div#map-link a");

	//HorizontalSliderPage Locators
	public static final By HorizontalSlider_Header = By.xpath("//h3[contains(text(),'Horizontal Slider')]");
	public static final By HorizontalSlider_Paragraph = By.cssSelector("h4.subheader");
	public static final By HorizontalSlider_SliderInput = By.cssSelector("input[type='range']");
	public static final By HorizontalSlider_SliderValue = By.cssSelector("span#range");

	//HoversPage Locators 
	public static final By Hovers_Header = By.xpath("//h3[contains(text(),'Hovers')]");
	public static final By Hovers_Paragraph = By.cssSelector("h3 + p");
	public static final By Hovers_Images = By.cssSelector("div.figure");

	//InfiniteScrollPage Locators	
	public static final By InfiniteScroll_Header = By.xpath("//h3[contains(text(),'Infinite Scroll')]");
	public static final By InfiniteScroll_Paragraph = By.cssSelector("div.jscroll-added");

	//InputsPage Locators
	public static final By Inputs_Header = By.xpath("//h3[contains(text(),'Inputs')]");
	public static final By Inputs_Paragraph = By.cssSelector("div.example p");
	public static final By Inputs_InputBox = By.cssSelector("input[type=number]");

	//JQueryUIMenusPage Locators
	public static final By JQueryUIMenus_Header = By.xpath("//h3[contains(text(),'JQueryUI - Menu')]");
	public static final By JQueryUIMenus_Paragraph = By.cssSelector("div.description p");
	public static final By JQueryUIMenus_DisabledMenu = By.cssSelector("li#ui-id-1.ui-state-disabled.ui-menu-item");
	public static final By JQueryUIMenus_EnabledMenu = By.cssSelector("li#ui-id-3.ui-menu-item");
	public static final By JQueryUIMenus_DownloadsMenu = By.cssSelector("li#ui-id-4.ui-menu-item");
	public static final By JQueryUIMenus_BackToJQueryUI_Option = By.cssSelector("li#ui-id-8.ui-menu-item");
	public static final By JQueryUIMenus_PDFOption = By.cssSelector("li#ui-id-5.ui-menu-item");
	public static final By JQueryUIMenus_CSVOption = By.cssSelector("li#ui-id-6.ui-menu-item");
	public static final By JQueryUIMenus_ExcelOption = By.cssSelector("li#ui-id-7.ui-menu-item");
	public static final By JQueryUIPage_Header = By.xpath("//h3[contains(text(),'JQuery UI')]");
	public static final By JQueryUIPage_Paragraph = By.cssSelector("div.description p");
	public static final By JQueryUIPage_MenuLink = By.linkText("Menu");
	public static final By JQueryUIPage_JQueryOfficialLink = By.linkText("JQuery UI");

	//JavascriptAlertsPage Locators
	public static final By JavascriptAlerts_Header = By.xpath("//h3[contains(text(),'JavaScript Alerts')]");
	public static final By JavascriptAlerts_Paragraph = By.cssSelector("h3 + p");
	public static final By JavascriptAlerts_JSAlert_Button = By.xpath("//button[contains(text(),'Click for JS Alert')]");
	public static final By JavascriptAlerts_JSConfirm_Button = By.xpath("//button[contains(text(),'Click for JS Confirm')]");
	public static final By JavascriptAlerts_JSPrompt_Button = By.xpath("//button[contains(text(),'Click for JS Prompt')]");
	public static final By JavascriptAlerts_ResultText = By.cssSelector("p#result");
	
	//JavaScriptOnloadEventErrorPage Locators
	public static final By JavaScriptOnloadEventError_Parapraph = By.cssSelector("body p");

	//KeyPressesPage Locators
	public static final By KeyPresses_Header = By.xpath("//h3[contains(text(),'Key Presses')]");
	public static final By KeyPresses_Paragraph = By.cssSelector("h3 + p");
	public static final By KeyPresses_InputBox = By.cssSelector("input#target");
	public static final By KeyPresses_ResultText = By.cssSelector("p#result");
	
	//LargeAndDeepDOMLinkPage Locators
	public static final By LargeAndDeepDOM_Header = By.xpath("//h3[contains(text(),'Large & Deep DOM')]");
	public static final By LargeAndDeepDOM_Paragraph = By.cssSelector("h3 + p");
	public static final By LargeAndDeepDOM_Siblings = By.cssSelector("div#siblings");
	public static final By LargeAndDeepDOM_Table = By.cssSelector("table#large-table");
	
	//MultipleWindowsPage Locators
	public static final By MultipleWindows_Header = By.xpath("//h3[contains(text(),'Opening a new window')]");
	public static final By MultipleWindows_NewWindowLink = By.linkText("Click Here");
	public static final By MultipleWindows_NewWindowHeader = By.xpath("//h3[contains(text(),'New Window')]");
	
	//NestedFramePage Locators
	public static final By NestedFrames_LeftFrame = By.cssSelector("frameset frame[name='frame-left']");
	public static final By NestedFrames_RightFrame = By.cssSelector("frameset frame[name='frame-right']");
	public static final By NestedFrames_MiddleFrame = By.cssSelector("frameset frame[name='frame-middle']");
	public static final By NestedFrames_BottomFrame = By.cssSelector("frame[name='frame-bottom']");
	public static final By NestedFrames_TopFrame = By.cssSelector("frame[name='frame-top']");

	//NotificationMessagesPage Locators
	public static final By NotificationMessages_Header = By.xpath("//h3[contains(text(),'Notification Message')]");
	public static final By NotificationMessages_Paragraph = By.cssSelector("h3 + p");
	public static final By NotificationMessages_NewNotificationLink = By.cssSelector("h3 + p a");
	public static final By NotificationMessages_NotificationText = By.cssSelector("div#flash");
	public static final By NotificationMessages_NotificationCloseButton = By.cssSelector("div#flash a");

	//RedirectPage Locators
	public static final By RedirectPage_Header = By.xpath("//h3[contains(text(),'Redirection')]");
	public static final By RedirectPage_Paragraph = By.cssSelector("h3 + p");
	public static final By RedirectPage_RedirectLink = By.cssSelector("a[href='redirect']");
	public static final By RedirectPage_StatusCodes_Header = By.xpath("//h3[contains(text(),'Status Codes')]");
	public static final By RedirectPage_StatusCodes_Paragraph = By.cssSelector("h3 + p");
	public static final By RedirectPage_StatusCodes_Link = By.cssSelector("h3 ~ ul a");
	public static final By RedirectPage_StatusCodes_200_Link = By.cssSelector("a[href*='200']");
	public static final By RedirectPage_StatusCodes_301_Link = By.cssSelector("a[href*='301']");
	public static final By RedirectPage_StatusCodes_404_Link = By.cssSelector("a[href*='404']");
	public static final By RedirectPage_StatusCodes_500_Link = By.cssSelector("a[href*='500']");

	//SecureFileDownloadPage Locators
	public static final By SecureFileDownload_Header = By.xpath("//h3[contains(text(),'Secure File Downloader')]");
	public static final By SecureFileDownload_FileLinks = By.cssSelector("h3 ~ a");

	//ShadowDOMPage Locators
	public static final By ShadowDOM_Header = By.xpath("//h1[contains(text(),'Simple template')]");

	//ShiftingContentPage Locators
	public static final By ShiftingContent_Header = By.xpath("//h3[contains(text(),'Shifting Content')]");
	public static final By ShiftingContent_Paragraph = By.cssSelector("h3 + p");
	public static final By ShiftingContent_MenuLink = By.partialLinkText("Menu");
	public static final By ShiftingContent_ImageLink = By.partialLinkText("image");
	public static final By ShiftingContent_ListLink = By.partialLinkText("List");
	public static final By ShiftingContent_MenuItems = By.cssSelector("ul li a");
	public static final By ShiftingContent_ListItems_TextContainer = By.cssSelector("div.large-6");

	//SlowResourcesPage Locators
	public static final By SlowResources_Header = By.xpath("//h3[contains(text(),'Slow Resources')]");
	public static final By SlowResources_Paragraph = By.cssSelector("h3 + p");
	public static final By SlowResources_MessageParagraph = By.cssSelector("div.example p");

	//SortableDataTablesPage Locators
	public static final By SortableDataTables_Header = By.xpath("//h3[contains(text(),'Data Tables')]");
	public static final By SortableDataTables_Paragraph = By.cssSelector("h3 + p");
	public static final By SortableDataTables_Table1 = By.cssSelector("table#table1");
	public static final By SortableDataTables_Table2 = By.cssSelector("table#table2");
	
	//TyposPage Locators
	public static final By Typos_Header = By.xpath("//h3[contains(text(),'Data Tables')]");
	public static final By Typos_Paragraph = By.cssSelector("h3 ~ p:nth-of-type(2)");

	
	
	
	
}
