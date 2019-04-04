package com.game.base;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class Rough extends BaseTestApplication{

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("C:/Users/swathi/Test/Test_Task.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(By.id("spinButton")).click();
/*		WebElement slot = driver.findElement(By.id("slot"));
		System.out.println(slot.findElement(By.xpath("//*[@id='reel1']/div[1]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel1']/div[2]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel1']/div[3]")).getText());
		
		System.out.println(slot.findElement(By.xpath("//*[@id='reel2']/div[1]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel2']/div[2]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel2']/div[3]")).getText());
		
		System.out.println(slot.findElement(By.xpath("//*[@id='reel3']/div[1]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel3']/div[2]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel3']/div[3]")).getText());
		
		System.out.println(slot.findElement(By.xpath("//*[@id='reel4']/div[1]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel4']/div[2]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel4']/div[3]")).getText());
		
		System.out.println(slot.findElement(By.xpath("//*[@id='reel5']/div[1]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel5']/div[2]")).getText());
		System.out.println(slot.findElement(By.xpath("//*[@id='reel5']/div[3]")).getText());*/
		
		
		
	int[][] numbers= new int[3][5];
	
	int i;
	int j;
		
	 for(i=0;i<3;i++){
		 int I = i+1;
		 for(j=0;j<5;j++){
			 int J = j+1;
			 numbers[i][j]=Integer.parseInt(driver.findElement(By.xpath("//*[@id='reel"+J+"']/div["+I+"]")).getText());
			 
			 
		 }
	 }
	 
	 for(i=0;i<3;i++){
		 for(j=0;j<5;j++){
			 System.out.print(numbers[i][j]);
			 System.out.print(" ");
		 }
		 System.out.println("");
		 
	 }

	}

}
