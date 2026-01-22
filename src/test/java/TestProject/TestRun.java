package TestProject;

import java.lang.reflect.Method;
import java.time.Duration;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentTest;

import Utility.ConfigurationInputdata;
import Utility.Log;
import iRetry.UnifiedExtentTestNGListener;
//import Application.extent_report_test;

@Listeners(iRetry.UnifiedExtentTestNGListener.class)

public class TestRun {
   public   WebDriver driver;
 
    
    @BeforeClass
      @Parameters({ "browser" })
      public  void Load_browser(@Optional("chrome")String browser) throws Exception {
    	
          driver = Utility.Browser_compatibility.setup(browser);
          PropertyConfigurator.configure("log4j.properties");
          Log.startTestCase("Classification_Test");
          Utility.ConfigurationInputdata.Setup();
         
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
          Thread.sleep(2000);
          
       
      }
    @BeforeSuite
    public void setupReport() {
        // ✅ Use the class name as report name
        String className = this.getClass().getSimpleName();
        UnifiedExtentTestNGListener.setForcedReportName(className);
    }

  
    @Test(priority = 1)
 
  
    public void SampleTest() throws Exception{
    	
        try {
        	
        	        	
        	TestScenarios.MainProject.SampleMethod(driver);
        	
        } catch (Exception e) {
        	
        	  throw new RuntimeException("Retry triggered due to failure: " + e.getMessage(), e);
        } finally {
        	 logMemoryUsage();
        }
    }

 
    @AfterClass
    public void tearDown() {
        try {
        	
          
            if (driver != null) {
                driver.close();
            }
        } catch (Exception e) {
            Log.error("Error during test teardown: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up static/global variables
        	if (driver != null) {
        	    driver.quit();
        	    driver = null;
        	}
            Utility.ConfigurationInputdata.clean();
          
            
        }
    }
    public void logMemoryUsage() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        long beforeGC = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Before GC - Used Memory: " + beforeGC + " bytes");

        System.gc(); // Suggest GC
        Thread.sleep(1500); // Wait for GC to act

        long afterGC = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("After GC - Used Memory: " + afterGC + " bytes");

        long freedMemory = beforeGC - afterGC;
        System.out.println("Memory Freed: " + freedMemory + " bytes");

        if (freedMemory > 0) {
            System.out.println("✅ Memory cleaned.");
        } else {
            System.out.println("⚠️ No significant memory freed.");
        }
    }

}
