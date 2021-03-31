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

import com.webdatabase.dgz.excelExport.CounterpartTypeExcelExporter;
import com.webdatabase.dgz.excelUpload.Counterpart_typeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Counterpart_type;
import com.webdatabase.dgz.repository.CounterpartTypeRepository;
import com.webdatabase.dgz.service.Counterpart_typeService;

@RestController
public class CounterpartTypeController {
	
	@Autowired
	private CounterpartTypeRepository counterpartTypeRepository;
	
	@GetMapping("/counterpart_types")
	public Page<Counterpart_type> getAllCounterpartType(Pageable pageable){
		return counterpartTypeRepository.findAll(pageable);
	}
	
	@GetMapping("/counterpart_types{id}")
	public Optional<Counterpart_type> getOneCounterpartType(@PathVariable Long id){
		return counterpartTypeRepository.findById(id);
	}
	
	@PostMapping("/counterpart_types")
	public Counterpart_type createCounterpartType(@Valid @RequestBody Counterpart_type counterpart_type) {
		return counterpartTypeRepository.save(counterpart_type);
	}
	
	@PutMapping("/counterpart_types{id}")
	public Counterpart_type updateCounterpartType(@PathVariable Long id, @Valid @RequestBody Counterpart_type counterpart_typeRequest) {
		return counterpartTypeRepository.findById(id)
				.map(counterpart_type -> {
					counterpart_type.setName(counterpart_typeRequest.getName());
					return counterpartTypeRepository.save(counterpart_type);
				}).orElseThrow(() -> new ResourceNotFoundException("Counterpart type not found with id "+ id));
	}
	
	@DeleteMapping("/counterpart_types{id}")
	public ResponseEntity<?> deleteCounterpartType(@PathVariable Long id){
		return counterpartTypeRepository.findById(id)
			.map(counterpart_type -> {
				counterpartTypeRepository.delete(counterpart_type);
				return ResponseEntity.ok().build();
			}).orElseThrow(() -> new ResourceNotFoundException("Counterpart type not found with id "+ id));
	}
	
	//export to excel
	
	@Autowired
	private Counterpart_typeService counterpart_typeService;
	
	@GetMapping("/counterpart_types/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=counterpart_type_"+currentDateTime+".xslx";
		response.setHeader(headerKey, headerValue);
		
		List<Counterpart_type> listCounterpart_types = counterpart_typeService.listAll();
		
		CounterpartTypeExcelExporter excelExport = new CounterpartTypeExcelExporter(listCounterpart_types);
		
		excelExport.export(response);
	}
	
	//upload from Excel
	
	@PostMapping("/counterpart_types/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
		String message = "";
		
		if(Counterpart_typeExcelUpload.hasExcelFormat(file)) {
			try {
				counterpart_typeService.save(file);
				
				message = "Файл успешно загружен: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
				
	}
	
	@GetMapping("/counterpart_types/excel")
	public ResponseEntity<List<Counterpart_type>> getAllCounterpart_types(){
		try {
			List<Counterpart_type> counterpart_types = counterpart_typeService.listAll();
			if (counterpart_types.isEmpty()) {
				return new ResponseEntity<>(counterpart_types, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(counterpart_types, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
