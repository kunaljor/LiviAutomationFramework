package dataProviders;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ExcelFileReader {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;

	public synchronized static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;
		FileInputStream ExcelFile = new FileInputStream(FilePath);
		// Access the required test data sheet
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int startRow = 1;
		int startCol = 1;
		int ci, cj;
		int totalRows = ExcelWSheet.getLastRowNum();
		int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
		tabArray = new String[totalRows][totalCols];
		ci = 0;
		for (int i = startRow; i <= totalRows; i++, ci++) {
			cj = 0;
			for (int j = startCol; j <= totalCols; j++, cj++) {
				tabArray[ci][cj] = getCellData(i, j - 1);
				// System.out.println("Data from cell " + tabArray[ci][cj]);
			}
		}
		return (tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {
		String CellData = null;
		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
		Iterator<Row> objIterator = ExcelWSheet.rowIterator();
		while (objIterator.hasNext()) {
			objIterator.next();
			objFormulaEvaluator.evaluate(Cell); // This will evaluate the cell, And any type of cell will return string
												// value
			System.out.println("after formula evaluator evaluate");
			CellData = objDefaultFormat.formatCellValue(Cell, objFormulaEvaluator);
			System.out.println("after format cell value");
		}
		return CellData;

	}

}
