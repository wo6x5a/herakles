package com.lcw.herakles.platform.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.enumeration.EErrorCode;

/**
 * Excel util
 * 
 * @author chenwulou
 *
 */
public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * TODO 生成 xlsx 文件
	 * 
	 * @param result
	 *            map 中key是行名。value是行的值
	 * @param url
	 *            生成文件所在的文件夹
	 * @param fileName
	 *            生成文件的文件名
	 * @return void
	 */
	@SuppressWarnings("resource")
	public static void createExcelByFileName(Map<String, List<String>> result, String fileName) {
		StringBuilder filePath = new StringBuilder();
		// TODO 未完成
		filePath.append("");
		filePath.append(fileName);
		File file = new File(filePath.toString());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			// TODO 对07版本以上的进行写入操作
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(fileName);
			int i = 0;
			for (Entry<String, List<String>> entry : result.entrySet()) {
				Row row = sheet.createRow(i++);
				int j = 0;
				row.createCell(j).setCellValue(entry.getKey());
				for (String str : entry.getValue()) {
					j++;
					row.createCell(j).setCellValue(str);
				}
			}
			workbook.write(out);
			System.out.println("文件写入成功");
		} catch (FileNotFoundException e) {
			LOGGER.error("ExcelUtil.createExcelByFileName(),{}.", e);
		} catch (IOException e) {
			LOGGER.error("ExcelUtil.createExcelByFileName(),{}.", e);
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				LOGGER.error("ExcelUtil.createExcelByFileName(),{}.", e);
			}
		}
	}

	/**
	 * 读取EXCEL
	 * 
	 * @param input
	 * @param fileName:文件名
	 * @param cellsize:excel有几列
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<Object[]> readExcel(InputStream input, String fileName, int cellsize) {
		List<Object[]> result = new ArrayList<Object[]>();
		boolean cellTypeBlank = false;// 判断EXCEL是否是空行
		try {
			if (null == input) {
				ErrorUtils.throwBizException(EErrorCode.COMM_ERROR_HINTS, "输入流不能为空");
			}
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (fileName.endsWith("xlsx")) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				/**
				 * 获取行号，从第1行开始，所以row.getRowNum()不等于0
				 */
				if (row.getRowNum() != 0) {
					Object[] cellObj = new Object[cellsize];
					Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
					while (cells.hasNext()) {
						Cell cell = cells.next();
						/**
						 * 得到一行的每一列，迭代列cell.getColumnIndex()得到列号从0开始
						 */
						switch (cell.getCellType()) { // 根据cell中的类型来输出数据
						case HSSFCell.CELL_TYPE_NUMERIC:
							String str = String.valueOf(cell.getNumericCellValue());
							str = str.substring(0, str.indexOf('.'));
							cellObj[cell.getColumnIndex()] = str;
							break;
						case HSSFCell.CELL_TYPE_STRING:
							cellObj[cell.getColumnIndex()] = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							cellObj[cell.getColumnIndex()] = cell.getBooleanCellValue();
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							cellObj[cell.getColumnIndex()] = cell.getCellFormula();
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							cellTypeBlank = true;
							break;
						default:
							break;
						}
					}
					if (!cellTypeBlank) {
						result.add(cellObj);
						cellTypeBlank = false;
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("ExcelUtil.readExcel(),{}.", e);
		}
		return result;
	}
}
