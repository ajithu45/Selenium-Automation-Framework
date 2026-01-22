package iRetry;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//import Application.extent_report_test;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnifiedExtentTestNGListener implements ITestListener {

    private static Map<String, ExtentReports> extentMap = new ConcurrentHashMap<>();
    private Map<String, Map<String, ExtentTest>> suiteTestMap = new HashMap<>();
    private Map<ITestResult, ExtentTest> testResultMap = new ConcurrentHashMap<>();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    public static ExtentTest test;   // static test variable

    private static String forcedReportName = null;

    public static void setForcedReportName(String reportName) {
        forcedReportName = reportName;
    }
    private ExtentReports getExtent(String suiteName) {
    	  // 1️⃣ Decide report name
        String reportName;

        if (forcedReportName != null && !forcedReportName.isEmpty()) {
            reportName = forcedReportName; // Use forced name for direct class run
        } else if (suiteName != null && !suiteName.isEmpty()) {
            reportName = suiteName;        // Use suite name for XML run
        } else {
            reportName = "DefaultTestReport"; // fallback
        }
    	
        return extentMap.computeIfAbsent(reportName, name -> {
        	
        	
            ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + name +"_Report.html");
           
            spark.config().setDocumentTitle(name + " Automation Report");
            spark.config().setReportName(name + " Test Results");

            ExtentReports extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Suite", name);
            // Add shutdown hook once per ExtentReports instance
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutdown detected, flushing report for suite: " + name);
                    extent.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            return extent;
        });
    }
 
    public static ExtentTest getCurrentTest() {
        return testThread.get();
    }
 
    
    // ==========================================================
    // 🔹 SCREENSHOT HANDLER
    // ==========================================================
    public static void screenshot_store(WebDriver driver) {
        try {
            // 1️⃣ Timestamp
            String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String dateFolder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // 2️⃣ Base folder
            String baseFolder = "D:\\ExtentReport_Screenshot";

            // 3️⃣ Create subfolder for today
            File folder = new File(baseFolder + "\\" + dateFolder);
            if (!folder.exists()) {
                folder.mkdirs();
                System.out.println("✅ Created folder: " + folder.getAbsolutePath());
            }

            // 4️⃣ Screenshot file name & full path
            String fileName = "ExtentReport_" + dateTime + ".png";
            String fullPath = folder.getAbsolutePath() + "\\" + fileName;

            // 5️⃣ Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // 6️⃣ Save screenshot to correct folder
            FileUtils.copyFile(source, new File(fullPath));

            // 7️⃣ Attach screenshot to current test safely
            ExtentTest currentTest = getCurrentTest(); // get from listener
            if (currentTest != null) {
                currentTest.info( MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
            } else {
                System.out.println("❌ Current ExtentTest is null, screenshot not attached.");
            }

        } catch (Exception e) {
            System.err.println("⚠️ Screenshot capture failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
	public static void screenshot_store(WebDriver driver, String modulename) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = "/karomi/karomi/Projects" + "/Screenshots/" + modulename + "/" + modulename + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		test.info(MediaEntityBuilder.createScreenCaptureFromPath(destination).build());
	}
    

    // ==========================================================
    // 🔹 TESTNG LIFECYCLE EVENTS
    // ==========================================================

    @Override
    public void onTestStart(ITestResult result) {
        String suiteName ;
        suiteName= result.getTestContext().getSuite().getName();
        ExtentReports extent = getExtent(suiteName);
       

        if (!suiteTestMap.containsKey(suiteName)) {
            suiteTestMap.put(suiteName, new HashMap<>());
        }
        Map<String, ExtentTest> testMap = suiteTestMap.get(suiteName);

        String rawMethodName = result.getMethod().getMethodName();
        String testName = convertToTitle(rawMethodName);

        ExtentTest test;
        if (!testMap.containsKey(testName)) {
            test = extent.createTest(testName);
            testMap.put(testName, test);
        } else {
            test = testMap.get(testName);
        }
      

        // Store test for this result so onTestSuccess/onTestFailure can find it:
        testResultMap.put(result, test);

 
        testThread.set(test);
    }



    @Override
    public void onTestSuccess(ITestResult result) {
        if (RetryAnalyzer.isLastAttempt(result)) {
        	  ExtentTest test = testResultMap.get(result);
              if (test != null) {
                  test.pass("✅ Test Passed");
              }
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (RetryAnalyzer.isLastAttempt(result)) {
        	 ExtentTest test = testResultMap.get(result);
        	
             if (test != null) {
                 test.fail("❌ Test Failed");
                 test.fail(result.getThrowable());
             }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (RetryAnalyzer.isLastAttempt(result)) {
        	 ExtentTest test = testResultMap.get(result);
             if (test != null) {
                 test.skip("⚠️ Test Skipped");
             }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        String suiteName = context.getSuite().getName();       // get suiteName from context
        ExtentReports extent = getExtent(suiteName);

        // Remove duplicate retries from failed tests, keep only last attempt
        for (ITestResult result : context.getFailedTests().getAllResults()) {
            if (result.getMethod().getCurrentInvocationCount() < result.getMethod().getInvocationCount()) {
                context.getFailedTests().removeResult(result.getMethod());
            }
        }

        // Remove duplicate retries from skipped tests, keep only last attempt
        for (ITestResult result : context.getSkippedTests().getAllResults()) {
            if (result.getMethod().getCurrentInvocationCount() < result.getMethod().getInvocationCount()) {
                context.getSkippedTests().removeResult(result.getMethod());
            }
        }

        // Flush report at the end
        extent.flush();
    }

    private String convertToTitle(String methodName) {
        String spaced = methodName.replaceAll("([A-Z])", " $1").trim();
        return spaced.toUpperCase();
    }
}
