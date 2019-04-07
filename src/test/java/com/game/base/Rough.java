package com.game.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.HashAttributeSet;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Rough extends BaseTestApplication {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("C:/Users/swathi/Test/Test_Task.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.id("spinButton")).click();
		// System.out.print(System.getProperty("user.dir")+
		// "\\src\\test\\resources\\executables\\Test_Task.html");
		System.out.println(driver.findElement(By.className("win3333"))
				.getCssValue("font-style"));
		driver.findElement(By.id("testdata")).sendKeys("33330");
		driver.findElement(By.id("spinButton")).click();
		System.out.println(driver.findElement(By.className("win3333"))
				.getCssValue("font-style"));

	}
}
