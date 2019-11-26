package dataProviders;
	 
	        import java.io.FileInputStream;
	 
	 import java.io.FileNotFoundException;
	 
	 import java.io.FileOutputStream;
	 
	 import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
	 
	 import org.apache.poi.xssf.usermodel.XSSFRow;
	 
	 import org.apache.poi.xssf.usermodel.XSSFSheet;
	 
	 import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	 import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

	 import org.apache.poi.ss.usermodel.Sheet;

	 import org.apache.poi.ss.usermodel.Workbook;
	 
	    public class ExcelFileReader {
	 
	 private static XSSFSheet ExcelWSheet;
	 
	 private static XSSFWorkbook ExcelWBook;
	 
	 private static XSSFCell Cell;
	 
	 private static XSSFRow Row;
	 int totalRows ;
	    
	    static int totalCols;
	 
	 public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   
	 
	    String[][] tabArray = null;
	 
	    FileInputStream ExcelFile = new FileInputStream(FilePath);
	 
	    // Access the required test data sheet
	 
	    ExcelWBook = new XSSFWorkbook(ExcelFile);
	 
	    ExcelWSheet = ExcelWBook.getSheet(SheetName);
	 
	    int startRow = 1;
	 
	    int startCol = 1;
	 
	    int ci,cj;
	 
	    int totalRows = ExcelWSheet.getLastRowNum();
	    
	    int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
	 
	    tabArray=new String[totalRows][totalCols];
	 
	    ci=0;
	 
	    for (int i=startRow;i<=totalRows;i++, ci++) {              
	 
	   cj=0;
	 
	    for (int j=startCol;j<=totalCols;j++, cj++){
	    	 System.out.println("Value of ci and cj right now "+ ci+" and " + cj);
	    	 System.out.println("Value of i and j right now "+ i+" and " + j);
	    tabArray[ci][cj]=getCellData(i,j-1);
	 
	    System.out.println("Data from cell "+tabArray[ci][cj]);  
	 
	 }
	 
	 }
	 
	 
	 return(tabArray);
	 
	 }
	 
	 public static String getCellData(int RowNum, int ColNum) throws Exception {
	 
		 String CellData=null ;
		 Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	// DataFormatter d= new DataFormatter();
	 //String CellData = Cell.getStringCellValue();
	 
	 DataFormatter objDefaultFormat = new DataFormatter();
	// FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
	 FormulaEvaluator objFormulaEvaluator= ExcelWBook.getCreationHelper().createFormulaEvaluator();
	 Iterator<Row> objIterator = ExcelWSheet.rowIterator();

	 while(objIterator.hasNext()){

	     Row row = objIterator.next();
	     
	     objFormulaEvaluator.evaluate(Cell); // This will evaluate the cell, And any type of cell will return string value
	     CellData = objDefaultFormat.formatCellValue(Cell,objFormulaEvaluator);

	 }
	 return CellData;
	 
	 
	 
	 }
	 
	 

	 }
