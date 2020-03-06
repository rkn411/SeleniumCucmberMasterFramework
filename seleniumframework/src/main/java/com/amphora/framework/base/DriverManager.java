package com.amphora.framework.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.amphora.framework.support.io.PropertiesFile;
import com.amphora.framework.utilities.UtilityMethods;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Manager browser launch and kill activities
 * Load property file and launch browser based on programmer choice given properties file
 * Kills browser for completion of every class execution
 *
 */
public class DriverManager {
	private static WebDriver driver;
	public static String browser;

	public static String baseDir = System.getProperty("user.dir");
	//private String driversPath = baseDir + "/drivers/";
	
	public static PropertiesFile propFile;
	
	@BeforeTest
	public void beforeTest() {
		String path=baseDir + "/src/main/java/com/amphora/";
		propFile=new PropertiesFile(path + UtilityMethods.getChildDirectoryName(path) + "/config/config.properties");
	}
	
	/**
	* This function launches browser based on browser name given in config properties file.
	*/
	@BeforeClass
	public void launchBrowser() {
		browser = propFile.getProperty("BrowserName");

		switch (browser.toLowerCase()) {
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options=new ChromeOptions();
			//options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome_proxy.exe");
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
		    options.addArguments("ignore-certificate-errors");
		    options.setAcceptInsecureCerts(true);
		    options.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			Assert.assertTrue(false, "Invalid Choice, plese give valid browser name");
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(propFile.getProperty("URL"));
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/**
	* This function closes all browser drivers.
	*/
	@AfterClass
	public static void closeDriver() {
		if (driver != null) {
			try {
				//driver.quit();
			} catch (NoSuchMethodError nsme) {
				nsme.printStackTrace();
			} catch (NoSuchSessionException nsse) {
				nsse.printStackTrace();
			} catch (SessionNotCreatedException snce) {
				snce.printStackTrace();
			}
			driver = null;
		}
	}
	
	/**
	* Gets driver instance
	* @return webdriver instance
	*/
	public static WebDriver getDriver() {
		return driver;
	}
}
