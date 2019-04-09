package com.game.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.game.base.BaseTestApplication;

public class RunWithSetTestData extends BaseTestApplication {
	
	static final Logger log = LoggerFactory.getLogger(RunWithSetTestData.class);
	
	@Test(priority = 1, enabled = true)
	public void verifyPageElementPresence() {

		Assert.assertTrue((driver.getTitle()).equals(ElementConfig
				.getProperty("pageTitle")));
		Assert.assertTrue(driver.findElement(By.id(ElementConfig.getProperty("slot")))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(ElementConfig.getProperty("balanceTextBox"))).isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(ElementConfig.getProperty("setTestDataInputBox"))).isDisplayed());

		Assert.assertTrue(driver.findElement(
				By.id(ElementConfig.getProperty("payTableLookup"))).isDisplayed());
		Assert.assertTrue(driver.findElement(
				By.id(ElementConfig.getProperty("spinButton"))).isDisplayed());

	}

	@Test(priority = 2, enabled = true)
	public void spinDecrementsBalance() {
		driver.findElement(By.id(ElementConfig.getProperty("setTestDataInputBox")))
				.clear();
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

		while (!(driver.findElement(By.id(ElementConfig.getProperty("winMessage")))
				.isDisplayed())) {
			spin();
		}
		// capturing balance after Win
		int balanceNew = captureCurrentBalance();
		// Test passes if balance has increased after win
		Assert.assertTrue(balanceNew > balance);

	}

	@Test(dataProvider = "paytable", priority = 5, enabled = true)
	public void setDataWin(String combination, int win,String winCss) {


		driver.findElement(By.id(ElementConfig.getProperty("setTestDataInputBox")))
				.clear();
		driver.findElement(By.id(ElementConfig.getProperty("setTestDataInputBox")))
				.sendKeys(combination);

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
			log.error("Win Message is not displayed for combination"
					+ combination);
		}
		String winText = driver
				.findElement(By.id(ElementConfig.getProperty("winMessage"))).getText();
		String[] split = winText.split("\\s+");
		int winDisplay = Integer.parseInt(split[1]);
		int balanceNew = Integer.parseInt(driver.findElement(
				By.id(ElementConfig.getProperty("balanceTextBox"))).getAttribute("value"));

		int excpectedBalance = (balance - 1) + win;
		Assert.assertEquals(winDisplay, win);

		// take screenshot

		Assert.assertEquals(balanceNew, excpectedBalance);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.id("winbox")));

	}

	@DataProvider
	Object[][] paytable() {

		return new Object[][] { 
				//{"SetTestData value", winPoints, "winClassName at Paytable"
				{ "11100", 60, "win111" },
				{ "11110", 80, "win1111" },
				{ "11111", 100, "win11111" },

				{ "22200", 120, "win222" }, 
				{ "22220", 160, "win2222" }, 
				{ "22222", 200, "win22222" },

				{ "33300", 180, "win333" },
				{ "33330", 240, "win3333" },
				{ "33333", 300, "win33333" },

				{ "44400", 240, "win444" },
				{ "44440", 320, "win4444" }, 
				{ "44444", 400, "win44444" },

				{ "55500", 300, "win555" }, 
				{ "55550", 400, "win5555" }, 
				{ "55555", 500, "win55555" }

		};

	}
	
	
	@Test(priority = 6,dataProvider = "paytable",dependsOnMethods={"setDataWin"}, enabled = true, alwaysRun=true)
	public void paytableWinHighlight(String combination, int win,String winCss) {
		driver.navigate().refresh();

		driver.findElement(By.id(ElementConfig.getProperty("setTestDataInputBox")))
				.clear();
		driver.findElement(By.id(ElementConfig.getProperty("setTestDataInputBox")))
				.sendKeys(combination);
		spin();
		
		String fontStyle = (driver.findElement(By.className(winCss))
				.getCssValue("font-style"));
		String fontWeight = (driver.findElement(By.className(winCss))
				.getCssValue("font-weight"));		

		Assert.assertTrue(fontStyle.equals("italic")&&fontWeight.equals("700"));
		

	}

}
