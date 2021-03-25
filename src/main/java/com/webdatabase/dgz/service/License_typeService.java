package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.License_typeExcelUpload;
import com.webdatabase.dgz.model.License_type;
import com.webdatabase.dgz.repository.License_typeRepository;

@Service
@Transactional
public class License_typeService {
	@Autowired
	private License_typeRepository license_typeRepository;
	
	 public void save(MultipartFile file) {
	    try {
	      List<License_type> license_types = License_typeExcelUpload.excelToLicense_types(file.getInputStream());
	      license_typeRepository.saveAll(license_types);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	 }
	
	public List<License_type> listAll(){
		return license_typeRepository.findAll(Sort.by("License type").ascending());
	}
}
