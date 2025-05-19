package com.github.project.SeleniumJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static Properties env = null;
	public static Properties config = null;
	public static Properties data = null;
	private static boolean isInitalized = false;
	public static Logger log = null;
	public static WebDriver driver = null;
	protected WebDriverWait wait = null;
	private static FileInputStream ip = null;
	public String productName = "";
	public static long startTime;
	public static long endTime;

	protected TestBase() {
		if (!isInitalized) {
			initConfig();
		}
	}

	private static void initConfig() {
		if (config == null) {
			try {
				// Initialize config.properties File
				config = new Properties();
				String config_fileName = "config.properties";
				String config_path = System.getProperty("user.dir") + File.separator + "config" + File.separator
						+ config_fileName;
				FileInputStream config_ip = new FileInputStream(config_path);
				config.load(config_ip);

				// Initialize data.properties File
				data = new Properties();
				String data_fileName = "data.properties";
				String data_path = System.getProperty("user.dir") + File.separator + "config" + File.separator
						+ data_fileName;
				FileInputStream data_ip = new FileInputStream(data_path);
				data.load(data_ip);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public WebDriver initializeDriver() throws Exception {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//config//config.properties");
		prop.load(fis);

		// Retrieving the "browser" property from Command Terminal OR else from Global
		// webdata.properties Using ternary operator
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {

			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();

			if (browserName.contains("chromeheadless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900)); // For Full Screen Mode, Effective In Headless
																		// Mode
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		} else if (browserName.equalsIgnoreCase("chromeHeadless")) {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless"); // 👈 Run Chrome in headless mode
			options.addArguments("--disable-gpu"); // 👈 Recommended for Windows systems
			options.addArguments("--window-size=1920,1080"); // 👈 Optional: sets screen size
			options.addArguments("--disable-notifications"); // 👈 Optional: disable popups
			options.addArguments("--no-sandbox"); // 👈 Optional: for Docker/Linux environments
			options.addArguments("--disable-dev-shm-usage"); // 👈 Optional: helps with memory issues

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("firefoxHeadless")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless"); // 👈 Run Chrome in headless mode
			options.addArguments("--disable-gpu"); // 👈 Recommended for Windows systems
			options.addArguments("--window-size=1920,1080"); // 👈 Optional: sets screen size
			options.addArguments("--disable-notifications"); // 👈 Optional: disable popups
			options.addArguments("--no-sandbox"); // 👈 Optional: for Docker/Linux environments
			options.addArguments("--disable-dev-shm-usage"); // 👈 Optional: helps with memory issues

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(options);

		} else if (browserName.equalsIgnoreCase("phantomjs")) {

//        	Ah, excellent and very important question! Here's the deal:
//
//        	👉 PhantomJS is officially NOT supported in Selenium 4.0.
//
//        	Why?
//        	PhantomJS is a headless browser that was very popular back when Selenium 2 and 3 were around.
//
//        	But development of PhantomJS stopped around 2018.
//
//        	Meanwhile, Chrome and Firefox added built-in headless modes (without needing a separate browser like PhantomJS).
//
//        	Selenium 4 is designed to work only with actively maintained browsers and W3C WebDriver compliant drivers.
//        	→ PhantomJS never fully implemented W3C WebDriver — it was using older protocols (JSONWire).
//
//        	So, in Selenium 4:
//        	No native PhantomJS driver support.
//
//        	PhantomJSDriver class is removed or deprecated.
//
//        	You cannot run your Selenium 4 scripts directly on PhantomJS without hacks (and it's not recommended anyway).
//        	
//        	
			// PhantomJS is a headless browser that is primarily used for GUI less
			// automation.
//            DesiredCapabilities caps = new DesiredCapabilities();
//            caps.setCapability("takeScreenshot", true);
//            String OS_Name = System.getProperty("os.name");
//            if (OS_Name.equalsIgnoreCase("Linux")) {
//                //caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//                //System.getProperty("user.dir") + File.separator + "webdrivers" + File.separator + "Linux"
//                //+ File.separator + "phantomjs");
//            } else if (OS_Name.contains("Windows")) {
//                //caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//                //System.getProperty("user.dir") + File.separator + "webdrivers" + File.separator + "Windows"
//                //+ File.separator + "phantomjs.exe");
//            } else if (OS_Name.contains("Mac")) {
//                //caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
//                //System.getProperty("user.dir") + File.separator + "webdrivers" + File.separator + "Mac"
//                //+ File.separator + "phantomjs");
//            }
//            driver = new PhantomJSDriver(caps);

		} else if (browserName.contains("grid")) {

			DesiredCapabilities caps = null;

			if (browserName.equalsIgnoreCase("gridchrome")) {
				caps = new DesiredCapabilities();
				caps.setBrowserName("chrome");
				caps.setAcceptInsecureCerts(true);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("--disable-notifications");
				caps.setCapability(ChromeOptions.CAPABILITY, options);
			}

			if (browserName.contains("gridfirefox")) {
				caps = new DesiredCapabilities();
				caps.setBrowserName("firefox");
				caps.setAcceptInsecureCerts(true);
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--disable-notifications");
				caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
			}

			if (browserName.contains("gridedge")) {
				caps = new DesiredCapabilities();
				caps.setBrowserName("edge");
				caps.setAcceptInsecureCerts(true);
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--disable-notifications");
				caps.setCapability(EdgeOptions.CAPABILITY, options);
			}

			driver = new RemoteWebDriver(new URL(config.getProperty("Grid_URL")), caps);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			//driver.manage().window().setSize(new Dimension(1920, 1080));
			//driver.manage().window().fullscreen();
			
		}
		return driver;
	}

	public List<HashMap<String, String>> getJSONDataToMap(String filePath) throws Exception {

		// Read JSON To String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String To HashMap Using Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver, String ssSavepath) throws Exception { 
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs (OutputType.FILE);
		File file = new File(ssSavepath);
		FileUtils.copyFile(source, file);
		return ssSavepath;
	}


	/**
	* Take Screenshot on failure.
	*
	* @return
	*/
	protected void getScreenshot() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		String url = driver.getCurrentUrl().replaceAll("[\\/:*\\?\">\\|]", "_"); 
		String ext = ".png";

		String path = getScreenshotSavePath() + File.separator + date + "_" + url + ext;
		try {
			if (driver instanceof TakesScreenshot) {
				File tmpFile = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
				org.openqa.selenium.io.FileHandler.copy(tmpFile, new File(path));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	* Define path for Screenshot file.
	*
	* @return
	*/
	protected String getScreenshotSavePath() {
		
		String packageName = this.getClass().getPackage().getName();
		File dir = new File(System.getProperty("user.dir") + File.separator + "screenshot" + File.separator + packageName + File.separator);
		dir.mkdirs();
		return dir.getAbsolutePath();
		
	}
	
	/**
	* Read properties.
	*
	* @return
	*/
	protected static String getPropertyValue(String propertyKey) {
		
		Properties props = null;
		FileInputStream fin = null;
		String propertyValue = null;
		
		try {
			File f = new File(System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties");
			fin = new FileInputStream(f);
			props = new Properties();
			props.load(fin);
			propertyValue = props.getProperty(propertyKey);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return propertyValue;
	}
	
	
	/**
     * Waits for the presence of an element located by the given locator.
     */
    public WebElement waitForElementPresence(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits for an element to be visible.
     */
    public WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable.
     */
    public WebElement waitForElementClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for a specific text to appear in a web element.
     */
    public boolean waitForTextInElement(By locator, String text, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Fluent wait with custom polling and exception ignoring.
     */
    public WebElement fluentWait(final By locator, int timeoutInSeconds, int pollingInMillis) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return wait.until(driver -> driver.findElement(locator));
    }
	
    /**
     * Waits for the element to be clickable and clicks it.
     */
    public void clickWhenClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    /**
     * Safe click with fallback to JavaScript click if standard click fails.
     */
    public void safeClick(By locator) {
        try {
            clickWhenClickable(locator);
        } catch (ElementNotInteractableException e) {
            // Fallback: JavaScript click
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * JavaScript click directly (use with caution).
     */
    public void javascriptClick(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
	
	
    /**
     * Checks if an element is displayed on the page within a given timeout.
     */
    public boolean isElementDisplayed(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an already located element is displayed.
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
	
    /**
     * Presses Enter on the element located by the given locator.
     */
    public void pressEnter(By locator) {
        WebElement element = driver.findElement(locator);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Presses Enter on a provided WebElement.
     */
    public void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }
	
    /**
     * Navigate to a specific URL.
     */
    public void goToUrl(String url) {
        driver.navigate().to(url);
    }

    /**
     * Navigate back in browser history.
     */
    public void goBack() {
        driver.navigate().back();
    }

    /**
     * Navigate forward in browser history.
     */
    public void goForward() {
        driver.navigate().forward();
    }

    /**
     * Refreshes the current page.
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Get the current URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get the current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Checks if an element is enabled using a locator with timeout.
     */
    public boolean isElementEnabled(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if a provided WebElement is enabled.
     */
    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Returns a single WebElement after waiting for its presence.
     */
    public WebElement getElement(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + locator);
            return null;
        }
    }

    /**
     * Returns a list of WebElements after waiting for their presence.
     */
    public List<WebElement> getElements(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            return driver.findElements(locator);
        } catch (NoSuchElementException e) {
            System.err.println("Elements not found: " + locator);
            return Collections.emptyList();
        }
    }
    
    /**
     * Clears an input field located by the given locator, with wait.
     */
    public void clearField(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
        } catch (Exception e) {
            System.err.println("Standard clear failed. Attempting JS clear: " + e.getMessage());
            clearFieldWithJS(locator);
        }
    }

    /**
     * Clears an input field using JavaScript (as a fallback).
     */
    public void clearFieldWithJS(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
        } catch (Exception e) {
            System.err.println("JavaScript clear also failed: " + e.getMessage());
        }
    }

    /**
     * Clears a given WebElement safely.
     */
    public void clearField(WebElement element) {
        try {
            element.clear();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
        }
    }
    
    
    /**
     * Gets an attribute value from an element using a locator and timeout.
     */
    public String getAttribute(By locator, String attributeName, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            System.err.println("Failed to get attribute: " + attributeName + " from " + locator);
            return null;
        }
    }

    /**
     * Gets an attribute value from a WebElement directly.
     */
    public String getAttribute(WebElement element, String attributeName) {
        try {
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            System.err.println("Failed to get attribute from WebElement: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets the visible text of an element located by a locator with a wait.
     */
    public String getText(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            System.err.println("Error retrieving text: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the visible text of a given WebElement.
     */
    public String getText(WebElement element) {
        try {
            return element.getText();
        } catch (StaleElementReferenceException e) {
            System.err.println("Error: Element is stale, unable to retrieve text.");
            return null;
        } catch (Exception e) {
            System.err.println("Error retrieving text: " + e.getMessage());
            return null;
        }
    }
	
    /**
     * Sends keys to an input element located by the given locator with wait.
     * Clears the field before sending keys.
     */
    public void sendKeys(By locator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();  // Clear the field before typing
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Error sending keys: " + e.getMessage());
        }
    }

    /**
     * Sends keys to a given WebElement.
     * Clears the field before sending keys.
     */
    public void sendKeys(WebElement element, String text) {
        try {
            element.clear();  // Clear the field before typing
            element.sendKeys(text);
        } catch (StaleElementReferenceException e) {
            System.err.println("Error: Element is stale, unable to send keys.");
        } catch (Exception e) {
            System.err.println("Error sending keys: " + e.getMessage());
        }
    }

    /**
     * Sends keys to an element and optionally presses the Enter key after the text.
     */
    public void sendKeysWithEnter(By locator, String text, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.sendKeys(text + Keys.ENTER);
        } catch (Exception e) {
            System.err.println("Error sending keys with Enter: " + e.getMessage());
        }
    }
	
    /**
     * Select an option by visible text from a standard <select> dropdown.
     */
    public void selectByVisibleText(By locator, String visibleText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
        } catch (Exception e) {
            System.err.println("Error selecting by visible text: " + e.getMessage());
        }
    }

    /**
     * Select an option by index from a standard <select> dropdown.
     */
    public void selectByIndex(By locator, int index) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            System.err.println("Error selecting by index: " + e.getMessage());
        }
    }

    /**
     * Select an option by value from a standard <select> dropdown.
     */
    public void selectByValue(By locator, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            System.err.println("Error selecting by value: " + e.getMessage());
        }
    }

    /**
     * Deselect all selected options in a multi-select dropdown.
     */
    public void deselectAll(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            dropdown.deselectAll();
        } catch (Exception e) {
            System.err.println("Error deselecting all: " + e.getMessage());
        }
    }

    /**
     * Get all options in a standard <select> dropdown.
     */
    public List<WebElement> getAllOptions(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            return dropdown.getOptions();
        } catch (Exception e) {
            System.err.println("Error retrieving options: " + e.getMessage());
            return null;
        }
    }

    /**
     * Check if a dropdown is a multi-select.
     */
    public boolean isMultiSelect(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Select dropdown = new Select(dropdownElement);
            return dropdown.isMultiple();
        } catch (Exception e) {
            System.err.println("Error checking multi-select: " + e.getMessage());
            return false;
        }
    }

    /**
     * Select an option in a custom dropdown (non-<select>) by visible text.
     */
    public void selectCustomDropdown(By dropdownLocator, By optionLocator, String optionText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            dropdownElement.click();  // Open the dropdown
            
            // Wait for options to be visible
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionLocator));
            
            // Find and select the option
            for (WebElement option : options) {
                if (option.getText().equals(optionText)) {
                    option.click();
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error selecting from custom dropdown: " + e.getMessage());
        }
    }
    
    
    /**
     * Select a date from a date picker (e.g., <input type="date"> or custom date pickers).
     */
    public void selectDate(By datePickerLocator, String date, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement datePickerElement = wait.until(ExpectedConditions.elementToBeClickable(datePickerLocator));
            datePickerElement.click();  // Open the date picker

            // Parse the date and select it
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("d MMM yyyy"));

            // Locate the specific date element and click it
            List<WebElement> days = driver.findElements(By.xpath("//td[@class='day' or @data-date]"));
            for (WebElement day : days) {
                if (day.getText().equals(String.valueOf(localDate.getDayOfMonth()))) {
                    day.click();
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Error selecting date: " + e.getMessage());
        }
    }

    /**
     * Select a date by navigating through months in the calendar.
     * This method works for more complex date pickers like those built with <div>, <span>, etc.
     */
    public void selectDateWithNavigation(By datePickerLocator, String date, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement datePickerElement = wait.until(ExpectedConditions.elementToBeClickable(datePickerLocator));
            datePickerElement.click();  // Open the date picker

            LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String targetMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

            // Wait until the current month is visible
            WebElement monthYearElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("datepicker-switch")));
            String currentMonthYear = monthYearElement.getText();

            // Navigate to the desired month
            while (!currentMonthYear.equals(targetMonthYear)) {
                WebElement nextButton = driver.findElement(By.cssSelector(".next"));
                nextButton.click();
                currentMonthYear = monthYearElement.getText();
            }

            // Now select the day from the calendar
            List<WebElement> days = driver.findElements(By.xpath("//td[@class='day' or @data-date]"));
            for (WebElement day : days) {
                if (day.getText().equals(String.valueOf(targetDate.getDayOfMonth()))) {
                    day.click();
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error navigating and selecting date: " + e.getMessage());
        }
    }

    /**
     * Get the currently selected date from a date picker.
     */
    public String getSelectedDate(By datePickerLocator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement datePickerElement = wait.until(ExpectedConditions.elementToBeClickable(datePickerLocator));
            return datePickerElement.getAttribute("value");  // Return the value attribute which contains the date
        } catch (Exception e) {
            System.err.println("Error getting selected date: " + e.getMessage());
            return null;
        }
    }

    /**
     * Select a date that is a specific number of days from the current date.
     */
    public void selectDateByOffset(By datePickerLocator, int daysFromToday, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            WebElement datePickerElement = wait.until(ExpectedConditions.elementToBeClickable(datePickerLocator));
            datePickerElement.click();  // Open the date picker

            LocalDate today = LocalDate.now();
            LocalDate targetDate = today.plus(daysFromToday, ChronoUnit.DAYS);
            selectDate(datePickerLocator, targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), timeoutInSeconds);
        } catch (Exception e) {
            System.err.println("Error selecting date by offset: " + e.getMessage());
        }
    }
    
    
    /**
     * Returns the current date in the specified format.
     * Example format: "yyyy-MM-dd", "dd/MM/yyyy", "MMM dd, yyyy"
     */
    public static String getCurrentDate(String format) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return currentDate.format(formatter);
    }

    /**
     * Returns the current date in default format yyyy-MM-dd.
     */
    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }
    
    
    /**
     * Waits for alert to be present and accepts it.
     */
    public void acceptAlert(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.err.println("No alert found to accept: " + e.getMessage());
        }
    }

    /**
     * Waits for alert to be present and dismisses it.
     */
    public void dismissAlert(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            System.err.println("No alert found to dismiss: " + e.getMessage());
        }
    }

    /**
     * Waits for alert and returns its text.
     */
    public String getAlertText(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            return alert.getText();
        } catch (NoAlertPresentException e) {
            System.err.println("No alert found to get text: " + e.getMessage());
            return null;
        }
    }

    /**
     * Sends text to a JavaScript prompt alert.
     */
    public void sendTextToAlert(String text, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.err.println("No alert found to send text: " + e.getMessage());
        }
    }

    /**
     * Checks if an alert is present without interacting.
     */
    public boolean isAlertPresent(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Performs a double-click on the given WebElement.
     */
    public void doubleClickElement(By locator) {
    	
    	WebElement element = getElement(locator, 10);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }
    
    /**
     * Scroll to a specific WebElement on the page.
     */
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Scroll down by a number of pixels.
     */
    public void scrollDownByPixels(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ")");
    }

    /**
     * Scroll up by a number of pixels.
     */
    public void scrollUpByPixels(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-" + pixels + ")");
    }

    /**
     * Scroll to the bottom of the page.
     */
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Scroll to the top of the page.
     */
    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }
    
    /**
     * Performs a mouse hover over the given WebElement.
     */
    public void hoverOverElement(By locator) {
    	WebElement element = getElement(locator, 10);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
    
    /**
     * Sends an arrow key to a given element.
     */
    public void sendArrowKey(WebElement element, Keys arrowKey) {
        element.sendKeys(arrowKey);
    }

    /**
     * Sends an arrow key to the active element using Actions.
     */
    public void pressArrowKey(Keys arrowKey) {
        Actions actions = new Actions(driver);
        actions.sendKeys(arrowKey).perform();
    }
    
    
    /**
     * Play the JW Player.
     */
    public void play() {
        executeJS("jwplayer().play();");
    }

    /**
     * Pause the JW Player.
     */
    public void pause() {
        executeJS("jwplayer().pause();");
    }

    /**
     * Mute the JW Player.
     */
    public void mute() {
        executeJS("jwplayer().setMute(true);");
    }

    /**
     * Unmute the JW Player.
     */
    public void unmute() {
        executeJS("jwplayer().setMute(false);");
    }

    /**
     * Seek to a specific time in the video (in seconds).
     */
    public void seek(int seconds) {
        executeJS("jwplayer().seek(" + seconds + ");");
    }

    /**
     * Get the current playback time in seconds.
     */
    public long getCurrentTime() {
        Object result = executeJSReturn("return jwplayer().getPosition();");
        return result instanceof Number ? ((Number) result).longValue() : -1;
    }

    /**
     * Get the total duration of the video in seconds.
     */
    public long getDuration() {
        Object result = executeJSReturn("return jwplayer().getDuration();");
        return result instanceof Number ? ((Number) result).longValue() : -1;
    }

    /**
     * Helper method to run JavaScript.
     */
    private void executeJS(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }

    /**
     * Helper method to run JavaScript and return a value.
     */
    private Object executeJSReturn(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }
    
    /**
     * Copies a file from source to destination. Overwrites if the destination exists.
     */
    public static void copyFile(String sourcePath, String destinationPath) {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        try {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied from " + sourcePath + " to " + destinationPath);
        } catch (IOException e) {
            System.err.println("File copy failed: " + e.getMessage());
        }
    }
    
    /**
     * Copies a folder (and its entire contents) from source to destination.
     */
    public static void copyFolder(String sourceDir, String destinationDir) {
        Path sourcePath = Paths.get(sourceDir);
        Path destinationPath = Paths.get(destinationDir);

        try {
            Files.walk(sourcePath).forEach(source -> {
                try {
                    Path target = destinationPath.resolve(sourcePath.relativize(source));
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to copy: " + source + " → " + e.getMessage());
                }
            });
            System.out.println("Folder copied successfully.");
        } catch (IOException e) {
            System.err.println("Error copying folder: " + e.getMessage());
        }
    }
    
    public String getStackTrace(Throwable t) {
    	
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	t.printStackTrace(pw);
    	return sw.toString();
    }
    
    public String generateMobileNumber() {
    	
    	Random rand = new Random();
    	
    	//Ensure the first digit is between 7 and 9
    	int firstDigit = rand.nextInt(3) + 7;
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(firstDigit);
    	
    	//Generate the remaining 9 digits
    	for(int i=0; i<9; i++) {
    		sb.append(rand.nextInt(10));
    	}
    	
    	return sb.toString();
    }

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		
	}
    
}
