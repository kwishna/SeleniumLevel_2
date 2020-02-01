package com.me.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.me.utilities.CustomDriverManager;
import com.me.utilities.DriverCustomListener;
import com.me.utilities.MyUtilities;
import com.me.utilities.ExcelUtils;
import com.me.utilities.ExtentReportManager;
import com.me.utilities.PropertyReader;

/**
 * 1. By Default, Text Required To Be Saved In A File Will Get Saved Into A New File.
 *    If You Want To Save In A Single File Then Just Remove The 'File Names' When saveIntoFile() Method Is Called In The Test Cases.
 * 
 * 2. Generated Text Files Can Be Found In src/Resources/outputFiles
 * 
 * 3. Don't Change Folder Structure.
 * 
 * 4. Excel Sheet Must Be Present In src/Resources/testdata Folder And Save The Data Inside Sheet In text Format Only. All The Numbers, Dates Type
 * 	  Values Will Be Read As Text Only. Hence, Data Must Be Saved In Each Cell As Text Format Only.
 * 
 * 5. Browser Drivers Must Be Present In The src/Resources/drivers
 * 
 * 6. All The Required Compatible Jar Files Are Already Present In src/Resources/Jars
 * 
 * 7. For Uploading Files, AutoIT Is Used. .exe File Has Been Used Which Is Present At src/Resources/drivers/UploadFile.exe. (May Work On All Windows OS Only)
 * 
 * 8. properties File Is Present At src/Resources/config.
 * 
 * 9. Extent Report Will Be Generated At src/ExtentReport.html . Another Location Can Be Configured In Class com.me.utilities.ExtenReportManager.java
 *  
 *
 */

public class BaseClass {
	
	protected static PropertyReader propertyReader;
	protected static EventFiringWebDriver driver;
	protected static ExcelUtils excelUtils;
	protected static MyUtilities filewriter;
	protected static WebDriverWait wait;
	
	public static ExtentReports extentReport;
	public static ExtentTest exTest;
	
	
	@BeforeSuite
	public void setUp() throws IOException {
		
		propertyReader = new PropertyReader("config.properties");
		excelUtils = new ExcelUtils(propertyReader.getValue("excel"));
		filewriter = new MyUtilities();
	
		driver = CustomDriverManager.getInstance();
		driver.register(new DriverCustomListener());

		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 15);
		
		extentReport = ExtentReportManager.getInstance();
		
	}
	
	
	@BeforeMethod
	public void removeSavedCredentials() {

		driver.manage().deleteAllCookies();
		
	}
	
	@AfterSuite
	public void tearDown(){
		
		Reporter.log("Closing The Driver");
		driver.close();
	}

}
