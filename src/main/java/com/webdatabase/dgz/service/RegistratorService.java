package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.RegistratorExcelUpload;
import com.webdatabase.dgz.model.Registrator;
import com.webdatabase.dgz.repository.RegistratorRepository;

@Service
@Transactional
public class RegistratorService {
	@Autowired
	private RegistratorRepository registratorRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Registrator> registrators = RegistratorExcelUpload.excelToRegistrators(file.getInputStream());
	      registratorRepository.saveAll(registrators);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Registrator> listAll(){
		return registratorRepository.findAll(Sort.by("Registrator").ascending());
	}
}
