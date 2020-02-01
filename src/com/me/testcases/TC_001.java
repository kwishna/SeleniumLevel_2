package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.me.base.BaseClass;
import com.me.utilities.DataProvide;


@SuppressWarnings("unused")
public class TC_001 extends BaseClass{
	
	@Test(description="Registration", dataProvider="dp", dataProviderClass=DataProvide.class)
	public void registration(String firstName, String lastName, String email, String phoneNo, String password) throws FileNotFoundException, IOException, InterruptedException {
		
		exTest.log(Status.INFO, "Starting");
		
		driver.get(propertyReader.getValue("url"));
		
		
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/register')]")).click();


		driver.findElement(By.id("input-firstname")).clear();
		driver.findElement(By.id("input-firstname")).sendKeys(firstName);
		

		driver.findElement(By.id("input-lastname")).clear();
		driver.findElement(By.id("input-lastname")).sendKeys(lastName);
		
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(email);
		
		
		driver.findElement(By.id("input-telephone")).clear();
		driver.findElement(By.id("input-telephone")).sendKeys(""+phoneNo);
		
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(password);
		
		
		driver.findElement(By.id("input-confirm")).clear();
		driver.findElement(By.id("input-confirm")).sendKeys(password);
		

		WebElement privacy = driver.findElement(By.xpath("//a[contains(@href,\'index.php?route=information/information/agree&information_id=3')]"));
		privacy.click();
		
		
		String privacyText = driver.findElement(By.xpath("//div[@id=\'modal-agree\']//div[@class=\'modal-body\']/p")).getText();
		SoftAssert soft = new SoftAssert();

		soft.assertEquals("Privacy Policy", privacyText.trim());
		
		
		filewriter.writeIntoFile("TC_001_PrivacyDetails.txt", privacyText);
		
		
		driver.findElement(By.xpath("//div[@id=\'modal-agree\']//div[@class=\'modal-header\']/button")).click(); // Privacy Policy
		WebElement privacyCheckBox = driver.findElement(By.name("agree"));
		
		
		
		boolean checkboxStatus = privacyCheckBox.isSelected();

		filewriter.writeIntoFile("TC_001_checkBoxStatus", String.valueOf("Checkbox Status Is : " +checkboxStatus)); // Status Of CheckBox
		
		
		if(!checkboxStatus) {

			privacyCheckBox.click();
			
		}
		
			
		boolean radioStatus = driver.findElement(By.xpath("//input[@type=\'radio\' and @name=\'newsletter\']")).isSelected();
		
		if(!radioStatus) {
			
			driver.findElement(By.xpath("//input[@type=\'radio\' and @name=\'newsletter\']")).click();
			
		}
		

		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Continue\']")).click();
		
		
		WebElement success = driver.findElement(By.id("content"));
		
		String successMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		Assert.assertEquals(successMsg.trim(),"Congratulations! Your new account has been successfully created!", "Error : Registration Not Successful!");
		
		String successMsg1 = success.findElement(By.tagName("h1")).getText();
		StringBuilder completesuccessMsg = new StringBuilder(successMsg1+"\n");
		success.findElements(By.tagName("p")).forEach((web) -> completesuccessMsg.append(web.getText()+"\n"));
		
		filewriter.writeIntoFile("TC_001_SuccessMsg.txt", completesuccessMsg.toString());
		
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
			
		WebElement logoutMsg = driver.findElement(By.id("content")); // Logout Success Message div
		
			
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		
		filewriter.writeIntoFile("TC_001_LogoutMsg.txt", completelogoutMsg.toString());
		soft.assertAll();
	}
	
}
