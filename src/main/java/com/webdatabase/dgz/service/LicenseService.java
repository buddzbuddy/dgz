package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.LicenseExcelUpload;
import com.webdatabase.dgz.model.License;
import com.webdatabase.dgz.repository.LicenseRepository;


@Service
@Transactional
public class LicenseService {
	@Autowired
	private LicenseRepository licenseRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<License> licenses = LicenseExcelUpload.excelToLicenses(file.getInputStream());
	      licenseRepository.saveAll(licenses);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<License> listAll(){
		return licenseRepository.findAll(Sort.by("License").ascending());
	}
}
