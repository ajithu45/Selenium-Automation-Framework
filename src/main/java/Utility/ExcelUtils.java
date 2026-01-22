package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Converter;
import org.yaml.snakeyaml.emitter.EmitterException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//uyi

public class ExcelUtils {

	public static String[] dataArray1;
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	public static String firstRange[], secondRange[], thirdRange[];
//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path, String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		try {

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = Cell.getStringCellValue();

			return CellData;

		} catch (Exception e) {
			return "";
		}
	}

	public static String getCellRange(int RowNum, int ColNum) throws Exception {
		String CellData;
		try {

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
				CellData = Cell.getCellFormula().toString();
			} else {
				CellData = Cell.getStringCellValue().toString();
			}
		} catch (Exception e) {
			return "";
		}
		return CellData;
	}

//This method is to write in the Excel cell, Row num and Col num are the parameters

	public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {

		try {
			Row = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(
					ConfigurationInputdata.Excel_Relative_Path + ConfigurationInputdata.File_TestData);
			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);
		}
	}

	public static void setCellData_dev(String Result, int RowNum, int ColNum) throws Exception {

		try {
			Row = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(
					ConfigurationInputdata.RelativePath + ConfigurationInputdata.File_TestData_dev);
			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);
		}
	}

	public static void setCellData_viewer(String Result, int RowNum, int ColNum) throws Exception {

		try {
			Row = ExcelWSheet.getRow(RowNum);

			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(Result);

			} else {

				Cell.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(
					ConfigurationInputdata.RelativePath + ConfigurationInputdata.Viewer_File_TestData);
			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);
		}
	}

	public static int getFiledDataRowCount() {
		try {
			int firstRowCount = ExcelWSheet.getPhysicalNumberOfRows();
			return firstRowCount;

		} catch (Exception e) {
			return 0;
		}
	}

	public static int getFiledDatafirstcolumnCount() {
		int rowCount = 0;
		try {

			System.out.println(ExcelWSheet.getLastRowNum());
			for (int i = 0; i <= ExcelWSheet.getLastRowNum(); i++) {

				Cell = ExcelWSheet.getRow(i).getCell(0);
				if (Cell == null || Cell.getCellType() == Cell.CELL_TYPE_BLANK) {
					rowCount = rowCount + 1;
					Log.info("Row count is" + rowCount);
				} else {
					String CellData = Cell.getStringCellValue();
					if (CellData.equals("End")) {
						Log.info("All rows data completed, untill End line in excel");
						System.out.println("All rows data completed, untill End line in excel");
						// break;
					} else {
						rowCount = rowCount + 1;
						Log.info("Row count is" + rowCount);
					}
				}

//          if(Cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA)
//          {
//        	  CellData = Cell.getCellFormula().toString();
//          }
//          else
//          {
//        	  CellData = Cell.getStringCellValue().toString(); 
//          }

			}
		} catch (Exception e) {

			return rowCount;
		}
		return rowCount;
	}

	public static String[] getdata(String range) {

		String Range = range;
		String[] dataArray;
		AreaReference aref = new AreaReference(ExcelWSheet.getSheetName() + "!" + Range + "");
		CellReference[] crefs = aref.getAllReferencedCells();
		DataFormatter formatter = new DataFormatter();
		dataArray = new String[crefs.length];
		for (int i = 0; i < crefs.length; i++) {
			XSSFSheet s = (XSSFSheet) ExcelWBook.getSheet(crefs[i].getSheetName());
			Row r = s.getRow(crefs[i].getRow());

			Cell c = r.getCell(crefs[i].getCol());

			dataArray[i] = c.getRichStringCellValue().getString();

		}
		return dataArray;
	}

	/*
	 * public static void getdata1(String range) {
	 * 
	 * 
	 * String Range=range; AreaReference aref = new
	 * AreaReference(ExcelWSheet.getSheetName() + "!"+Range+""); CellReference[]
	 * crefs = aref.getAllReferencedCells(); DataFormatter formatter = new
	 * DataFormatter(); dataArray1 =new String[crefs.length]; for (int i=0;
	 * i<crefs.length; i++) { XSSFSheet s = (XSSFSheet)
	 * ExcelWBook.getSheet(crefs[i].getSheetName()); Row r =
	 * s.getRow(crefs[i].getRow());
	 * 
	 * Cell c = r.getCell(crefs[i].getCol());
	 * 
	 * dataArray1[i] =c.getRichStringCellValue().getString();
	 * 
	 * } }
	 */
	public static void getrangevalues(String range, int Row, int Column, boolean isthird) {
		Cell = ExcelWSheet.getRow(Row).getCell(Column);
		Log.info("Here getting the cell value based on row and column");
		String formulaValue = range;
		if (Cell != null && (Cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA)) {
			CellReference cellRef = new CellReference(Cell);
			System.out.println(cellRef.getRow());
			Row r = ExcelWSheet.getRow(cellRef.getRow());
			if (r != null) {

				Cell c = r.getCell(cellRef.getCol());
				System.out.println(c.getCellFormula());
				int ite = 0;
				for (Cell cell : r) {
					int flag;
					System.out.println(cell.toString());
					if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {

						int iteration = ite++;
						flag = iteration + 1;// This flag value for to differentiate, if there is two range values
						Log.info("found the flag value is" + flag);
						formulaValue = cell.getCellFormula().toString();// Here fetching the value from formula/Range
						Log.info("Got the forumula value is" + formulaValue);
						if (flag == 1) {
							if (!isthird) {
								firstRange = getdata(formulaValue);
								Log.info("Found the first range is" + firstRange);
							} else {
								thirdRange = getdata(formulaValue);
								Log.info("Found the thirdRange is" + thirdRange);
							}
						}
						if (flag == 2) {
							secondRange = getdata(formulaValue);
							Log.info("Found the second range is" + secondRange);
						}
						if (flag == 3) {
							thirdRange = getdata(formulaValue);
							Log.info("Found the thirdRange is" + thirdRange);
						}
					}
				}
			}
		}
	}

	public static void getrangevalues_temp(String range, int Row, int Column) {
		Cell = ExcelWSheet.getRow(Row).getCell(Column);
		String rowmun = Cell.getCellFormula();
		String metadata = range;
		DataFormatter formatter = new DataFormatter();
		CellReference cellRef = new CellReference(Cell);
		System.out.println(cellRef.getRow());

		for (Row row : ExcelWSheet) {

			for (Cell cell : row) {
				// get the text that appears in the cell by getting the cell value and applying
				// any data formats (Date, 0.00, 1.23e9, $1.23, etc)
				String text = formatter.formatCellValue(cell);
				// Alternatively, get the value and format it yourself
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						// System.out.println(cell.getDateCellValue());
					} else {
						// System.out.println(cell.getNumericCellValue());
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					// System.out.println(cell.getBooleanCellValue());

					break;
				case XSSFCell.CELL_TYPE_FORMULA:
					// System.out.println(cell.getCellFormula());
					metadata = cell.getCellFormula().toString();
					getdata(metadata);
					/*
					 * for (int i=0; i<dataArray.length; i++) { System.out.println(dataArray[i]);
					 * 
					 * }
					 */

					break;
				case XSSFCell.CELL_TYPE_BLANK:
					// System.out.println();
					break;
				default:
					// System.out.println();
					break;

				}
			}
		}

	}

	public static String getrangeformula() {
		String range = null;
		ExcelWSheet = ExcelWBook.getSheet("filesToUpload");
		DataFormatter formatter = new DataFormatter();
		CellReference cellRef = new CellReference(1, 2);
		System.out.println(cellRef.getRow());
		for (Row row : ExcelWSheet) {
			for (Cell cell : row) {

				switch (cell.getCellType()) {

				case XSSFCell.CELL_TYPE_FORMULA:
					System.out.println(cell.getCellFormula());
					range = cell.getCellFormula().toString();

					break;

				default:
					System.out.println();

				}
			}
		}
		return range;

	}

	public static Number getNumaricCellValue(int RowNum, int ColNum) {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			double CellData = Cell.getNumericCellValue();
			DecimalFormat pattern = new DecimalFormat("#,#,#,#,#,#,#,#,#,#");
			NumberFormat testNumberFormat = NumberFormat.getNumberInstance();
			String mob = testNumberFormat.format(CellData);
			Number n = pattern.parse(mob);
			return n;
		} catch (Exception e) {
			return 0;
		}
	}
}

