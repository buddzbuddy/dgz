package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.AppealExcelUpload;
import com.webdatabase.dgz.model.Appeal;
import com.webdatabase.dgz.repository.AppealRepository;

@Service
@Transactional
public class AppealService {
	@Autowired
	private AppealRepository aRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Appeal> appeals = AppealExcelUpload.excelToAppeals(file.getInputStream());
			aRepository.saveAll(appeals);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data" + e.getMessage());
		}
	}
	
	public List<Appeal> listAll(){
		return aRepository.findAll(Sort.by("Appeal").ascending());
	}
}
