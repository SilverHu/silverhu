package com.common.utils.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.utils.file.FileUtils;

/**
 * excel工具类
 * 
 * @author SilverHu
 * @dependency org.apache.poi 3.14
 */
public class ExcelUtil {

	private final static Logger log = LoggerFactory.getLogger(FileUtils.class);
	private final static String DEFAULT_SHEETNAME = "Sheet1";

	public static ExcelBean readExcel(File excel, String sheetName) {
		// 验证excel是否合法
		if (excel == null || !excel.exists() || !validateName(excel.getName())) {
			return null;
		}
		if (StringUtils.isBlank(sheetName)) {
			sheetName = DEFAULT_SHEETNAME;
		}

		try (InputStream in = new FileInputStream(excel); Workbook workbook = getWorkbook(excel, in)) {
			Sheet sheet = workbook.getSheet(sheetName);

			// 得到Excel最后一行的index，没有为-1
			int maxRow = sheet.getLastRowNum();

			// 得到Excel的列数，没有为0
			int maxCell = 0;
			if (maxRow >= 0 && sheet.getRow(0) != null) {
				maxCell = sheet.getRow(0).getLastCellNum();
			}
			// 读取数据
			List<List<Object>> rows = new ArrayList<>();
			for (int i = 0; i <= maxRow; i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				List<Object> cols = new ArrayList<>();
				for (int j = 0; j < maxCell; j++) {
					cols.add(getCellValue(row.getCell(j)));
				}
				rows.add(cols);
			}
			return new ExcelBean(excel, sheetName, rows);
		} catch (FileNotFoundException e) {
			log.error("excel is not exists, e : {}", e);
		} catch (IOException e) {
			log.error("read excel is error, e : {}", e);
		}
		return null;
	}

	/**
	 * batch write data to excel
	 * 
	 * @param excelBean
	 * 
	 */
	public static boolean writeExcel(ExcelBean excelBean) {
		if (excelBean == null || excelBean.getExcel() == null || excelBean.getRows() == null) {
			return false;
		}
		if (!validateName(excelBean.getExcel().getName())) {
			log.error("excel is null, or is not xls! excelName : {}", excelBean.getExcel().getName());
			return false;
		}
		if (StringUtils.isBlank(excelBean.getSheetName())) {
			excelBean.setSheetName(DEFAULT_SHEETNAME);
		}

		try (InputStream in = new FileInputStream(excelBean.getExcel());
				Workbook workbook = getWorkbook(excelBean.getExcel(), in);
				OutputStream out = new FileOutputStream(excelBean.getExcel());) {

			// 创建工作表
			Sheet sheet = workbook.getSheet(excelBean.getSheetName());
			if (sheet == null) {
				sheet = workbook.createSheet(excelBean.getSheetName());
			}

			// 写入数据，rownum从0开始，没有也是0
			int rownum = sheet.getLastRowNum();
			if (rownum == 0 && sheet.getRow(0) == null) {
				rownum = -1;
			}

			// 写入内容
			if (CollectionUtils.isNotEmpty(excelBean.getRows())) {
				for (int i = 0; i < excelBean.getRows().size(); i++) {
					Row row = sheet.createRow(rownum + i + 1);
					List<Object> cols = excelBean.getRows().get(i);
					for (int j = 0; j < cols.size(); j++) {
						Object col = cols.get(j);
						if (col instanceof Boolean) {
							row.createCell(j).setCellValue((boolean) col);
						} else if (col instanceof Double) {
							row.createCell(j).setCellValue((double) col);
						} else if (col instanceof Calendar) {
							row.createCell(j).setCellValue((Calendar) col);
						} else if (col instanceof Date) {
							row.createCell(j).setCellValue((Date) col);
						} else if (col instanceof RichTextString) {
							row.createCell(j).setCellValue((RichTextString) col);
						} else {
							row.createCell(j).setCellValue(String.valueOf(col));
						}

					}
				}
			}
			workbook.write(out);
			return true;
		} catch (FileNotFoundException e) {
			log.error("file is not found!", e);
		} catch (IOException e) {
			log.error("write date to excel is failed!", e);
		}
		return false;
	}

	/**
	 * create excel file, if file is exists, will remove file, and create a new file
	 * 
	 * @param excelBean
	 * 
	 */
	public static boolean createExcel(ExcelBean excelBean) {
		if (excelBean == null || excelBean.getExcel() == null || !validateName(excelBean.getExcel().getName())) {
			return false;
		}
		if (StringUtils.isBlank(excelBean.getSheetName())) {
			excelBean.setSheetName(DEFAULT_SHEETNAME);
		}

		// 存在则删除
		if (excelBean.getExcel().exists()) {
			FileUtils.removeFile(excelBean.getExcel());
		}

		try (OutputStream out = new FileOutputStream(excelBean.getExcel());
				Workbook workbook = getWorkbook(excelBean.getExcel(), null)) {
			// 写入表头
			Sheet sheet = workbook.createSheet(excelBean.getSheetName());
			if (CollectionUtils.isNotEmpty(excelBean.getHeaders())) {
				Row row = sheet.createRow(0);
				for (int i = 0; i < excelBean.getHeaders().size(); i++) {
					row.createCell(i).setCellValue(excelBean.getHeaders().get(i));
				}
			}
			workbook.write(out);
			return true;
		} catch (FileNotFoundException e) {
			log.error("error is not found!", e);
		} catch (IOException e) {
			log.error("create excel is failed!", e);
		}
		return false;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param excel
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		Object value = null;
		// 以下是判断数据的类型
		switch (cell.getCellTypeEnum()) {
		case NUMERIC: // 数字
			value = cell.getNumericCellValue();
			break;
		case STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case BOOLEAN: // Boolean
			value = cell.getBooleanCellValue();
			break;
		case BLANK: // 空值
			value = "";
			break;
		case ERROR: // 故障
			value = "非法字符";
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * 根据版本号获取对应的Workbook
	 * 
	 * @param excel
	 * @param in
	 *            不为空时，使用in创建
	 * @return
	 * @throws IOException
	 */
	private static Workbook getWorkbook(File excel, InputStream in) throws IOException {
		Workbook workbook = null;
		if (excel.getName().endsWith(".xls")) {
			// excel 2003
			if (in == null) {
				workbook = new HSSFWorkbook();
			} else {
				workbook = new HSSFWorkbook(in);
			}
		} else if (excel.getName().endsWith(".xlsx")) {
			// excel 2007
			if (in == null) {
				workbook = new XSSFWorkbook();
			} else {
				workbook = new XSSFWorkbook(in);
			}
		}
		return workbook;
	}

	/**
	 * 验证excel文件名是否合法
	 * 
	 * @param excelName
	 * @return
	 */
	private static boolean validateName(String excelName) {
		if (StringUtils.isBlank(excelName) || (!excelName.endsWith(".xls") && !excelName.endsWith(".xlsx"))) {
			return false;
		} else {
			return true;
		}
	}
}
