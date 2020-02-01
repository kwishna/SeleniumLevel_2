package com.me.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.google.common.io.Files;
import com.me.base.BaseClass;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class MyUtilities extends BaseClass{

	private String filePath = null;
	private FileWriter fwr = null;
	
	public void writeIntoFile(String fileName, String data) throws IOException {
			
			filePath = System.getProperty("user.dir")+"/Resources/outputFiles/";
		
			if(!fileName.endsWith(".txt")) {
				
				fileName = fileName + ".txt";
				
			}
				fwr = new FileWriter(filePath + fileName, true);
				fwr.write(data+"\n");
				fwr.flush();
				fwr.close();
				
				Reporter.log("Text Written Into File "+filePath+"/MessageFile.txt From Page : "+driver.getTitle());
				exTest.log(Status.INFO, "Text Written Into File "+(filePath+fileName)+" From Page : "+driver.getTitle());
				
	}
	
	public void writeIntoFile(String data) throws IOException {
	
			filePath = System.getProperty("user.dir")+"/Resources/outputFiles/";
			
			fwr = new FileWriter(filePath + "MessageFile.txt", true);
			fwr.write(data+"\n");
			fwr.flush();
			fwr.close();
			
			Reporter.log("Text Written Into File "+filePath+"/MessageFile.txt From Page : "+driver.getTitle());
			exTest.log(Status.INFO, "Text Written Into File "+filePath+"MessageFile.txt From Page : "+driver.getTitle());
	}
	
	public static void captureAndSaveScreenshot() throws IOException {
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		File sink = new File(System.getProperty("user.dir")+"/Resources/screenshot/"+String.join("_", LocalDateTime.now().toString().split("[^A-Za-z0-9]"))+".PNG");
		
		Files.copy(screenshot, sink);
		
		
		Reporter.log("Screenshot Captured And Attached In Report And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());
		exTest.log(Status.INFO, "Screenshot Captured And Attached In Report And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());
//		exTest.addScreenCaptureFromPath(sink.getAbsolutePath(), "Screenshot Here");
		
		Reporter.log("<a href=\""+sink+"\" target=\"_blank\">Click To Open Screenshot</a>");
		exTest.log(Status.INFO,"<a href=\""+sink+"\" target=\"_blank\">Click To Open Screenshot</a>");

	}
	
	public static void captureScreenshot() throws IOException {
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		File sink = new File(System.getProperty("user.dir")+"/Resources/screenshot/"+String.join("_", LocalDateTime.now().toString().split("[^A-Za-z0-9]"))+".PNG");
		
		Files.copy(screenshot, sink);
		
		Reporter.log("Screenshot Captured On Page And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());
		exTest.log(Status.INFO, "Screenshot Captured On Page And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());

	}
	
	public static void captureFullPageScreenshot() throws IOException {

		File sink = new File(System.getProperty("user.dir")+"/Resources/screenshot/"+String.join("_", LocalDateTime.now().toString().split("[^A-Za-z0-9]"))+".PNG");
		
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
	    ImageIO.write(fpScreenshot.getImage(),"PNG", sink);
		

	    Reporter.log("Screenshot Captured And Attached In Report And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());
		exTest.log(Status.INFO, "Screenshot Captured And Attached In Report And Saved To "+sink.getAbsolutePath()+"From Page : "+driver.getTitle());
		exTest.addScreenCaptureFromPath(sink.getAbsolutePath(), "Screenshot Here");
		
		Reporter.log("<a href=\""+sink+"\" target=\"_blank\">Click To Open Screenshot</a>");
//		exTest.log(Status.INFO,"<a href=\""+sink+"\" target=\"_blank\">Click To Open Screenshot</a>");

	}
		
	
	public static void upLoadFile(File fileName) {
		
		if(fileName.exists()) {
			
			try {
				
				Runtime.getRuntime().exec(System.getProperty("user.dir")+"/Resources/drivers/UploadFile.exe"+" "+fileName.getAbsolutePath());
			
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		Reporter.log("File Uploaded On Page : "+driver.getTitle());
		exTest.log(Status.INFO, "File Uploaded On Page : "+driver.getTitle());
		
	}
	
	
	public static void scrollToTheElement(WebElement e) {
	
	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", e);
	
	Reporter.log("Scrolled To Element "+e.getText()+" On Page : "+driver.getTitle());
	exTest.log(Status.INFO, "Scrolled To Element "+e.getText()+" On Page : "+driver.getTitle());
	
	}	
	
	public static void scrollToTheElement(By by) {
		
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(by));
		
		Reporter.log("Scrolled To Element "+by.toString()+" On Page : "+driver.getTitle());
		exTest.log(Status.INFO, "Scrolled To Element "+by.toString()+" On Page : "+driver.getTitle());
		
		}
	
	
}