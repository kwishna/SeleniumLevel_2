package com.me.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.me.base.BaseClass;
import com.me.utilities.DataProvide;
import com.me.utilities.MyUtilities;

@SuppressWarnings("unused")
public class TC_004 extends BaseClass{
	
	@Test(description="Validate menus and count links ", dataProvider="dp", dataProviderClass=DataProvide.class)
	public void menuCount(String email, String password) throws FileNotFoundException, IOException, InterruptedException, FindFailed, AWTException {
		
		driver.get(propertyReader.getValue("url"));
		
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[1][contains(@href,'index.php?route=account/login\')]")).click();
		
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(email);
		
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(password);
		
		
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		

		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		MyUtilities.captureAndSaveScreenshot();
		
		List<WebElement> nav = driver.findElements(By.xpath("//nav[@id='menu']//ul[@class='nav navbar-nav']/li"));
		
		Assert.assertTrue(nav.size() == 8, "Error : Unable To Find Menu");
		
		filewriter.writeIntoFile("TC_004_MenuCount", nav.size()+"");
		
		for(int i=1; i<=nav.size(); i++) {
			
			driver.findElement(By.xpath("//nav[@id='menu']//ul[@class='nav navbar-nav']/li["+i+"]")).click();
			MyUtilities.captureAndSaveScreenshot();
		}
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		
		WebElement logoutMsg = driver.findElement(By.id("content"));
			
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		
		filewriter.writeIntoFile("TC_004_LogoutMsg.txt", completelogoutMsg.toString());
		
	}
	
	
}
