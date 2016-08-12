
package com.lcw.herakles.platform.system.files.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.system.entity.BasePo;
import com.lcw.herakles.platform.system.entity.id.IdInjectionEntityListener;

@Entity
@Table(name = "GL_FILE")
@EntityListeners(IdInjectionEntityListener.class)
public class FilePo extends BasePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILE_ID")
	@Comment("文件Id")
	private String fileId;

	@Column(name = "FILE_CODE")
	@Comment("文件编码")
	private String fileCode;

	@Column(name = "FILE_NAME")
	@Comment("文件名")
	private String fileName;

	@Column(name = "FILE_PATH")
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
