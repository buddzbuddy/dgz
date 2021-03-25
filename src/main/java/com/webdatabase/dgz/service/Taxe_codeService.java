package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Taxe_codeExcelUpload;
import com.webdatabase.dgz.model.Taxe_code;
import com.webdatabase.dgz.repository.Taxe_codeRepository;

@Service
@Transactional
public class Taxe_codeService {
	@Autowired
	private Taxe_codeRepository taxe_codeRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Taxe_code> taxe_codes = Taxe_codeExcelUpload.excelToTaxe_codes(file.getInputStream());
	      taxe_codeRepository.saveAll(taxe_codes);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Taxe_code> listAll(){
		return taxe_codeRepository.findAll(Sort.by("Taxe code").ascending());
	}
}
