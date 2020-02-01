package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.me.base.BaseClass;
import com.me.utilities.CustomDriverManager;
import com.me.utilities.DataProvide;
import com.me.utilities.MyUtilities;


@SuppressWarnings("unused")
public class TC_005 extends BaseClass{

	@Test(description="Navigate through Brands", dataProvider="dp", dataProviderClass=DataProvide.class)
	public void navigateBrands(String email, String password) throws FileNotFoundException, IOException{
		
		driver.get(propertyReader.getValue("url"));	
		
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/login\')]")).click();
		
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(email);
		
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(password);
		

		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		

		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		MyUtilities.captureAndSaveScreenshot();
		
		
		driver.findElement(By.xpath("//div[@id='logo']/h1/a")).click();
		
		Assert.assertTrue(driver.findElement(By.id("slideshow0")).isDisplayed());
		
		driver.findElement(By.xpath("//footer//a[contains(@href,'/index.php?route=product/manufacturer')]")).click();
		
		List<WebElement> nav = driver.findElements(By.xpath("//div[@id='content']//div[@class='row']//a"));
		
		filewriter.writeIntoFile("TC_005_BrandCount", nav.size()+"");
		
		
		for(int i=1; i<=nav.size(); i++) {
			
			List<WebElement> insideNav = driver.findElements(By.xpath("//div[@id='content']//div[@class='row']["+i+"]/div[@class='col-sm-3']"));
			
			for(int j=1; j<=insideNav.size(); j++) {
				
				WebElement e = driver.findElement(By.xpath("//div[@id='content']//div[@class='row']["+i+"]/div[@class='col-sm-3']["+j+"]/a"));
				
				String brandName = e.getText();
				
				MyUtilities.scrollToTheElement(e);
				
				e.click();
				
				Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"product-manufacturer\"]//a[text()=\'"+brandName+"\']")).isDisplayed(), "Brand Page Didn't Open");
				
				MyUtilities.captureFullPageScreenshot();
				
				new Actions(driver).moveToElement(driver.findElement(By.xpath("//footer//a[contains(@href,'/index.php?route=product/manufacturer')]"))).click().perform();
				
				Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@href,'/index.php?route=product/manufacturer') and text()='Brand']")).isDisplayed(), "Error! Brand Page Didn't Open");
	
			}	
			
		}
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();

		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		
		WebElement logoutMsg = driver.findElement(By.id("content"));
			
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		
		filewriter.writeIntoFile("TC_005_LogoutMsg.txt", completelogoutMsg.toString());
		
	}

}
