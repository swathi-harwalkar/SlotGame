package com.game.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.game.base.BaseTestApplication;

public class RunWithSetTestData extends BaseTestApplication {

	@Test(priority = 1, enabled = true)
	public void verifyPageElementPresence() {

		Assert.assertTrue((driver.getTitle()).equals(OR
				.getProperty("pageTitle")));
		Assert.assertTrue(driver.findElement(By.id(OR.getProperty("slot")))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(OR.getProperty("balanceTextBox"))).isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(OR.getProperty("setTestDataInputBox"))).isDisplayed());

		Assert.assertTrue(driver.findElement(
				By.id(OR.getProperty("payTableLookup"))).isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(OR.getProperty("spinButton"))).isDisplayed());
		

	}

	@Test(priority = 2, enabled = true)
	public void spinDecrementsBalance() {
		driver.findElement(By.id(OR.getProperty("setTestDataInputBox"))).clear();
		int balance = captureCurrentBalance();
		spin();
		if (!isWin()) {
			int balanceNew = captureCurrentBalance();
			Assert.assertEquals(balanceNew, balance - 1);
		} else
			spinDecrementsBalance();

	}

	@Test(priority = 3, enabled = true)
	public void spinUpdatesSlotTable() {
		// verifying slot table numbers are updated on clicking spin
		spin();
		int[][] slot1 = slotNumbers();
		spin();
		int[][] slot2 = slotNumbers();
		// compare table displayed after spin is not equal to previous
		// table
		Assert.assertTrue(slot1 != slot2);

	}

	@Test(priority = 4, enabled = true)
	public void clickUntilWin() {

		// capturing initial balance
		int balance = captureCurrentBalance();

		while (!(driver.findElement(By.id(OR.getProperty("winMessage"))).isDisplayed())) {
			spin();
		}
		// capturing balance after Win
		int balanceNew = captureCurrentBalance();
		// Test passes if balance has increased after win
		Assert.assertTrue(balanceNew > balance);

	}
	
	

	@Test(dataProvider = "paytable", priority = 5, enabled = true)
	public void setDataWin(String combination, int win) {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		driver.findElement(By.id(OR.getProperty("setTestDataInputBox"))).clear();
		driver.findElement(By.id(OR.getProperty("setTestDataInputBox"))).sendKeys(combination);

		int balance = Integer.parseInt(driver.findElement(
				By.id("balance-value")).getAttribute("value"));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.id("winbox")));

		spin();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.id("winbox")));
		} catch (TimeoutException te) {

			Assert.fail("Win Message is not displayed for combination"
					+ combination);
		}
		String winText = driver.findElement(By.id(OR.getProperty("winMessage"))).getText();
		String[] split = winText.split("\\s+");
		int winDisplay = Integer.parseInt(split[1]);
		int balanceNew = Integer.parseInt(driver.findElement(
				By.id(OR.getProperty("balanceTextBox"))).getAttribute("value"));

		int excpectedBalance = (balance - 1) + win;
		Assert.assertEquals(winDisplay, win);

		//take screenshot

		Assert.assertEquals(balanceNew, excpectedBalance);
		

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.id("winbox")));

	}

	@DataProvider
	Object[][] paytable() {

		return new Object[][] { { "11100", 60 }, { "11110", 80 },
				{ "11111", 100 },

				{ "22200", 120 }, { "22220", 160 }, { "22222", 200 },

				{ "33300", 180 }, { "33330", 240 }, { "33333", 300 },

				{ "44400", 240 }, { "44440", 320 }, { "44444", 400 },

				{ "55500", 300 }, { "55550", 400 }, { "55555", 500 }

		};

	}
	@Test(priority = 6, enabled = true)
	public void wonCombinationHighlight() {

	
		driver.findElement(By.id(OR.getProperty("setTestDataInputBox"))).clear();
		driver.findElement(By.id(OR.getProperty("setTestDataInputBox"))).sendKeys("33330");
		spin();
		
		
		Assert.assertTrue((driver.findElement(By.className("win3333")).getCssValue("font-style")).equals("italic"));

	}

}
