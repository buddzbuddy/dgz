package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.CounterpartExcelUpload;
import com.webdatabase.dgz.model.Counterpart;
import com.webdatabase.dgz.repository.CounterpartRepository;

@Service
@Transactional
public class CounterpartService {
	@Autowired
	private CounterpartRepository counterpartRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Counterpart> counterparts = CounterpartExcelUpload.excelToCounterparts(file.getInputStream());
			counterpartRepository.saveAll(counterparts);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}
	
	public List<Counterpart> listAll(){
		return counterpartRepository.findAll(Sort.by("Counterpart").ascending());
	}
}
