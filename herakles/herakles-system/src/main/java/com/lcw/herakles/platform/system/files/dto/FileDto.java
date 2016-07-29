package com.lcw.herakles.platform.system.files.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseDto;

public class FileDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Comment("文件Id")
	private String fileId;

	@Comment("文件编码")
	private String fileCode;

	@Comment("文件名")
	private String fileName;

	@Comment("文件路径")
	private String filePath;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
