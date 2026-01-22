package TestScenarios;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

//import Application.extent_report_test;
import Utility.ConfigurationInputdata;
import Utility.ExcelUtils;
import Utility.Log;
import iRetry.UnifiedExtentTestNGListener;

public class Login_PageFunctions {
	

	@Test
	public static void LoginFunction(WebDriver driver) throws Exception {
		var test = UnifiedExtentTestNGListener.getCurrentTest();
		try {
			ExcelUtils.setExcelFile(ConfigurationInputdata.Excel_Relative_Path + ConfigurationInputdata.File_TestData,"Automation");
			int rowcount = ExcelUtils.getFiledDataRowCount();
 
			for (int i = 1; i <= rowcount; i++)
 
			{
 				driver.get(ConfigurationInputdata.URL);
				PageObjects.CommonMethods.documentready(driver);
				Thread.sleep(1000);
				driver.manage().window().maximize();
				//Application.extent_report_test.screenshot_store(driver);
				test.pass("URL Launched Successfully");
				break;
 			}
 
		} catch (StaleElementReferenceException e) 
		{
			String excp = e.getMessage();
			Log.error(e);
			test.fail("URL Lunch Failed");
			test.fail(excp);
			 throw new RuntimeException(e);
		}
	}
	
	
}
