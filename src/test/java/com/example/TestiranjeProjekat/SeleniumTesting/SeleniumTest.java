package com.example.TestiranjeProjekat.SeleniumTesting;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
	  	ChromeOptions options = new ChromeOptions();
	    options.setCapability("webdriver.chrome.driver", "http://localhost:9515");

	    try {
	      driver = new ChromeDriver(options);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    vars = new HashMap<String, Object>();
	  }
  
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void test() {
    driver.get("http://localhost:4200/");
    driver.manage().window().setSize(new Dimension(1418, 1032));
    driver.findElement(By.cssSelector(".listItem-container:nth-child(1) .gg-check-o")).click();
    driver.findElement(By.cssSelector(".crossed-out > .listItem-button")).click();
    driver.findElement(By.cssSelector(".ng-untouched")).click();
    driver.findElement(By.cssSelector(".ng-untouched")).sendKeys("Novi task");
    driver.findElement(By.cssSelector(".new-item > button")).click();
    assertThat(driver.switchTo().alert().getText(), is("item added refresh the page"));
    driver.findElement(By.cssSelector(".listItem-container:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".listItem-container:nth-child(3) .gg-check-o")).click();
    driver.findElement(By.cssSelector(".crossed-out > .listItem-button")).click();
  }
}
