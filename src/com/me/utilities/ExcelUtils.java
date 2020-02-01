package com.me.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private String excelFilePath;
	private FileInputStream fin = null;
	private Workbook work = null;
	
	private static XSSFWorkbook wbookWrite = null;
	private static XSSFWorkbook wbookRead = null;
	
	private static FileOutputStream ffout = null;
	private static FileInputStream ffint = null;
	
	
	
	public ExcelUtils(String fileName){

		excelFilePath = System.getProperty("user.dir")+"/Resources/testdata/";
			
		if(!fileName.endsWith(".xlsx")) {
			
			fileName = fileName + ".xlsx";
		}
		
		
		String file = excelFilePath + fileName;
		
		try {
			
			fin = new FileInputStream(file);
				
			work = new XSSFWorkbook(fin);
			
			fin.close();
		}
	
		 catch (Exception e) {
			
			e.printStackTrace();
		} 
		
}

	public String readCell(String sheetName, int row, int col) {
		
		DataFormatter formatter = new DataFormatter();
		
		Sheet sheet = work.getSheet(sheetName);
		Row roww = sheet.getRow(row);
		Cell cell = roww.getCell(col);
		
		return formatter.formatCellValue(cell);
		
	}
	
	public String readCell(int sheetIndex, int row, int col) {
		
		DataFormatter formatter = new DataFormatter();
		
		Sheet sheet = work.getSheetAt(sheetIndex);
		Row roww = sheet.getRow(row);
		Cell cell = roww.getCell(col);
		
		return formatter.formatCellValue(cell);
		
	}
	
	public static Object[][] providesData(String excelFileName, String sheetName) throws IOException{

		XSSFWorkbook workk = new XSSFWorkbook(new FileInputStream(new File(System.getProperty("user.dir")+"/Resources/testdata/"+excelFileName)));
		
		XSSFSheet sheett = workk.getSheet(sheetName);
		
		DataFormatter format = new DataFormatter();
		
		int rowCountt = sheett.getPhysicalNumberOfRows();
		
		Object[][] objj = new Object[rowCountt-1][];
		
		for(int i=1; i<rowCountt; i++) {
			
			XSSFRow ro = sheett.getRow(i);
			
				int cellCountt = ro.getPhysicalNumberOfCells();

				objj[i-1] = new Object[cellCountt];
			
					for(int j=0; j<cellCountt; j++) {
				
							XSSFCell celll = ro.getCell(j);
				
							objj[i-1][j] = format.formatCellValue(celll);

					}
			}
		
		return objj;
	}
	
	
	
	public static Object[][] dataAsMap(String excelFileName, String sheetName) throws IOException{
		
		ExcelUtils xr = new ExcelUtils(excelFileName);
		
	//	XSSFWorkbook workk = new XSSFWorkbook(new FileInputStream(new File(System.getProperty("user.dir")+"/Resources/testdata/"+excelFileName)));
		
		Sheet sheett = xr.work.getSheet(sheetName);
		
		int rowCountt = sheett.getPhysicalNumberOfRows();
		
		Object[][] objj = new Object[rowCountt-1][1];
		
		for(int i=1; i<rowCountt; i++) {
			
				Row ro = sheett.getRow(i);
			
				int cellCountt = ro.getPhysicalNumberOfCells();

		//		objj[i] = new Object[cellCountt];
			
				Map<String, String> hash = new HashMap<>();
				
					for(int j=0; j<cellCountt; j++) {

							hash.put(xr.readCell(sheetName, 0, j), xr.readCell(sheetName, i, j));
							
//							System.out.println(readCell(sheetName, 0, j)+"-----Added-----"+readCell(sheetName, i, j));
							
							
					}
					objj[i-1][0] = hash;
				//	k++;
			}
		
		return objj;
	}
	
	
	
	public static void writeInExcel(String[][] str, String ffileName, String sheetName){
		
		ffileName = ffileName.replaceAll("[^\\w-]", "_");
		
		if(!ffileName.endsWith(".xlsx")) {
			
			ffileName = ffileName + ".xlsx";
		}
		
		File eFile = new File(System.getProperty("user.dir")+"/"+ffileName);
		
		if(!eFile.exists()) {
			
			try {
				
				eFile.createNewFile();
				ffout = new FileOutputStream(eFile, true);

			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			wbookWrite = new XSSFWorkbook();
			
			XSSFSheet xwrSheet = wbookWrite.createSheet(sheetName);
			
			for(int i=0; i<str.length; i++) {
				
				XSSFRow xwrRow = xwrSheet.createRow(i);
				
				for(int j=0; j<str[i].length; j++) {
					
					XSSFCell xwrCell = xwrRow.createCell(j);
					xwrCell.setCellValue(str[i][j]);;
				}
			}
			
			try {
				
				wbookWrite.write(ffout);
				ffout.flush();
				ffout.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		}
		
		
		else {
			
			try {
				
				ffint = new FileInputStream(eFile);
				wbookRead = new XSSFWorkbook(ffint);

			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			XSSFSheet xSheet = null;
				
			xSheet = wbookRead.getSheet(sheetName);
				
			if(xSheet==null) {
				
				xSheet = wbookRead.createSheet(sheetName);

			}
			
			int rCount = xSheet.getPhysicalNumberOfRows();
			
			if(rCount == 0) {  rCount = rCount - 2; }
			
			int total = str.length+rCount+2;
			
			int k=0;
			
			for(int i=rCount+2; i<total;i++) {
				
				XSSFRow rowT = xSheet.createRow(i);
				
				for(int j=0; j<str[k].length; j++) {
						
					rowT.createCell(j).setCellValue(str[k][j]);
					
				}
				
				k++;
			}
			 
			 FileOutputStream output_file;
			 
			try {
				
			 output_file = new FileOutputStream(eFile);
			 wbookRead.write(output_file);

			 output_file.flush();
			 output_file.close();
			 ffint.close();
			 
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	
	}
	
	

}
