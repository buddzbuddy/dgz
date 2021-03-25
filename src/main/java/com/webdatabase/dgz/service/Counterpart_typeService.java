package com.webdatabase.dgz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Counterpart_typeExcelUpload;
import com.webdatabase.dgz.model.Counterpart_type;
import com.webdatabase.dgz.repository.CounterpartTypeRepository;

@Service
@Transactional
public class Counterpart_typeService {
	
	@Autowired
	private CounterpartTypeRepository counterpartTypeRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Counterpart_type> counterpart_types = Counterpart_typeExcelUpload.excelToCounterpart_types(file.getInputStream());
			counterpartTypeRepository.saveAll(counterpart_types);
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}
	
	public List<Counterpart_type> listAll(){
		return counterpartTypeRepository.findAll(Sort.by("Counterpart type").ascending());
		
	}
}
