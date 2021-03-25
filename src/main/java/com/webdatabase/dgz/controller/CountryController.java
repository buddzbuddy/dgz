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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.model.Country;
import com.webdatabase.dgz.repository.CountryRepository;
import com.webdatabase.dgz.service.CountryService;
import com.webdatabase.dgz.excelExport.CountryExcelExporter;
import com.webdatabase.dgz.excelUpload.CountryExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;


@RestController
public class CountryController {
	
	@Autowired
	private CountryRepository countriesRepository;
	
	@GetMapping("/countries")
	public Page<Country> getAllCountries(Pageable pageable){
		return countriesRepository.findAll(pageable);
	}
	
	@GetMapping("/countries{Id}")
	public Optional<Country> getOneCountry(@PathVariable Long id){
		return countriesRepository.findById(id);
	}
	
	@PostMapping("/countries")
	public Country createCountry(@Valid @RequestBody Country country) {
		return countriesRepository.save(country);
	}
	
	@PutMapping("/countries{id}")
	public Country updateCountry(@PathVariable Long id, @Valid @RequestBody Country countryRequest) {
		return countriesRepository.findById(id)
				.map(country -> {
					country.setName(countryRequest.getName());
					return countriesRepository.save(country);
				}).orElseThrow(() -> new ResourceNotFoundException("Country not found with id "+ id));
	}
	
	@DeleteMapping("/countries{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable Long id){
		return countriesRepository.findById(id)
			.map(country -> {
				countriesRepository.delete(country);
				return ResponseEntity.ok().build();
			}).orElseThrow(() -> new ResourceNotFoundException("Country not found with id "+ id));
	}
	
	// export to excel
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping("/countries/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application.ostet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headervalue = "attachment; filename=countries_"+ currentDateTime + ".xslx";
		response.setHeader(headerKey, headervalue);
		
		List<Country> listCountries = countryService.listAll();
		
		CountryExcelExporter excelExport = new CountryExcelExporter(listCountries);
		
		excelExport.export(response);
	}
	
	//upload from excel
	
	@PostMapping("/countries/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
		String message = "";
		
		if(CountryExcelUpload.hasExcelFormat(file)) {
			try {
				countryService.save(file);
				
				message = "Файл успешно загружен: "+ file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	@GetMapping("/countries/excel")
	public ResponseEntity<List<Country>> getAllCountries(){
		try {
			List<Country> countries = countryService.listAll();
			
			if(countries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(countries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
