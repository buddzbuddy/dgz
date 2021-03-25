package com.webdatabase.dgz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.CurrencyExcelUpload;
import com.webdatabase.dgz.model.Currency;
import com.webdatabase.dgz.repository.CurrencyRepository;

@Service
@Transactional
public class CurrencyService {
	@Autowired
	private CurrencyRepository currencyRepository;
	
	public void save(MultipartFile file) {
		try {
			List<Currency> currencies = CurrencyExcelUpload.excelToCurrencies(file.getInputStream());
			currencyRepository.saveAll(currencies);
		} catch (Exception e) {
			throw new RuntimeException("fail to store excel data: "+ e.getMessage());
		}
	}
	
	public List<Currency> listAll(){
		return currencyRepository.findAll(Sort.by("Currency").ascending());
	}
}
