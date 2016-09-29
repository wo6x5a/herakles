package com.lcw.herakles.platform.system.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.lcw.herakles.platform.common.constant.FileConsts;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.util.AppConfigUtil;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.system.files.dto.FileDto;

/**
 * 文件工具.
 * 
 * @author chenwulou
 *
 */
public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 文件上传.
	 * 
	 * @param file
	 *            文件
	 * @param watermark
	 *            是否水印
	 * @param markWords
	 *            水印文字
	 * @return
	 */
	public static FileDto upload(MultipartFile file, boolean isWatermark, String markWords) {
		// TODO
		FileDto fileDto = new FileDto();

		return fileDto;
	}

	/**
	 * 文件下载.
	 * 
	 * @param fileName
	 * @param filePath
	 * @param response
	 */
	public static void download(String fileName, String filePath, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition",
					"attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
			InputStream inputStream = getInputStream(filePath);
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			LOGGER.error("download file error, {}", e);
			ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, e);
		}
	}

	/**
	 * 文件删除
	 * 
	 * @param filePath
	 */
	public static void delete(String filePath) {
		try {
			File file = new File(AppConfigUtil.getConfig(FileConsts.FILE_UPLOAD_PATH), filePath);
			FileUtils.forceDeleteOnExit(file);
		} catch (IOException e) {
			LOGGER.error("delete file error, {}", e);
			ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, e);
		}
	}

	private static InputStream getInputStream(String filePath) throws FileNotFoundException {
		String realPath = AppConfigUtil.getConfig(FileConsts.FILE_UPLOAD_PATH) + "/" + filePath;
		return new FileInputStream(realPath);
	}
}
