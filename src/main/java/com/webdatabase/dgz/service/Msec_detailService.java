package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Msec_detailExcelUpload;
import com.webdatabase.dgz.model.Msec_detail;
import com.webdatabase.dgz.repository.Msec_detailRepository;


@Service
@Transactional
public class Msec_detailService {
	@Autowired
	private Msec_detailRepository msec_detailRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Msec_detail> msec_details = Msec_detailExcelUpload.excelToMsec_details(file.getInputStream());
	      msec_detailRepository.saveAll(msec_details);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Msec_detail> listAll(){
		return msec_detailRepository.findAll(Sort.by("Msec detail").ascending());
	}
}
