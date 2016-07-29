package com.lcw.herakles.platform.demo.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lcw.herakles.platform.common.constant.ApplicationConstant;
import com.lcw.herakles.platform.common.service.AbstractExcelService;
import com.lcw.herakles.platform.demo.dto.ProductDto;

/**
 * @author chenwulou
 *
 */
@Service
public class ProductInfoExcelExportService extends AbstractExcelService {

	@Override
	protected void buildExcelData(Map<String, Object> model, HSSFWorkbook workbook) {
		@SuppressWarnings("unchecked")
		List<ProductDto> list = (List<ProductDto>) model.get(ApplicationConstant.REPORT_DATA);
		int rownum = 1;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle style = this.getStyle(workbook);
		for (ProductDto dto : list) {
			// 创建行
			row = sheet.createRow(rownum);
			// 序号
			cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(rownum);
			cell.setCellStyle(style);

			// 名称
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(dto.getName());
			cell.setCellStyle(style);

			// 描述
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(dto.getDescription());
			cell.setCellStyle(style);

			rownum++;
		}
	}

}
