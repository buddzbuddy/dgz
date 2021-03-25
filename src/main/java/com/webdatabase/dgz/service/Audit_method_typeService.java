package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Audit_method_typeExcelUpload;
import com.webdatabase.dgz.model.Audit_method_type;
import com.webdatabase.dgz.repository.Audit_method_typeRepository;

@Service
@Transactional
public class Audit_method_typeService {
	@Autowired
	private Audit_method_typeRepository audit_method_typeRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Audit_method_type> audit_method_types = Audit_method_typeExcelUpload.excelToAudit_method_types(file.getInputStream());
			audit_method_typeRepository.saveAll(audit_method_types);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}
	
	public List<Audit_method_type> listAll(){
		return audit_method_typeRepository.findAll(Sort.by("Audit model type").ascending());
	};
}
