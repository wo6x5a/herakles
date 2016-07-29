package com.lcw.herakles.platform.system.files.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.system.files.entity.FilePo;

/**
 * @author chenwulou
 *
 */
public interface FileRepository extends JpaRepository<FilePo, String>, JpaSpecificationExecutor<FilePo> {

	FilePo findByFilePath(String filePath);

	List<FilePo> findByFileCode(String fileCode);
}