/*
 * package Utility;
 * 
 * import java.io.File; import java.io.FileInputStream; import
 * java.io.FileNotFoundException; import java.io.FileOutputStream; import
 * java.text.DecimalFormat; import java.text.NumberFormat;
 * 
 * import org.apache.poi.ss.usermodel.DataFormatter; import
 * org.apache.poi.ss.usermodel.DateUtil; import
 * org.apache.poi.ss.util.AreaReference; import
 * org.apache.poi.ss.util.CellRangeAddress; import
 * org.apache.poi.ss.util.CellReference; import
 * org.apache.poi.xssf.usermodel.XSSFCell; import
 * org.apache.poi.xssf.usermodel.XSSFRow; import
 * org.apache.poi.xssf.usermodel.XSSFSheet; import
 * org.apache.poi.xssf.usermodel.XSSFWorkbook; import org.testng.Converter;
 * import org.yaml.snakeyaml.emitter.EmitterException; import
 * org.apache.poi.ss.usermodel.Cell; import org.apache.poi.ss.usermodel.Row;
 * //uyi
 * 
 * public class ExcelUtils {
 * 
 * public static String[] dataArray1; private static XSSFSheet ExcelWSheet;
 * 
 * private static XSSFWorkbook ExcelWBook;
 * 
 * private static XSSFCell Cell;
 * 
 * private static XSSFRow Row;
 * 
 * public static String firstRange[],secondRange[],thirdRange[]; //This method
 * is to set the File path and to open the Excel file, Pass Excel Path and
 * Sheetname as Arguments to this method
 * 
 * public static void setExcelFile(String Path,String SheetName) throws
 * Exception {
 * 
 * try {
 * 
 * // Open the Excel file
 * 
 * FileInputStream ExcelFile = new FileInputStream(Path);
 * 
 * //Access the required test data sheet
 * 
 * ExcelWBook = new XSSFWorkbook(ExcelFile);
 * 
 * ExcelWSheet = ExcelWBook.getSheet(SheetName); } catch (Exception e){ throw
 * (e); } }
 * 
 * //This method is to read the test data from the Excel cell, in this we are
 * passing parameters as Row num and Col num
 * 
 * public static String getCellData(int RowNum, int ColNum) throws Exception{
 * 
 * try{
 * 
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
 * 
 * String CellData = Cell.getStringCellValue();
 * 
 * return CellData;
 * 
 * }catch (Exception e){ return""; } } public static String getCellRange(int
 * RowNum, int ColNum) throws Exception{ String CellData; try{
 * 
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum); if(Cell.getCellType() ==
 * XSSFCell.CELL_TYPE_FORMULA) { CellData = Cell.getCellFormula().toString(); }
 * else { CellData = Cell.getStringCellValue().toString(); } }catch (Exception
 * e) { return""; } return CellData; }
 * 
 * //This method is to write in the Excel cell, Row num and Col num are the
 * parameters
 * 
 * public static void setCellData(String Result, int RowNum, int ColNum) throws
 * Exception {
 * 
 * try{ Row = ExcelWSheet.getRow(RowNum);
 * 
 * Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
 * 
 * if (Cell == null) {
 * 
 * Cell = Row.createCell(ColNum);
 * 
 * Cell.setCellValue(Result);
 * 
 * } else {
 * 
 * Cell.setCellValue(Result);
 * 
 * }
 * 
 * // Constant variables Test Data path and Test Data file name
 * 
 * FileOutputStream fileOut = new
 * FileOutputStream(ConfigurationInputdata.RelativePath +
 * ConfigurationInputdata.File_TestData);
 * 
 * ExcelWBook.write(fileOut);
 * 
 * fileOut.flush();
 * 
 * fileOut.close();
 * 
 * }catch(Exception e){
 * 
 * throw (e); } }
 * 
 * public static int getFiledDataRowCount() { try{ int firstRowCount =
 * ExcelWSheet.getPhysicalNumberOfRows(); return firstRowCount;
 * 
 * 
 * }catch (Exception e){ return 0; } } public static int
 * getFiledDatafirstcolumnCount() { int rowCount=0; try{
 * 
 * 
 * System.out.println(ExcelWSheet.getLastRowNum()); for(int
 * i=1;i<=ExcelWSheet.getLastRowNum();i++) {
 * 
 * Cell = ExcelWSheet.getRow(i).getCell(0); String CellData;
 * if(Cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) { CellData =
 * Cell.getCellFormula().toString(); } else { CellData =
 * Cell.getStringCellValue().toString(); }
 * 
 * if(CellData.equals("End")) {
 * Log.info("All rows data completed, untill End line in excel");
 * System.out.println("All rows data completed, untill End line in excel");
 * break; }else { rowCount=rowCount+1; Log.info("Row count is"+rowCount); } }
 * }catch (Exception e){ return 0; } return rowCount; } public static String[]
 * getdata(String range) {
 * 
 * 
 * String Range=range; String[] dataArray; AreaReference aref = new
 * AreaReference(ExcelWSheet.getSheetName() + "!"+Range+""); CellReference[]
 * crefs = aref.getAllReferencedCells(); DataFormatter formatter = new
 * DataFormatter(); dataArray =new String[crefs.length]; for (int i=0;
 * i<crefs.length; i++) { XSSFSheet s = (XSSFSheet)
 * ExcelWBook.getSheet(crefs[i].getSheetName()); Row r =
 * s.getRow(crefs[i].getRow());
 * 
 * Cell c = r.getCell(crefs[i].getCol());
 * 
 * dataArray[i] =c.getRichStringCellValue().getString();
 * 
 * } return dataArray; } public static void getdata1(String range) {
 * 
 * 
 * String Range=range; AreaReference aref = new
 * AreaReference(ExcelWSheet.getSheetName() + "!"+Range+""); CellReference[]
 * crefs = aref.getAllReferencedCells(); DataFormatter formatter = new
 * DataFormatter(); dataArray1 =new String[crefs.length]; for (int i=0;
 * i<crefs.length; i++) { XSSFSheet s = (XSSFSheet)
 * ExcelWBook.getSheet(crefs[i].getSheetName()); Row r =
 * s.getRow(crefs[i].getRow());
 * 
 * Cell c = r.getCell(crefs[i].getCol());
 * 
 * dataArray1[i] =c.getRichStringCellValue().getString();
 * 
 * } } public static void getrangevalues(String range,int Row, int
 * Column,boolean isthird) { Cell = ExcelWSheet.getRow(Row).getCell(Column);
 * Log.info("Here getting the cell value based on row and column"); String
 * formulaValue=range; if(Cell!=null &&
 * (Cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA)) { CellReference cellRef =
 * new CellReference(Cell); System.out.println(cellRef.getRow()); Row r =
 * ExcelWSheet.getRow(cellRef.getRow()); if (r != null) {
 * 
 * Cell c = r.getCell(cellRef.getCol()); System.out.println(c.getCellFormula());
 * int ite = 0; for (Cell cell : r) { int flag;
 * System.out.println(cell.toString()); if(cell.getCellType() ==
 * XSSFCell.CELL_TYPE_FORMULA) {
 * 
 * int iteration=ite++; flag=iteration+1;//This flag value for to differentiate,
 * if there is two range values Log.info("found the flag value is"+flag);
 * formulaValue=cell.getCellFormula().toString();//Here fetching the value from
 * formula/Range Log.info("Got the forumula value is"+formulaValue); if(flag==1)
 * { if(!isthird) { firstRange=getdata(formulaValue);
 * Log.info("Found the first range is"+firstRange); } else {
 * thirdRange=getdata(formulaValue);
 * Log.info("Found the thirdRange is"+thirdRange); } } if(flag==2) {
 * secondRange=getdata(formulaValue);
 * Log.info("Found the second range is"+secondRange); } if(flag==3) {
 * thirdRange=getdata(formulaValue);
 * Log.info("Found the thirdRange is"+thirdRange); } } } } } } public static
 * void getrangevalues_temp(String range,int Row, int Column) { Cell =
 * ExcelWSheet.getRow(Row).getCell(Column); String rowmun=Cell.getCellFormula();
 * String metadata=range; DataFormatter formatter = new DataFormatter();
 * CellReference cellRef = new CellReference(Cell);
 * System.out.println(cellRef.getRow());
 * 
 * for (Row row : ExcelWSheet) {
 * 
 * for (Cell cell : row) { // get the text that appears in the cell by getting
 * the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
 * String text = formatter.formatCellValue(cell); // Alternatively, get the
 * value and format it yourself switch (cell.getCellType()) { case
 * XSSFCell.CELL_TYPE_STRING: break; case XSSFCell.CELL_TYPE_NUMERIC: if
 * (DateUtil.isCellDateFormatted(cell)) {
 * //System.out.println(cell.getDateCellValue()); } else {
 * //System.out.println(cell.getNumericCellValue()); } break; case
 * XSSFCell.CELL_TYPE_BOOLEAN: //System.out.println(cell.getBooleanCellValue());
 * 
 * break; case XSSFCell.CELL_TYPE_FORMULA: //
 * System.out.println(cell.getCellFormula());
 * metadata=cell.getCellFormula().toString(); getdata(metadata); for (int i=0;
 * i<dataArray.length; i++) { System.out.println(dataArray[i]);
 * 
 * }
 * 
 * break; case XSSFCell.CELL_TYPE_BLANK: //System.out.println(); break; default:
 * //System.out.println(); break;
 * 
 * } } }
 * 
 * } public static String getrangeformula() { String range = null; ExcelWSheet =
 * ExcelWBook.getSheet("filesToUpload"); DataFormatter formatter = new
 * DataFormatter(); CellReference cellRef = new CellReference(1,2);
 * System.out.println(cellRef.getRow()); for (Row row : ExcelWSheet) { for (Cell
 * cell : row) {
 * 
 * 
 * switch (cell.getCellType()) {
 * 
 * case XSSFCell.CELL_TYPE_FORMULA: System.out.println(cell.getCellFormula());
 * range=cell.getCellFormula().toString();
 * 
 * 
 * 
 * break;
 * 
 * default: System.out.println();
 * 
 * } } } return range;
 * 
 * } public static Number getNumaricCellValue(int RowNum, int ColNum) { try{
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum); double CellData =
 * Cell.getNumericCellValue(); DecimalFormat pattern = new
 * DecimalFormat("#,#,#,#,#,#,#,#,#,#"); NumberFormat testNumberFormat =
 * NumberFormat.getNumberInstance(); String mob =
 * testNumberFormat.format(CellData); Number n = pattern.parse(mob); return n;
 * }catch (Exception e){ return 0; } } }
 * 
 * package Utility;
 * 
 * import java.io.File; import java.io.FileInputStream; import
 * java.io.FileNotFoundException; import java.io.FileOutputStream; import
 * java.text.DecimalFormat; import java.text.NumberFormat;
 * 
 * import org.apache.poi.ss.usermodel.DataFormatter; import
 * org.apache.poi.ss.usermodel.DateUtil; import
 * org.apache.poi.ss.util.AreaReference; import
 * org.apache.poi.ss.util.CellRangeAddress; import
 * org.apache.poi.ss.util.CellReference; import
 * org.apache.poi.xssf.usermodel.XSSFCell; import
 * org.apache.poi.xssf.usermodel.XSSFRow; import
 * org.apache.poi.xssf.usermodel.XSSFSheet; import
 * org.apache.poi.xssf.usermodel.XSSFWorkbook; import org.testng.Converter;
 * import org.apache.poi.ss.usermodel.Cell; import
 * org.apache.poi.ss.usermodel.Row;
 * 
 * 
 * public class ExcelUtils {
 * 
 * public static String[] dataArray1; private static XSSFSheet ExcelWSheet;
 * 
 * private static XSSFWorkbook ExcelWBook;
 * 
 * private static XSSFCell Cell;
 * 
 * private static XSSFRow Row;
 * 
 * public static String firstRange[],secondRange[],thirdRange[]; //This method
 * is to set the File path and to open the Excel file, Pass Excel Path and
 * Sheetname as Arguments to this method
 * 
 * public static void setExcelFile(String Path,String SheetName) throws
 * Exception {
 * 
 * try {
 * 
 * // Open the Excel file
 * 
 * FileInputStream ExcelFile = new FileInputStream(Path);
 * 
 * //Access the required test data sheet
 * 
 * ExcelWBook = new XSSFWorkbook(ExcelFile);
 * 
 * ExcelWSheet = ExcelWBook.getSheet(SheetName); } catch (Exception e){ throw
 * (e); } }
 * 
 * //This method is to read the test data from the Excel cell, in this we are
 * passing parameters as Row num and Col num
 * 
 * public static String getCellData(int RowNum, int ColNum) throws Exception{
 * 
 * try{
 * 
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
 * 
 * String CellData = Cell.getStringCellValue();
 * 
 * return CellData;
 * 
 * }catch (Exception e){ return""; } } public static String getCellRange(int
 * RowNum, int ColNum) throws Exception{ String CellData; try{
 * 
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum); if(Cell.getCellType() ==
 * XSSFCell.CELL_TYPE_FORMULA) { CellData = Cell.getCellFormula().toString(); }
 * else { CellData = Cell.getStringCellValue().toString(); } }catch (Exception
 * e) { return""; } return CellData; }
 * 
 * //This method is to write in the Excel cell, Row num and Col num are the
 * parameters
 * 
 * public static void setCellData(String Result, int RowNum, int ColNum) throws
 * Exception {
 * 
 * try{ Row = ExcelWSheet.getRow(RowNum);
 * 
 * Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
 * 
 * if (Cell == null) {
 * 
 * Cell = Row.createCell(ColNum);
 * 
 * Cell.setCellValue(Result);
 * 
 * } else {
 * 
 * Cell.setCellValue(Result);
 * 
 * }
 * 
 * // Constant variables Test Data path and Test Data file name
 * 
 * FileOutputStream fileOut = new
 * FileOutputStream(ConfigurationInputdata.Path_TestData +
 * ConfigurationInputdata.File_TestData);
 * 
 * ExcelWBook.write(fileOut);
 * 
 * fileOut.flush();
 * 
 * fileOut.close();
 * 
 * }catch(Exception e){
 * 
 * throw (e); } }
 * 
 * public static int getFiledDataRowCount() { try{ int firstRowCount =
 * ExcelWSheet.getPhysicalNumberOfRows(); return firstRowCount;
 * 
 * 
 * }catch (Exception e){ return 0; } } public static int
 * getFiledDatafirstcolumnCount() { int rowCount=0; try{
 * 
 * 
 * System.out.println(ExcelWSheet.getLastRowNum()); for(int
 * i=0;i<=ExcelWSheet.getLastRowNum();i++) {
 * 
 * Cell = ExcelWSheet.getRow(i).getCell(0); String
 * CellData=Cell.getStringCellValue(); if(Cell.getCellType() ==
 * XSSFCell.CELL_TYPE_FORMULA) { CellData = Cell.getCellFormula().toString(); }
 * else { CellData = Cell.getStringCellValue().toString(); }
 * 
 * if(CellData.equals("End")) {
 * Log.info("All rows data completed, untill End line in excel");
 * System.out.println("All rows data completed, untill End line in excel");
 * break; }else { rowCount=rowCount+1; Log.info("Row count is"+rowCount); } }
 * }catch (Exception e){ return 0; } return rowCount; } public static String[]
 * getdata(String range) {
 * 
 * 
 * String Range=range; String[] dataArray; AreaReference aref = new
 * AreaReference(ExcelWSheet.getSheetName() + "!"+Range+""); CellReference[]
 * crefs = aref.getAllReferencedCells(); DataFormatter formatter = new
 * DataFormatter(); dataArray =new String[crefs.length]; for (int i=0;
 * i<crefs.length; i++) { XSSFSheet s = (XSSFSheet)
 * ExcelWBook.getSheet(crefs[i].getSheetName()); Row r =
 * s.getRow(crefs[i].getRow());
 * 
 * Cell c = r.getCell(crefs[i].getCol());
 * 
 * dataArray[i] =c.getRichStringCellValue().getString();
 * 
 * } return dataArray; } public static void getdata1(String range) {
 * 
 * 
 * String Range=range; AreaReference aref = new
 * AreaReference(ExcelWSheet.getSheetName() + "!"+Range+""); CellReference[]
 * crefs = aref.getAllReferencedCells(); DataFormatter formatter = new
 * DataFormatter(); dataArray1 =new String[crefs.length]; for (int i=0;
 * i<crefs.length; i++) { XSSFSheet s = (XSSFSheet)
 * ExcelWBook.getSheet(crefs[i].getSheetName()); Row r =
 * s.getRow(crefs[i].getRow());
 * 
 * Cell c = r.getCell(crefs[i].getCol());
 * 
 * dataArray1[i] =c.getRichStringCellValue().getString();
 * 
 * } } public static void getrangevalues(String range,int Row, int
 * Column,boolean isthird) { Cell = ExcelWSheet.getRow(Row).getCell(Column);
 * Log.info("Here getting the cell value based on row and column"); String
 * formulaValue=range; if(Cell!=null &&
 * (Cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA)) { CellReference cellRef =
 * new CellReference(Cell); System.out.println(cellRef.getRow()); Row r =
 * ExcelWSheet.getRow(cellRef.getRow()); if (r != null) {
 * 
 * Cell c = r.getCell(cellRef.getCol()); System.out.println(c.getCellFormula());
 * int ite = 0; for (Cell cell : r) { int flag;
 * System.out.println(cell.toString()); if(cell.getCellType() ==
 * XSSFCell.CELL_TYPE_FORMULA) {
 * 
 * int iteration=ite++; flag=iteration+1;//This flag value for to differentiate,
 * if there is two range values Log.info("found the flag value is"+flag);
 * formulaValue=cell.getCellFormula().toString();//Here fetching the value from
 * formula/Range Log.info("Got the forumula value is"+formulaValue); if(flag==1)
 * { if(!isthird) { firstRange=getdata(formulaValue);
 * Log.info("Found the first range is"+firstRange); } else {
 * thirdRange=getdata(formulaValue);
 * Log.info("Found the thirdRange is"+thirdRange); } } if(flag==2) {
 * secondRange=getdata(formulaValue);
 * Log.info("Found the second range is"+secondRange); } } } } } } public static
 * void getrangevalues_temp(String range,int Row, int Column) { Cell =
 * ExcelWSheet.getRow(Row).getCell(Column); String rowmun=Cell.getCellFormula();
 * String metadata=range; DataFormatter formatter = new DataFormatter();
 * CellReference cellRef = new CellReference(Cell);
 * System.out.println(cellRef.getRow());
 * 
 * for (Row row : ExcelWSheet) {
 * 
 * for (Cell cell : row) { // get the text that appears in the cell by getting
 * the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
 * String text = formatter.formatCellValue(cell); // Alternatively, get the
 * value and format it yourself switch (cell.getCellType()) { case
 * XSSFCell.CELL_TYPE_STRING: break; case XSSFCell.CELL_TYPE_NUMERIC: if
 * (DateUtil.isCellDateFormatted(cell)) {
 * //System.out.println(cell.getDateCellValue()); } else {
 * //System.out.println(cell.getNumericCellValue()); } break; case
 * XSSFCell.CELL_TYPE_BOOLEAN: //System.out.println(cell.getBooleanCellValue());
 * 
 * break; case XSSFCell.CELL_TYPE_FORMULA: //
 * System.out.println(cell.getCellFormula());
 * metadata=cell.getCellFormula().toString(); getdata(metadata); for (int i=0;
 * i<dataArray.length; i++) { System.out.println(dataArray[i]);
 * 
 * }
 * 
 * break; case XSSFCell.CELL_TYPE_BLANK: //System.out.println(); break; default:
 * //System.out.println(); break;
 * 
 * } } }
 * 
 * } public static String getrangeformula() { String range = null; ExcelWSheet =
 * ExcelWBook.getSheet("filesToUpload"); DataFormatter formatter = new
 * DataFormatter(); CellReference cellRef = new CellReference(1,2);
 * System.out.println(cellRef.getRow()); for (Row row : ExcelWSheet) { for (Cell
 * cell : row) {
 * 
 * 
 * switch (cell.getCellType()) {
 * 
 * case XSSFCell.CELL_TYPE_FORMULA: System.out.println(cell.getCellFormula());
 * range=cell.getCellFormula().toString();
 * 
 * 
 * 
 * break;
 * 
 * default: System.out.println();
 * 
 * } } } return range;
 * 
 * } public static Number getNumaricCellValue(int RowNum, int ColNum) { try{
 * Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum); double CellData =
 * Cell.getNumericCellValue(); DecimalFormat pattern = new
 * DecimalFormat("#,#,#,#,#,#,#,#,#,#"); NumberFormat testNumberFormat =
 * NumberFormat.getNumberInstance(); String mob =
 * testNumberFormat.format(CellData); Number n = pattern.parse(mob); return n;
 * }catch (Exception e){ return 0; } } }
 */