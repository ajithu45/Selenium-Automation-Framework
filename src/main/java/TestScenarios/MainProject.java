package TestScenarios ;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

// Application.extent_report_test;

import Utility.ConfigurationInputdata;
import Utility.ExcelUtils;
import Utility.Log;
import iRetry.UnifiedExtentTestNGListener;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentTest;

public class MainProject 
{
	
	public static void SampleMethod(WebDriver driver) throws Exception 
	{
	var test = UnifiedExtentTestNGListener.getCurrentTest();

	//	driver.get(ConfigurationInputdata.URL);
		driver.get(ConfigurationInputdata.URL);
	    test.info("Google Search Opened Successfully");

		UnifiedExtentTestNGListener.screenshot_store(driver);
		
		//driver.get("www.google.com");
		
		
		
	}
}
