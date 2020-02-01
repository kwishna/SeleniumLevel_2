package com.me.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
//	private final String propertyFilesFolder = "../Selenium_Assignment_1/Resources/config/";
//	private final String propertyFilesFolder = "D:\\BU396551\\Krishna\\Resources\\config\\";
	private String fileName;
	
	public PropertyReader(String fileName) {
		
			this.fileName = fileName;
		
	}
	
	public String getValue(String key) throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("user.dir")+"/Resources/config/"+fileName));
		return prop.getProperty(key);
		
	}
	
}
