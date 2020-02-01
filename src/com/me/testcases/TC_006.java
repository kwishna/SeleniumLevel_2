package com.me.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.me.base.BaseClass;
import com.me.utilities.DataProvide;
import com.me.utilities.ExcelUtils;
import com.me.utilities.MyUtilities;

public class TC_006 extends BaseClass{
	
	@Test(description="Add product to cart and make payment", dataProvider="dp", dataProviderClass=DataProvide.class)
	public void buySamsung(Map<String, String> m) throws FileNotFoundException, IOException, InterruptedException {
		
		driver.get(propertyReader.getValue("url"));
		
		driver.findElement(By.xpath("//li[@class=\'dropdown\']/a[contains(@href,\'index.php?route=account/account\')]")).click();
		driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href,'index.php?route=account/login\')]")).click();
		
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(m.get("Email"));
		
		
		driver.findElement(By.id("input-password")).clear();
		driver.findElement(By.id("input-password")).sendKeys(m.get("Password"));

		
		driver.findElement(By.xpath("//input[@type=\'submit\' and @value=\'Login\']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//aside[@id='column-right']//a[1][contains(text(),'My Account')]")).isDisplayed(), "Error : Not Succesfully Logged In");
		
		MyUtilities.captureAndSaveScreenshot();
		
		driver.findElement(By.xpath("//a[contains(@href, 'route=product/category&path=25')]")).click();
		driver.findElement(By.xpath("//a[contains(text(), 'Monitors')]")).click();

		MyUtilities.captureAndSaveScreenshot();
		
		String productName = driver.findElement(By.xpath("//div[@id='content']//div[@class='caption']/h4/a[contains(text(), 'Apple')]")).getText();
		
		String productDetails = driver.findElement(By.xpath("//div[@id='content']//div[@class='caption']/p[contains(text(), 'Apple')]")).getText();
		
		String productPrice = driver.findElement(By.xpath("//div[@id='content']//div[@class='caption']/p/span[@class='price-new']")).getText();
		
		filewriter.writeIntoFile("TC_006_ProductDetails.txt", "Product Name : "+productName+"\nProduct Details : "+productDetails+"\nProduct Price : "+productPrice+"\n\n");
		
		driver.findElement(By.xpath("//div[contains(@class,'product-layout')][2]//button[contains(@onclick, 'cart.add')]")).click();
		
		MyUtilities.captureAndSaveScreenshot();
		
		WebElement succ = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
		
		Assert.assertTrue(succ.isDisplayed(), "Error! Not Added To Cart Successfully");
		
