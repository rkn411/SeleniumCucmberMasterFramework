package com.amphora.framework.support.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDriver {
	private InputStream iFileReader;
	private OutputStream oFileWriter;
	private XSSFWorkbook oExcelWorkbook;
	private String sWorkbookStatus;
	FormulaEvaluator evaluator;

	private void setNullValues() {
		this.sWorkbookStatus = "not opened";
		this.iFileReader = null;
		this.oFileWriter = null;
		this.oExcelWorkbook = null;
	}

	public ExcelDriver() {
		setNullValues();
	}

	public void createNewExcelWorkbook(String sFileName) {
		try {
			if (sFileName.isEmpty()) {
				throw new Exception("File name is blank....");
			}

			if (new File(sFileName).exists()) {
				throw new Exception("Specified File already exists....File=" + sFileName);
			}

			this.oFileWriter = new FileOutputStream(sFileName);
			this.oExcelWorkbook = new XSSFWorkbook();
			this.oExcelWorkbook.write(this.oFileWriter);
			this.oFileWriter.close();
			setNullValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openExcelWorkbook(String fileName) {
		try {
			String sFileName=fileName+".xlsx";
			if (sFileName.isEmpty()) {
				throw new Exception("File name is blank...");
			}
			if (!new File(sFileName).exists()) {
				throw new Exception("Specified File dows not exists...File=" + sFileName);
			}
			this.iFileReader = new FileInputStream(sFileName);
			this.oExcelWorkbook = new XSSFWorkbook(iFileReader);
			// this.oExcelWorkbook = WorkbookFactory.create(this.iFileReader);
			this.sWorkbookStatus = "opened";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createNewSheet(String sSheetName) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank....");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened! Please call 'openExcelWorkbook'before any operation....");
			}

			Sheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet != null) {
				throw new Exception("Specified Sheet already exists.sheet Name=" + sSheetName);
			}
			this.oExcelWorkbook.createSheet(sSheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCellValue(String sSheetName, int iRow, int iColumn) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("Sheetname is blank...");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("workbook is not opened! Please call'openExcelWorkbook'before any operation...");
			}

			if ((iRow < 1) || (iColumn < 1)) {
				throw new Exception("Invalid Row/column Index. Row/column index starts from 1.");
			}

			XSSFSheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				throw new Exception("Sheet not Found");
			}

			Row oRow = oSheet.getRow(iRow - 1);

			if (oRow == null) {
				return "";
			}
			Cell oCell = oRow.getCell(iColumn - 1);

			if (oCell == null) {
				return "";
			}

			String cellStringValue = oCell.getStringCellValue();
			return cellStringValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public Object getAnyCellValue(String sSheetName, int iRow, int iColumn) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank....");
			}
			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("invalid Row/Column Index. Row/Column Index Starts from 1.");
			}
			XSSFSheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				throw new Exception("Sheet not found");
			}
			Row oRow = oSheet.getRow(iRow - 1);
			if (oRow == null) {
				return "";
			}

			Cell oCell = oRow.getCell(iColumn - 1);
			if (oCell == null) {
				return "";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setCellValue(String sSheetName, int iRow, int iColumn, String sValue) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}
			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Inavlid Row/Column index. Row/Column index starts from 1.");
			}
			Sheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				createNewSheet(sSheetName);
				oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			}
			Row oRow = oSheet.getRow(iRow - 1);
			if (oRow == null) {
				oSheet.createRow(iRow - 1);
				oRow = oSheet.getRow(iRow - 1);
			}
			Cell oCell = oRow.getCell(iColumn - 1);
			if (oCell == null) {
				oRow.createCell(iColumn - 1);
				oCell = oRow.getCell(iColumn - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCountInSheet(String sSheetName) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened ! please call'openExcelWorkbook' before any operation...");
			}
			XSSFSheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				throw new Exception("Specified Sheet does not exists. Sheet Name=" + sSheetName);
			}
			return oSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getColumnCountInRow(String sSheetName, int iRow) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened! Please call 'openExcelWorkbook' before any opertion...");
			}
			if (iRow < 1) {
				throw new Exception("Row index starts from 1...");
			}
			XSSFSheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				throw new Exception("Specified sheet does not exist. Sheet Name=" + sSheetName);
			}

			Row oRow = oSheet.getRow(iRow - 1);
			if (oRow == null) {
				return 0;
			}
			return oRow.getLastCellNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getRowNumberForValue(String sSheetName, String sCellData) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Exception is not opened! Please Call 'openExcelWorkbook' before any operation...");
			}

			int iRows = getRowCountInSheet(sSheetName);
			for (int i = 2; i <= iRows; i++) {
				if (getCellValue(sSheetName, i, 1).toString().equals(sCellData)) {
					return i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getColumnNumberForValue(String sSheetName, String sCellData) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}
			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Exception is not opened! Please Call 'openExcelWorkbook' before any operation...");
			}
			int iColumn = getColumnCountInRow(sSheetName, 1);
			for (int i = 1; i <= iColumn; i++) {
				if (getCellValue(sSheetName, 1, i).toString().equals(sCellData))
					return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	private HashMap<String, String> getRowRecord(String sSheetName, String sRowValue) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}

			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened! Please call 'openExcelWorkbook' before any operation...");
			}
			int iRows = getRowCountInSheet(sSheetName);
			int iRow = 0;
			for (int i = 2; i <= iRows; i++) {
				if (getCellValue(sSheetName, i, 1).toString().equals(sRowValue)) {
					iRow = i;
					break;
				}
			}

			if (iRow == 0) {
				return null;
			}

			int iColumns = getColumnCountInRow(sSheetName, 1);
			HashMap<String, String> rowRecord = new HashMap<String, String>();
			for (int i = 2; i <= iColumns; i++) {
				rowRecord.put(getCellValue(sSheetName, 1, i).toString().trim(),
						getCellValue(sSheetName, iRow, i).toString().trim());
			}
			return rowRecord;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getFirstSheetName() {
		try {
			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened! Please call 'openExcelworkbook' before any operation....");
			}
			return this.oExcelWorkbook.getSheetName(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean verifyValueExistsInAnyExcelCell(String sSheetName, String key) {
		try {
			if (sSheetName.isEmpty()) {
				throw new Exception("SheetName is blank...");
			}
			if ((this.oExcelWorkbook == null) || (this.sWorkbookStatus.equalsIgnoreCase("not opened"))) {
				throw new Exception("Workbook is not opened!Please call 'openExcelWorkbook' before any operation...");
			}

			Sheet oSheet = this.oExcelWorkbook.getSheet(sSheetName);
			if (oSheet == null) {
				throw new Exception("Sheet not found");
			}
			for (int row = 1; row < getRowCountInSheet(sSheetName); row++) {
				for (int col = 1; col <= getColumnCountInRow(sSheetName, row); col++) {
					if (getCellValue(sSheetName, row, col).equals(key))
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public HashMap<String,String> getRowData(String workbookName,String sheetName,String rowKey){
		String testDataPath=System.getProperty("user.dir")+"/src/test/resources/testdata/";
		openExcelWorkbook(testDataPath+workbookName);
		return getRowRecord(sheetName, rowKey);
	}
}
