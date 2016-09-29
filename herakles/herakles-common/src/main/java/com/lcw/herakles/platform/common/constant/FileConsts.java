package com.lcw.herakles.platform.common.constant;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * 文件常量
 * 
 * @author chenwulou
 *
 */
public final class FileConsts {

	public static final String XLS = ".xls";
	public static final String XLSX = ".xlsx";
	public static final String DOC = ".doc";
	public static final String DOCX = ".docx";
	public static final String PDF = ".pdf";
	public static final String ZIP = ".zip";

	public static final Set<String> VALID_FILE_EXTS = Sets.newHashSet("jpg", "png", "jpeg", "gif", "bmp",
			"pdf", "doc", "docx", "xls", "xlsx");
	public static final Set<String> IMG_EXTS = Sets.newHashSet("jpg", "png", "jpeg", "gif", "bmp");

	public static final String FILE_UPLOAD_PATH = "file.upload.path";
	public static final String FILE_UPLOAD_URL= "file.upload.path";

}
