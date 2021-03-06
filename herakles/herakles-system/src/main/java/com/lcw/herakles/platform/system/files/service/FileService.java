package com.lcw.herakles.platform.system.files.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.system.files.dto.FileDto;
import com.lcw.herakles.platform.system.files.entity.FilePo;
import com.lcw.herakles.platform.system.files.repository.FileRepository;

/**
 * @author chenwulou
 *
 */
@Service
public class FileService extends BaseService{

	@Autowired
	private FileRepository fileRepository;

	@Transactional
	public FileDto upload(MultipartFile file, String fileCode, boolean isWatermark, String markWords) {
		FileDto fileDto = new FileDto();
//		fileDto = FileUtil.upload(file, isWatermark, markWords);
		FilePo filePo = new FilePo();
		filePo.setFileCode(fileCode);
		filePo.setFileName(fileDto.getFileName());
		filePo.setFilePath(fileDto.getFilePath());
        super.setCreateInfo(filePo);
		fileRepository.save(filePo);
		fileDto.setFileCode(fileCode);
		return fileDto;
	}

	@Transactional(readOnly = true)
	public void download(String filePath, HttpServletResponse response) {
		FilePo filePo = fileRepository.findByFilePath(filePath);
		if (filePo == null) {
			ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "文件不存在");
		}
//		FileUtil.download(filePo.getFileName(), filePo.getFilePath(), response);
	}

	@Transactional
	public void removeFile(String filePath) {
		FilePo filePo = fileRepository.findByFilePath(filePath);
		if (filePo == null) {
			ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "文件不存在");
		}
		fileRepository.delete(filePo);
//		FileUtil.delete(filePo.getFilePath());
	}
}
