package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.IndustryExcelUpload;
import com.webdatabase.dgz.model.Industry;
import com.webdatabase.dgz.repository.IndustryRepository;

@Service
@Transactional
public class IndustryService {
	@Autowired
	private IndustryRepository industryRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Industry> industries = IndustryExcelUpload.excelToIndustries(file.getInputStream());
	      industryRepository.saveAll(industries);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Industry> listAll(){
		return industryRepository.findAll(Sort.by("Industry").ascending());
	}
}
