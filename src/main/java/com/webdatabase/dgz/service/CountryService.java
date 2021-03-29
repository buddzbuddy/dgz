package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.CountryExcelUpload;
import com.webdatabase.dgz.model.Country;
import com.webdatabase.dgz.repository.CountryRepository;

@Service
@Transactional
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Country> countries = CountryExcelUpload.excelToCountries(file.getInputStream());
			countryRepository.saveAll(countries);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data" + e.getMessage());
		}
	}
	
	public List<Country> listAll() {
		return countryRepository.findAll(Sort.by("name").ascending());
	}
}
