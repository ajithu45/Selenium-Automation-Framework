package Utility;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v137.page.Page;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Browser_compatibility {
	static InputStream input = null;
	public static String CHROME_DRIVER = "";
	public static String Edge_DRIVER = "";
	public static String RelativePath = "";
	public static String Relativepath_shortexcel = "";
	public static String downloadpath = "";

	static Properties prop = new Properties();
	public static WebDriver driver;

	@SuppressWarnings("deprecation")
	@Test
	@Parameters("browser")
	public static WebDriver setup(String browser) throws IOException {
		PropertyConfigurator.configure("log4j.properties");
		input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);
		// Relativepath_excel=prop.getProperty("Relativepath");
		Relativepath_shortexcel = prop.getProperty("Relativepath");
		downloadpath = prop.getProperty("downloadpath");

		String Dirc = System.getProperty("user.dir");
		// downloadpath=prop.getProperty("downloadpath");
		// RelativePath=Dirc+Relativepath_excel;
//	       

		RelativePath = Dirc + Relativepath_shortexcel;

		System.out.println(RelativePath);
		// Check if parameter passed from TestNG is 'edge'
		if (browser.equalsIgnoreCase("edge")) {
		    Edge_DRIVER = prop.getProperty("edgedriver");
		   

		    System.setProperty("webdriver.edge.driver", RelativePath + Edge_DRIVER);
		    Log.info("Get the path where eclipse is installed");

		    driver = new EdgeDriver();
		    Log.info("EdgeDriver is initiated");
		}
		if (browser.equalsIgnoreCase("edge")) {
		    Edge_DRIVER = prop.getProperty("edgedriver");
		   

		    System.setProperty("webdriver.edge.driver", RelativePath + Edge_DRIVER);
		    Log.info("Get the path where eclipse is installed");

		    driver = new EdgeDriver();
		    Log.info("EdgeDriver is initiated");
		}
		else if (browser.equalsIgnoreCase("headless-edge")) {
		    Edge_DRIVER = prop.getProperty("edgedriver");

		    System.setProperty("webdriver.edge.driver", RelativePath + Edge_DRIVER);
		    Log.info("Get the path where eclipse is installed");

		    // Configure EdgeOptions for headless mode
		    org.openqa.selenium.edge.EdgeOptions options = new org.openqa.selenium.edge.EdgeOptions();
		    options.addArguments("--headless=new");
		    options.addArguments("--disable-gpu");
		    options.addArguments("--window-size=1920,1080");
		    options.addArguments("--force-device-scale-factor=1");
		    options.addArguments("--no-sandbox");
		    options.addArguments("--disable-dev-shm-usage");

		    // Optional: disable download prompts and popups
		    Map<String, Object> prefs = new HashMap<>();
		    prefs.put("download.prompt_for_download", false);
		    prefs.put("profile.default_content_settings.popups", 0);
		    prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
		    options.setExperimentalOption("prefs", prefs);

		    driver = new EdgeDriver(options);
		    Log.info("Headless EdgeDriver is initiated");

		    // Optional: emulate screen metrics
		    try {
		        if (driver instanceof org.openqa.selenium.chromium.ChromiumDriver) {
		            Map<String, Object> metrics = new HashMap<>();
		            metrics.put("width", 1920);
		            metrics.put("height", 1080);
		            metrics.put("deviceScaleFactor", 1);
		            metrics.put("mobile", false);
		            ((org.openqa.selenium.chromium.ChromiumDriver) driver)
		                .executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
		        }
		    } catch (Exception e) {
		        Log.warn("Failed to apply screen emulation in headless Edge: " + e.getMessage());
		    }
		}


		// Check if parameter passed as 'chrome'
		else if (browser.equalsIgnoreCase("chrome")) {
			CHROME_DRIVER = prop.getProperty("chromedriver");
			// driver access//
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			System.setProperty("webdriver.chrome.driver", RelativePath + CHROME_DRIVER);
			Log.info("Get the path where eclipse is installed");
//			driver = new ChromeDriver();
			Log.info("ChromeDriver is initiated");
			Map<String, Object> prefs = new HashMap<>();
//		        prefs.put("download.default_directory", downloadPath);
			prefs.put("download.prompt_for_download", false); // Disable download prompt
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("headless-chrome")) {
			
			CHROME_DRIVER = prop.getProperty("chromedriver");
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			System.setProperty("webdriver.chrome.driver", RelativePath + CHROME_DRIVER);
			 ChromeOptions options = new ChromeOptions();
		        options.addArguments("--headless=new");
		        options.addArguments("--disable-gpu");
		        options.addArguments("--window-size=1920,1080");
		        options.addArguments("--force-device-scale-factor=1");
		        options.addArguments("--no-sandbox");
		        options.addArguments("--disable-dev-shm-usage");
		        driver = new ChromeDriver(options);
		        // ✅ Fix: emulate full HD screen in headless mode
		        Map<String, Object> metrics = new HashMap<>();
		        metrics.put("width", 1920);
		        metrics.put("height", 1080);
		        metrics.put("deviceScaleFactor", 1);
		        metrics.put("mobile", false);
		        
		        Map<String, Object> prefs = new HashMap<>();
//		        prefs.put("download.default_directory", downloadPath);
			prefs.put("download.prompt_for_download", false); // Disable download prompt
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
			options.setExperimentalOption("prefs", prefs);

		        ((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
		}

//				}
		else {
			// If no browser passed throw exception
			Log.info("Browser is not correct");
		}
		return driver;
	}
}