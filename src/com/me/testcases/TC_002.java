package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.me.base.BaseClass;
import com.me.utilities.DataProvide;
import com.me.utilities.MyUtilities;


@SuppressWarnings("unused")
public class TC_002 extends BaseClass{
	
	@Test(description="Login and Edit Account information ", dataProvider="dp", dataProviderClass=DataProvide.class)
	public void phoneChange(String email, String password) throws FileNotFoundException, IOException {
		
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
		

		driver.findElement(By.xpath("//aside[@id='column-right']/div[@class='list-group']/a[contains(@href,'edit')]")).click();
		
		String accnt = driver.findElement(By.xpath("//div[@id='content']/form/fieldset/legend")).getText();
		Assert.assertEquals(accnt, "Your Personal Details", "Error : Account Page Didn't Open");
		

		driver.findElement(By.id("input-telephone")).clear();
		driver.findElement(By.id("input-telephone")).sendKeys("48974350947");
		

		driver.findElement(By.xpath("//input[@class='btn btn-primary' and @value='Continue']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).getText().contains("Success: Your account has been successfully updated."), "Error! Edit Account Failed!");
		
		MyUtilities.captureAndSaveScreenshot();
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();

		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
	
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");

		
		WebElement logoutMsg = driver.findElement(By.id("content"));
			
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		
		filewriter.writeIntoFile("TC_002_LogoutMsg.txt", completelogoutMsg.toString());
			
	}
}
