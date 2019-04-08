package com.game.base;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class BaseTestApplication {

	// Initialising webdriver, reading properties and common methods

	public static WebDriver driver;
	public static Properties Config = new Properties();
	public static Properties ElementConfig = new Properties();
	public static FileInputStream fis;
	public static WebDriverWait wait;
	static final Logger log = LoggerFactory.getLogger(BaseTestApplication.class);

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			// Loading Config.properties and ElementConfig.properties
			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/properties/ElementConfig.properties");
				ElementConfig.load(fis);
				log.debug("Element Repository File Loaded");

				fis = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/properties/Config.properties");
				Config.load(fis);
				log.debug("Config File Loaded");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("Properties File Not Found at specified location");
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Error while Reading or Writting into Properties File");
			}

			if (Config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome Launched");
				driver.get(System.getProperty("user.dir")+ "\\src\\test\\resources\\executables\\Test_Task.html");
				

			} else if (Config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("FireFox Launched");
				driver.get(Config.getProperty("testURL"));
				

			} else if (Config.getProperty("browser").equals("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				log.debug("Edge Launched");
				driver.get(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\executables\\Test_Task.html");
				
				// Or can get the path from config properties
				// driver.get(Config.getProperty("testURL"));
			}
log.debug("Navigated to Test_Task.html");
			driver.manage().window().maximize();
			driver.manage()
					.timeouts()
					.implicitlyWait(
							Integer.parseInt(Config.getProperty("implicitWait")),
							TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, Integer.parseInt(Config
					.getProperty("explicitWait")));

		}

	}

	public int[][] slotNumbers() {
		// Capturing and returning all the numbers displayed in the slot

		int[][] numbers = new int[3][5];

		int i;
		int j;

		try {
			for (i = 0; i < 3; i++) {
				int I = i + 1;
				for (j = 0; j < 5; j++) {
					int J = j + 1;
					numbers[i][j] = Integer.parseInt(driver
							.findElement(
									By.xpath("//*[@id='reel" + J + "']/div["
											+ I + "]")).getText());

				}
			}
		} catch (NumberFormatException nfe) {
			Assert.fail("Error: Slot has empty or non-numeric values");
			log.error("Error: Slot has empty or non-numeric values");
		}

		// To Print all numbers in slot
		/*
		 * for(i=0;i<3;i++){ for(j=0;j<5;j++){ System.out.print(numbers[i][j]);
		 * System.out.print(" "); } System.out.println("");
		 * 
		 * }
		 */

		return numbers;

	}

	public int captureCurrentBalance() {

		int balance = Integer.parseInt(driver.findElement(
				By.id(ElementConfig.getProperty("balanceTextBox"))).getAttribute("value"));
		return balance;
	}

	public void spin() {
		driver.findElement(By.id(ElementConfig.getProperty("spinButton"))).click();
	}

	public static boolean isWin() {

		return driver.findElement(By.id(ElementConfig.getProperty("winMessage")))
				.isDisplayed();

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
			
		}
		log.debug("Test Execution completed");
	}

}
