package PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class CallFunctions 
{
	private static WebElement element=null;
	  @Test
	  public static WebElement Username_textbox(WebDriver driver) {
		  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserName")));
		  element=driver.findElement(By.id("UserName"));
		  return element;
	  }

}
