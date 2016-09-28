package com.lcw.herakles.platform.common.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.lcw.herakles.platform.common.constant.ApplicationConsts;

/**
 * @author chenwulou
 *
 */
public abstract class AbstractExcelService extends AbstractXlsView {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExcelService.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = String.valueOf(model.get(ApplicationConsts.REPORT_FILE_NAME));
		String tempPath = String.valueOf(model.get(ApplicationConsts.REPORT_TEMP_PATH));
		InputStream ins = AbstractExcelService.class.getClassLoader().getResourceAsStream(tempPath);
		workbook = new HSSFWorkbook(ins);
		try {
			buildExcelData(model, workbook);
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			LOGGER.error("error in export xls, {}", e);
		}
	}

	protected abstract void buildExcelData(Map<String, Object> model, Workbook workbook) throws Exception;

	/**
	 * 获取样式
	 * 
	 * @param workbook
	 * @return
	 */
	public CellStyle getStyle(Workbook workbook) {
		// 设置字体;
		Font font = workbook.createFont();
		// 设置字体大小;
		font.setFontHeightInPoints((short) 12);
		// 设置字体名字;
		font.setFontName("宋体");
		// 设置样式;
		CellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}

}