		MyUtilities.captureAndSaveScreenshot();
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0)");
		Thread.sleep(2000);

		driver.findElement(By.xpath("//div[@id='top-links']/ul/li[4]/a")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='checkout-cart']//a[contains(@href,'/index.php?route=checkout/cart')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='checkout-cart']//a[contains(@href,'/index.php?route=checkout/cart')]")).isDisplayed(), "Error! Checkout Page Didn't Open");
		
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,100)");
		
		MyUtilities.captureAndSaveScreenshot();
		
		
	
		String prodName = driver.findElement(By.xpath("//div[@id='content']//tbody/tr[1]/td[2]/a")).getText();
		String prodPrice = driver.findElement(By.xpath("//div[@id='content']//tbody/tr[1]/td[6]")).getText();
		
		filewriter.writeIntoFile("TC_006_ProductDetails.txt", "Product Name : "+prodName+"\nProduct Price : "+prodPrice+"\n\n");
		
		
		MyUtilities.scrollToTheElement(driver.findElement(By.partialLinkText("Shipping & Taxes")));
		driver.findElement(By.partialLinkText("Shipping & Taxes")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-country")));
		
		driver.findElement(By.id("input-country")).click();
		new Select(driver.findElement(By.id("input-country"))).selectByVisibleText(m.get("Country"));
		
		driver.findElement(By.id("input-zone")).click();
		new Select(driver.findElement(By.id("input-zone"))).selectByVisibleText(m.get("State"));
		
		driver.findElement(By.id("input-postcode")).clear();
		driver.findElement(By.id("input-postcode")).sendKeys(m.get("Post"));
		
		driver.findElement(By.id("button-quote")).click();
		
		Thread.sleep(2000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method']")).isDisplayed(), "Error : Pop Up Is Not Displayed");
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method']")).isSelected());
		
		driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("button-shipping")));
		
		driver.findElement(By.id("button-shipping")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-success alert-dismissible']")));
		
		WebElement ssShipping = driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
		Assert.assertTrue(ssShipping.getText().contains("Success: Your shipping estimate has been applied!"));
		
		driver.findElement(By.xpath("//a[contains(@href,'/index.php?route=checkout/checkout') and @class=\'btn btn-primary\']")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='content']/h1[contains(text(),'Continue')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='content']/h1[contains(text(),'Continue')]")).isDisplayed(), "Error! Payment Page Didn't Open");
		
		driver.findElement(By.id("input-payment-firstname")).clear();
		driver.findElement(By.id("input-payment-firstname")).sendKeys(m.get("Firstname"));
		
		driver.findElement(By.id("input-payment-lastname")).clear();
		driver.findElement(By.id("input-payment-lastname")).sendKeys(m.get("Lastname"));
		
		driver.findElement(By.id("input-payment-company")).clear();
		driver.findElement(By.id("input-payment-company")).sendKeys(m.get("Company"));
		
		driver.findElement(By.id("input-payment-address-1")).clear();
		driver.findElement(By.id("input-payment-address-1")).sendKeys(m.get("Address1"));
		
		driver.findElement(By.id("input-payment-address-2")).clear();
		driver.findElement(By.id("input-payment-address-2")).sendKeys(m.get("Address2"));
		
		driver.findElement(By.id("input-payment-city")).clear();
		driver.findElement(By.id("input-payment-city")).sendKeys(m.get("City"));
		
		driver.findElement(By.id("input-payment-postcode")).clear();
		driver.findElement(By.id("input-payment-postcode")).sendKeys(m.get("Post"));
		
		driver.findElement(By.id("input-payment-country")).click();
		new Select(driver.findElement(By.id("input-payment-country"))).selectByVisibleText(m.get("Country"));
		
		driver.findElement(By.id("input-payment-zone")).click();
		new Select(driver.findElement(By.id("input-payment-zone"))).selectByVisibleText(m.get("State"));
		
		driver.findElement(By.id("button-payment-address")).click();
		
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-shipping-address")));
		driver.findElement(By.id("button-shipping-address")).click();
		
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-shipping-method")));
		driver.findElement(By.xpath("//input[@type='radio' and @name='shipping_method' and @value='flat.flat']")).click();
		driver.findElement(By.xpath("//div[@id='collapse-shipping-method']//textarea[@name='comment']")).clear();
		driver.findElement(By.xpath("//div[@id='collapse-shipping-method']//textarea[@name='comment']")).sendKeys(m.get("ShippingComment"));
		driver.findElement(By.id("button-shipping-method")).click();
		

		Thread.sleep(1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-payment-method")));
		driver.findElement(By.xpath("//div[@id='collapse-payment-method']//textarea[@name='comment']")).clear();
		driver.findElement(By.xpath("//div[@id='collapse-payment-method']//textarea[@name='comment']")).sendKeys(m.get("PaymentComment"));
		driver.findElement(By.xpath("//input[@type='checkbox' and @name='agree' and @value='1']")).click();
		driver.findElement(By.id("button-payment-method")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-confirm")));
		MyUtilities.captureAndSaveScreenshot();
		
	//###########################################################################################
		
		List<WebElement> thead = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']/thead/tr/td"));
		List<WebElement> tbody = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr"));
		List<WebElement> tfoot = driver.findElements(By.xpath("//table[@class='table table-bordered table-hover']/tbody/tr"));
		
		String[][] str = {{}};
		
		int hd = 0;
		int bd = 1;
		
		for(int i=0; i<thead.size(); i++) {
			
			
			
			str[hd][i] = thead.get(i).getText();
		}
		
		for(int i=0; i<tbody.size(); i++) {
			
			List<WebElement> tbodyTd = tbody.get(i).findElements(By.tagName("td"));
			
			for(int j=0; j<tbodyTd.size(); j++) {
				
				if(j==0) {  str[bd][j] = tbodyTd.get(i).findElement(By.tagName("a")).getText(); continue; }
				
				str[bd][j] = tbodyTd.get(j).getText();
			}
			
			bd++;
		}
		
		for(int i=0; i<tfoot.size(); i++) {
			
			List<WebElement> tfootTd = tbody.get(i).findElements(By.tagName("td"));
			
			for(int j=0; j<tfootTd.size(); j++) {
				
				if(j==0) {  str[bd][j] = tfootTd.get(i).findElement(By.tagName("strong")).getText(); continue; }
				
				str[bd][j] = tfootTd.get(j).getText();
			}
			
			bd++;
		}
		
		
		ExcelUtils.writeInExcel(str, "TableData2", "Sheet1");
		//ExcelUtils.writeInExcel(String, FileName, SheetName);
		
		
	//###########################################################################################		
		
		driver.findElement(By.id("button-confirm")).click();
		
		
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='content']/h1"))));
		String orderSuccess = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals("Your order has been placed!", orderSuccess);
		
		
		driver.findElement(By.xpath("//div[@id=\'top-links\']//li[@class=\'dropdown\']//a[@title=\'My Account\']")).click();	
		driver.findElement(By.xpath("//ul[@class=\'dropdown-menu dropdown-menu-right\']//a[text()=\'Logout\']")).click();
		
		String logoutSucessMsg = driver.findElement(By.xpath("//div[@id='content']//p[1]")).getText();
		Assert.assertEquals(logoutSucessMsg.trim(), "You have been logged off your account. It is now safe to leave the computer.","Error : Logout Failed");
		
		WebElement logoutMsg = driver.findElement(By.xpath("//div[@id=\'content\' and @class=\'col-sm-9\']"));
		
		String logoutMsg1 = logoutMsg.findElement(By.tagName("h1")).getText();
		StringBuilder completelogoutMsg = new StringBuilder(logoutMsg1+"\n");
			
		logoutMsg.findElements(By.tagName("p")).forEach((web) -> completelogoutMsg.append(web.getText()+"\n"));
		filewriter.writeIntoFile("TC_006_LogoutMsg.txt", completelogoutMsg.toString());
		
	}
}
