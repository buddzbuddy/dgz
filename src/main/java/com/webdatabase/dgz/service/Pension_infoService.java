package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Pension_infoExcelUpload;
import com.webdatabase.dgz.model.Pension_info;
import com.webdatabase.dgz.repository.Pension_infoRepository;

@Service
@Transactional
public class Pension_infoService {
	@Autowired
	private Pension_infoRepository pension_infoRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Pension_info> pension_infos = Pension_infoExcelUpload.excelToPension_infos(file.getInputStream());
	      pension_infoRepository.saveAll(pension_infos);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Pension_info> listAll(){
		return pension_infoRepository.findAll(Sort.by("Pension info").ascending());
	}
}
