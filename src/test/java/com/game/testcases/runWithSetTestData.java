package com.game.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.game.base.BaseTestApplication;
import com.game.uitilities.DataRead;

public class runWithSetTestData extends BaseTestApplication{
	
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
	
	@Test
	public void setDataSpin() {
		
		String s = driver.findElement(By.xpath(OR.getProperty("reel1_notch2"))).getText();
		Assert.assertEquals(s, "");
		driver.findElement(By.xpath(OR.getProperty("spinButton"))).click();
		String s1 = driver.findElement(By.xpath(OR.getProperty("reel1_notch2"))).getText();
		Assert.assertEquals(s1, "");
		
	}
	
	@AfterMethod
	public void captureCurrentBalance(){
		//int balance = Integer.parseInt(driver.findElement(By.xpath(OR.getProperty("balanceTextBox"))).getText());
		System.out.println("Current balance is"+driver.findElement(By.xpath(OR.getProperty("balanceTextBox"))).getAttribute("value"));
	}
	
	/*@AfterMethod
	public void captureCurrentBalance(){
		int balance = Integer.parseInt(driver.findElement(By.xpath(OR.getProperty("balanceTextBox"))).getText());
		System.out.println("Current balance is"+balance);
	}*/
	

}
