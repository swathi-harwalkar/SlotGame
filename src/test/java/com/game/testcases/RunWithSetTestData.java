package com.game.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.game.base.BaseTestApplication;
import com.game.uitilities.DataRead;

public class RunWithSetTestData extends BaseTestApplication{
	
	/*@BeforeTest
	public void getTestData(){
		DataRead reader = new DataRead();
		List<String> lines = reader.readFile();
	}
	@BeforeTest
	public void setProperties() throws IOException{
		Properties element = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/SlotGame/src/test/resources/OR.properties");
		element.load(fis);
		
		System.out.println(element.getProperty("dataInputTextBox"));
	}*/
	
	/*@Test
	public void setDataSpin() {
		
		String s = driver.findElement(By.xpath(OR.getProperty("reel1_notch2"))).getText();
		Assert.assertEquals(s, "");
		driver.findElement(By.xpath(OR.getProperty("spinButton"))).click();
		String s1 = driver.findElement(By.xpath(OR.getProperty("reel1_notch2"))).getText();
		Assert.assertEquals(s1, "");
		
	}
	

	
	@Test
	
	public void clickUntilWin(){
		while(!(driver.findElement(By.id("winbox")).isDisplayed())){
			driver.findElement(By.id("spinButton")).click();
		}
		System.out.println(driver.findElement(By.id("winbox")).getText());
	}*/
	
	@Test(dataProvider="paytable")
	
	public void setDataWin(String combination, int win){
		
		WebDriverWait wait = new WebDriverWait(driver,10);
		
		
		driver.findElement(By.id("testdata")).clear();
		driver.findElement(By.id("testdata")).sendKeys(combination);
		
		int balance = Integer.parseInt(driver.findElement(By.id("balance-value")).getAttribute("value"));  
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("winbox")));
		
		driver.findElement(By.id("spinButton")).click();
		try{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("winbox")));
		}catch(TimeoutException te){
			
			Assert.fail("Win Message is not displayed for combination" +combination);
		}
		String winText = driver.findElement(By.id("winbox")).getText();
		String[] split = winText.split("\\s+");
		int winDisplay = Integer.parseInt(split[1]);
		int balanceNew = Integer.parseInt(driver.findElement(By.id("balance-value")).getAttribute("value")); 

		int excpectedBalance =(balance - 1) + win;
		Assert.assertEquals(winDisplay, win);
		
		System.out.println("Win is +winDisplay +win +combination "+winDisplay +win +combination);
			
		Assert.assertEquals(balanceNew,excpectedBalance); 
				System.out.println("Balance is +excpectedBalance +balanceNew +combination " +excpectedBalance +balanceNew +combination);
			
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("winbox")));

		
	}
	
	@DataProvider
	Object[][] paytable(){
		
		return new Object[][]{
				{"11100", 60},
				{"11110", 80},
				{"11111", 100},
				
				{"22200", 120},
				{"22220", 160},
				{"22222", 200},
				
				{"33300", 180},
				{"33330", 240},
				{"33333", 300},
				
				{"44400", 240},
				{"44440", 320},
				{"44444", 400},
				
				{"55500", 300},
				{"55550", 400},
				{"55555", 500}
				
				
				
		};
		
	}
	
	
	

}
