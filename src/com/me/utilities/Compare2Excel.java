package com.me.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Compare2Excel {

    private static final String firstExcelPath = SystemUtils.getUserDir() + "\\TestData.xls";
    private static final String secondExcelPath = SystemUtils.getUserDir() + "\\TestData1.xls";

    public static void main(String[] args) throws Exception {

        String[] arr = new String[]{"UK", "FR", "IT", "DE", "PT", "BE", "AT", "IE", "ES", "CH", "NL", "LU"};

        printMismatchForAllSheets();

    }

    private static void printMismatchForGivenSheetName(String sheetName) throws Exception {

        Workbook one = getWorkBook(firstExcelPath);
        Workbook two = getWorkBook(secondExcelPath);

        Map<String, String> dataFromASheetFor1stExcel = getAllDataFromExcelInFormOfListOfMaps(one, sheetName);
        Map<String, String> dataFromASheetFor2ndExcel = getAllDataFromExcelInFormOfListOfMaps(two, sheetName);

        System.out.println("******************** SHEET '" + sheetName + "' COMPARISION STARTS *****************");
        printMismatch(dataFromASheetFor1stExcel, dataFromASheetFor2ndExcel);
        System.out.println("******************** SHEET '" + sheetName + "' COMPARISION FINISHED *****************");

    }

    private static Workbook getWorkBook(String path) throws Exception {

        System.out.println("-------- Loading File " + path + " --------");

        FileInputStream inStream = FileUtils.openInputStream(FileUtils.getFile(path));

//        System.out.println("---- Loading Finished ----- ");
        return WorkbookFactory.create(inStream);
    }

    private static void printMismatchForAllSheets() throws Exception {

        Workbook one = getWorkBook(firstExcelPath);
        Workbook two = getWorkBook(secondExcelPath);

        String[] arr = new String[]{"UK", "FR", "IT", "DE", "PT", "BE", "AT", "IE", "ES", "CH", "NL", "LU"};

        Arrays.asList(arr).forEach(a -> {

            Map<String, String> dataFromASheetFor1stExcel = getAllDataFromExcelInFormOfListOfMaps(one, a);
            Map<String, String> dataFromASheetFor2ndExcel = getAllDataFromExcelInFormOfListOfMaps(two, a);

            System.out.println("\n******************** SHEET '" + a + "' COMPARISION STARTS *****************");
            printMismatch(dataFromASheetFor1stExcel, dataFromASheetFor2ndExcel);
            System.out.println("******************** SHEET " + a + " COMPARISION FINISHED *****************\n");
        });
    }

    private static Map<String, String> getAllDataFromExcelInFormOfListOfMaps(Workbook work, String sheetName) {

        System.out.println("----- Collecting Data From Sheet " + sheetName + " ------");

        Sheet s = work.getSheet(sheetName);
        Objects.requireNonNull(s, "Sorry! This Sheet Not Found The Excel File");
        Iterator<Row> itr = s.rowIterator();
        Map<String, String> m = new HashMap<>();
        int i = 0;
        while (itr.hasNext()) {
//          System.out.println("Iterating Next Row - " + i);
            Row row = itr.next();
            Cell first = row.getCell(0);
            String key = first != null ? getCellValue(first) : LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_SSS"));

            Cell second = row.getCell(1);
            String val = second != null ? getCellValue(second) : "";

            if (m.put(key, val) != null) System.err.println("Multiple Keys Found In The Same Row - " + key);
//            i++;
        }

//        System.out.println("----- Collection Finished ------");
        return m;
    }

    private static void printMismatch(Map<String, String> one, Map<String, String> two) {

        Objects.requireNonNull(one, "First Map Is Found Null");
        Objects.requireNonNull(two, "Second Map Is Found Null");

        printKeyDiff(one, two);

        for (String str : one.keySet()) {

            if (!two.containsKey(str)) {

                System.err.println("This Key Present In 1st Excel Not Found In 2nd Row Of The 1st Excel ->" + str);
            } else {

                String valueFrom1stMap = one.get(str);
                String valueFrom2ndMap = two.get(str);

//                System.out.println("----- Comparing ------ :: ");
//                System.out.println(valueFrom1stMap+"--- Versus --- "+valueFrom2ndMap);

                if (valueFrom1stMap != null && valueFrom1stMap.equals(valueFrom2ndMap)) {
//                  System.out.println("No Mismatch For : "+str);
                } else {
                    System.err.println("-------------------------------------------------------------------------");
                    System.err.println("Mismatch : Value For Key " + str + " In First File Is ->" + valueFrom1stMap);
                    System.err.println("Value For Key " + str + " In Second File Is ->" + valueFrom2ndMap);
                    System.err.println("-------------------------------------------------------------------------");
                }
            }

        }

    }

    /**
     * Retrieve {@link Cell} Value From Depending Upon Cell Type In String Form.
	 *
     * @param cell
	 * {@link Cell} Instance
	 * @return {@link String} Cell Value
	 */
    private static String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {

        if (cell == null) {
            throw new RuntimeException("[Can Fetch Value From null Cell]");
        }

        String output = "";

        switch (cell.getCellTypeEnum()) {

            case STRING:
                output = output + cell.getRichStringCellValue().getString();
                break;

            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {

                    output = output + cell.getDateCellValue();

                } else {

                    output = output + cell.getNumericCellValue();
                }
                break;

            case FORMULA:
                output = output + cell.getCellFormula();
                break;

            case BOOLEAN:
                output = output + cell.getBooleanCellValue();
                break;

            case ERROR:
                output = output + cell.getErrorCellValue();
                break;

            default:
                break;
        }

        return output;
    }

    private static void printKeyDiff(Map<String, String> one, Map<String, String> two){

        System.out.println("Printing All Keys Which Are Not Present In First File But Present In Second File");
        printDiffOfSets(one.keySet(), two.keySet());

        System.out.println("Printing All Keys Which Are Not Present In Second File But Present In First File");
        printDiffOfSets(one.keySet(), two.keySet());
    }

    private static void printDiffOfSets(Set<String> a, Set<String> b) { // Wrong. Search For How To Compare Set
        Set<String> tempOneOne = a;
        Set<String> tempOneTwo = b;
        tempOneOne.removeAll(tempOneTwo);

        if (!tempOneOne.isEmpty()) {
            System.err.println(tempOneOne);
        } else {
            System.out.println("There Is No Key Mismatch");
        }
        System.out.println("---------------------------------------------");
    }
}
