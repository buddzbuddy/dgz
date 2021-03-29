package com.webdatabase.dgz.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelExport.CurrencyExcelExporter;
import com.webdatabase.dgz.excelUpload.CurrencyExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Currency;
import com.webdatabase.dgz.repository.CurrencyRepository;
import com.webdatabase.dgz.service.CurrencyService;

@RestController
public class CurrencyController {
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@GetMapping("/currencies")
	public Page<Currency> getAllCurrencies(Pageable pageable){
		return currencyRepository.findAll(pageable);
	}
	
	@GetMapping("/currencies{id}")
	public Optional<Currency> getOneCurrency(@PathVariable Long id){
		return currencyRepository.findById(id);
	}
	
	@PostMapping("/currencies")
	public Currency createCurrency(@Valid @RequestBody Currency currency) {
		return currencyRepository.save(currency);
	}
	
	@PutMapping("/currencies{id}")
	public Currency updateCurrency(@PathVariable Long id, @Valid @RequestBody Currency currencyRequest) {
		return currencyRepository.findById(id)
				.map(currency -> {
					currency.setName(currencyRequest.getName());
					currency.setCreatedAt(currencyRequest.getCreatedAt());
					currency.setUpdatedAt(currencyRequest.getUpdatedAt());
					return currencyRepository.save(currency);
				}).orElseThrow(()-> new ResourceNotFoundException("Currency not found with id "+ id));
	}
	
	@DeleteMapping("/currencies{id}")
	public ResponseEntity<?> deleteCurrency(@PathVariable Long id){
		return currencyRepository.findById(id)
				.map(currency -> {
					currencyRepository.delete(currency);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Currency not found with id "+ id));
	}
	
	//export to Excel
	
	@Autowired
	private CurrencyService currencyService;
	
	@GetMapping("/currencies/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=currency_"+ currentDate +".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Currency> listCurrencies = currencyService.listAll();
		
		CurrencyExcelExporter excelExport = new CurrencyExcelExporter(listCurrencies);
		
		excelExport.export(response);
	}
	
	// upload from Excel
	@PostMapping("/currencies/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
		String message = "";
		
		if (CurrencyExcelUpload.hasExcelFormat(file)) {
			try {
				currencyService.save(file);
				
				message = "Файл успешно загружен: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Не удалось загрузить файл: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	
	@GetMapping("/currencies/excel")
	public ResponseEntity<List<Currency>> getAllCurrencies(){
		try {
			List<Currency> currencies = currencyService.listAll();
			
			if (currencies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(currencies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
