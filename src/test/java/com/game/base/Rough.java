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

		Map<String, Integer> map = new HashMap<>();

		// Prepare the map
		map.put("11100", 60);
		map.put("11110", 80);
		map.put("11111", 100);
		map.put("22200", 120);
		map.put("22220", 160);
		map.put("22222", 200);
		map.put("33300", 180);
		map.put("33330", 240);
		map.put("33333", 300);
		map.put("44400", 240);
		map.put("44440", 320);
		map.put("44444", 400);
		map.put("55500", 300);
		map.put("55550", 400);
		map.put("55555", 500);

		// Loop
		int i=0;
		for (Entry<String, Integer> entry : map.entrySet()) {
			i++;
			String combination = entry.getKey();
			int win = entry.getValue();
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("winbox")));
			driver.findElement(By.id("testdata")).clear();
			driver.findElement(By.id("testdata")).sendKeys(combination);
			int balance = Integer.parseInt(driver.findElement(By.id("balance-value")).getAttribute("value"));  
			
			driver.findElement(By.id("spinButton")).click();
			try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("winbox")));
			}catch(TimeoutException te){
				System.out.println("Win Message is not displayed for combination" +combination);
				continue;
			}
			String winText = driver.findElement(By.id("winbox")).getText();
			String[] split = winText.split("\\s+");
			int winDisplay = Integer.parseInt(split[1]);
			int balanceNew = Integer.parseInt(driver.findElement(By.id("balance-value")).getAttribute("value")); 

			int excpectedBalance =(balance - 1) + win;
			if (win == winDisplay) {
				System.out.println("Win matches"+i);
				
				if (balanceNew == excpectedBalance) {
					System.out.println("Balance is updated correctly");
				} else { System.out.println("Error: Balance is not updated correctly => Expected:"+excpectedBalance+"Actual"+balanceNew);
				
				}
				
				
			}else { System.out.println("Error: win did not match=> Expected"+win+"Actual"+winDisplay);
			
				
			}

			
		}

	}
}
