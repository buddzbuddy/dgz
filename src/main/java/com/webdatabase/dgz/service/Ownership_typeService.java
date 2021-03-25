package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Ownewship_typeExcelUpload;
import com.webdatabase.dgz.model.Ownership_type;
import com.webdatabase.dgz.repository.Ownership_typeRepository;

@Service
@Transactional
public class Ownership_typeService {
	@Autowired
	private Ownership_typeRepository ownership_typeRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Ownership_type> ownership_types = Ownewship_typeExcelUpload.excelToOwnership_types(file.getInputStream());
	      ownership_typeRepository.saveAll(ownership_types);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Ownership_type> listAll(){
		return ownership_typeRepository.findAll(Sort.by("Ownership type").ascending());
	}
}
