package com.me.utilities;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class DataProvide{
	
	Object[][] obj;
	
	@DataProvider(name="dp")
	public Object[][] dataGenerator(Method m) throws IOException{
		
		String fileName = "Registration.xlsx";
		
		if(m.getName().equalsIgnoreCase("registration")){
			
			String sheetName = "Registration.xlsx";
			obj = ExcelUtils.providesData(fileName, sheetName);

		}
			
		else if(m.getName().equalsIgnoreCase("phoneChange")){
			
			String sheetName = "Login.xlsx";
			obj = ExcelUtils.providesData(fileName, sheetName);

		}
		
		else if(m.getName().equalsIgnoreCase("addProduct")){
			
			String sheetName = "TC003";
			obj = ExcelUtils.dataAsMap(fileName, sheetName);

		}
		
		else if(m.getName().equalsIgnoreCase("menuCount")){
			
			String sheetName = "Login.xlsx";
			obj = ExcelUtils.providesData(fileName, sheetName);

		}
		
		else if(m.getName().equalsIgnoreCase("navigateBrands")){
			
			String sheetName = "Login.xlsx";
			obj = ExcelUtils.providesData(fileName, sheetName);

		}
		
		else if(m.getName().equalsIgnoreCase("buySamsung")){
			
			String sheetName = "TC_006";
			obj = ExcelUtils.dataAsMap(fileName, sheetName);

		}
		
		return obj;
	
	}
	
}
