package com.me.utilities;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
		
		private static ExtentReports extent;
		private static ExtentHtmlReporter htmlReporter;
	
		public static ExtentReports getInstance() {
			
				if(extent == null) {
				
					htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\ExtentReport.html");
					htmlReporter.config().setAutoCreateRelativePathMedia(true);
					htmlReporter.config().setDocumentTitle("Assignment 2 Report By Krishna"); 
					htmlReporter.config().setReportName("Selenium L2 Report"); 
					htmlReporter.config().setCSS("css-string");
					htmlReporter.config().setEncoding("utf-8");
					htmlReporter.config().setJS("js-string");
					htmlReporter.config().setProtocol(Protocol.HTTPS);
					htmlReporter.config().setTheme(Theme.DARK);
					htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
					htmlReporter.getDeviceContextInfo();
					htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/Resources/config/extent_config.xml"), true);

					extent = new ExtentReports();
					extent.attachReporter(htmlReporter);
					extent.setSystemInfo("Host Name", "Krishna");
					extent.setSystemInfo("User Name", System.getProperty("user.name"));
					extent.setSystemInfo("JVM", System.getProperty("java.version"));
					extent.setSystemInfo("Runtime Version", System.getProperty("java.runtime.version"));
					extent.setSystemInfo("System OS Architecture", System.getProperty("os.arch"));
					extent.setSystemInfo("System OS Name", System.getProperty("os.name"));
					extent.setSystemInfo("System OS Version", System.getProperty("os.version"));
					extent.setSystemInfo("Working Directory", System.getProperty("user.dir"));
				}
				
			return extent;
		}
		

}//test1.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("1.png").build());
