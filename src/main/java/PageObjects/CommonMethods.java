
package PageObjects;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

//import Application.extent_report_test;
import Utility.ConfigurationInputdata;
import Utility.ExcelUtils;

public class CommonMethods {

	public static String getcolor;

	public static String File;
	public static String Filename;
	public static String fileLocation;
	
	
	@Test
	public static void documentready(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String ready = (String) js.executeScript("return document.readyState");
		System.out.println(ready);
	}

	// Mask
	@Test
	public static void Mask(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("fullscreen-load")));
		Log.info("Mask is found");

	}

	// Window Maximize

	@Test
	public static void WindowMaximize(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		driver.manage().window().maximize();
		Log.info("Mask is found");

	}

	// refresh

	@Test
	public static void Refresh(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		driver.navigate().refresh();
		Log.info("Mask is found");
	}

	// Driver close

	@Test
	public static void driverclose(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		driver.close();
		Log.info("Mask is found");
	}


	// Page Scroll

	@Test
	public static void Pagescrolldown(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("scroll(0,750);");
		Log.info("Mask is found");
	}

	@Test
	public static void Pagescrollup(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("scroll(0,-500);");
		Log.info("Mask is found");
	}

	// implicit wait

	@Test
	public static void implicitwt(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		Log.info("Mask is found");
	}

	// Thread

	@Test
	public static void sleep(WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
		Thread.sleep(3000);
		Log.info("Mask is found");
	}

	@Test
	public static int isFileDownloaded(String downloadPath, String wanted_downloded_filename) {
		int flag = 0;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().contains(wanted_downloded_filename))
				return flag = 1;
		}

		return flag;
	}

	@Test
	public static void green(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector(".glyphicon.glyphicon-ok.bulkfile-upload-completed")));
			String sucess = driver.findElement(By.cssSelector(".glyphicon.glyphicon-ok.bulkfile-upload-completed"))
					.getCssValue("background-color");
			System.out.println(sucess);
			String[] hexValue = sucess.replace("rgba(", "").replace(")", "").split(",");
			int hexValue1 = Integer.parseInt(hexValue[0]);
			hexValue[1] = hexValue[1].trim();
			int hexValue2 = Integer.parseInt(hexValue[1]);
			hexValue[2] = hexValue[2].trim();
			int hexValue3 = Integer.parseInt(hexValue[2]);
			System.out.println(hexValue1);
			System.out.println(hexValue2);
			System.out.println(hexValue3);
			getcolor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
			System.out.println(getcolor);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public static void alert(WebDriver driver) {
		try {
			// Handle the alert pop-up using seithTO alert statement
			Alert alert = driver.switchTo().alert();
			Log.info("Alert is present");
			alert.accept();
			Log.info("Alert is Accept");
		} catch (NoAlertPresentException e) {
			Log.info("Alert is not present");
		}
	}

	@Test
	public static void alert_dismiss(WebDriver driver) {
		try {
			// Handle the alert pop-up using seithTO alert statement
			Alert alert = driver.switchTo().alert();
			Log.info("Alert is present");
			alert.dismiss();
			Log.info("Alert is Accept");
		} catch (NoAlertPresentException e) {
			Log.info("Alert is not present");
		}
	}

	@Test
	public static void DB_connectionwith_queryexecution(WebDriver driver, String query)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = ConfigurationInputdata.DBconnection_URL;
		String username = ConfigurationInputdata.DB_username;
		String password = ConfigurationInputdata.DB_password;
		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();

	}

	@Test
	public static ResultSet DB_connectionwith_queryexecutionwith_returndata(WebDriver driver, String query)
			throws SQLException, ClassNotFoundException {
		ResultSet resultSet = null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = ConfigurationInputdata.DBconnection_URL;
		String username = ConfigurationInputdata.DB_username;
		String password = ConfigurationInputdata.DB_password;
		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();
		resultSet.next();
		return resultSet;

	}

	@Test
	public static ResultSet DB_connectionwith_queryexecutionwith_returndata_Workflow(WebDriver driver, String query)
			throws SQLException, ClassNotFoundException {
		ResultSet resultSet = null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = ConfigurationInputdata.DBconnection_URL_workflow;
		String username = ConfigurationInputdata.DB_username;
		String password = ConfigurationInputdata.DB_password;
		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();
		resultSet.next();
		return resultSet;

	}

	@Test
	public static ResultSet DB_connectionwith_queryexecutionwith_returndata_tlog(WebDriver driver, String query)
			throws SQLException, ClassNotFoundException {
		ResultSet resultSet = null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = ConfigurationInputdata.DBconnection_URL_tlog;
		String username = ConfigurationInputdata.DB_username;
		String password = ConfigurationInputdata.DB_password;
		Connection conn = DriverManager.getConnection(url, username, password);
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		resultSet = preparedStmt.executeQuery();
		resultSet.next();
		return resultSet;

	}
	

}
