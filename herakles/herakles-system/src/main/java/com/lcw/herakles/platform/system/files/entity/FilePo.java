
package com.lcw.herakles.platform.system.files.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GL_FILE")
@EntityListeners(IdInjectionEntityListener.class)
public class FilePo extends BasePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "file_id")
    @ColumnMeta(length = "32", nullable = false, comment = "文件Id")
	private String fileId;

	@Column(name = "file_code")
    @ColumnMeta(length = "32", nullable = false, comment = "文件编码")
	private String fileCode;

	@Column(name = "file_name")
    @ColumnMeta(length = "255", nullable = false, comment = "文件名")
	private String fileName;

	@Column(name = "file_path")
    @ColumnMeta(length = "255", nullable = false, comment = "文件路径")
	private String filePath;

}
