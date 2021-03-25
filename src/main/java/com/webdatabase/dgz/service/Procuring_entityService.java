package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Procuring_entityExcelUpload;
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.repository.Procuring_entityRepository;

@Service
@Transactional
public class Procuring_entityService {
	@Autowired
	private Procuring_entityRepository procuring_entityRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Procuring_entity> procuring_entities = Procuring_entityExcelUpload.excelToProcuring_entities(file.getInputStream());
	      procuring_entityRepository.saveAll(procuring_entities);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Procuring_entity> listAll(){
		return procuring_entityRepository.findAll(Sort.by("Procuring entity").ascending());
	}
}
