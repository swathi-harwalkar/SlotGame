package com.game.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTestApplication {

	// initialising webdriver, testdata read etc

	public static WebDriver driver;
	public static Properties Config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;

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

			System.setProperty(
					"webdriver.chrome.driver",
					"C:\\Users\\swathi\\workspace\\SlotGame\\SlotGame\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			// driver.get(Config.getProperty("testURL"));
			driver.get("C:/Users/swathi/Test/Test_Task.html");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		}

	}

	// @AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
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

	public static int captureCurrentBalance() {

		int balance = Integer.parseInt(driver.findElement(
				By.id(OR.getProperty("balanceTextBox"))).getAttribute("value"));
		return balance;
	}

	public void spin() {
		driver.findElement(By.id("spinButton")).click();
	}
	
	public static boolean isWin(){
		
		return driver.findElement(By.id("winbox")).isDisplayed();
		
	}

}
