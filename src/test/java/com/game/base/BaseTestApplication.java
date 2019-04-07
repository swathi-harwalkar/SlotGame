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
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTestApplication {

	// initialising webdriver, reading properties and common methods

	public static WebDriver driver;
	public static Properties Config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static WebDriverWait wait;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/properties/OR.properties");
				OR.load(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/properties/Config.properties");
				Config.load(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.get(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\executables\\Test_Task.html");

			} else if (Config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.get(Config.getProperty("testURL"));

			} else if (Config.getProperty("browser").equals("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.get(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\executables\\Test_Task.html");
			}

			// Or can get the path from config properties
			// driver.get(Config.getProperty("testURL"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitWait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, Integer.parseInt(Config.getProperty("explicitWait")));

		}

	}

	public int[][] slotNumbers() {

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
				By.id(OR.getProperty("balanceTextBox"))).getAttribute("value"));
		return balance;
	}

	public void spin() {
		driver.findElement(By.id(OR.getProperty("spinButton"))).click();
	}

	public static boolean isWin() {

		return driver.findElement(By.id(OR.getProperty("winMessage")))
				.isDisplayed();

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

	}

}
