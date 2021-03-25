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

import com.webdatabase.dgz.excelExport.CounterpartExcelExporter;
import com.webdatabase.dgz.excelUpload.CounterpartExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Counterpart;
import com.webdatabase.dgz.repository.CounterpartRepository;
import com.webdatabase.dgz.service.CounterpartService;


@RestController
public class CounterpartController {
	
	@Autowired
	private CounterpartRepository counterpartRepository;
	
	@GetMapping("/counterparts")
    public Page<Counterpart> getAll(Pageable pageable) {
        return counterpartRepository.findAll(pageable);
    }

    @GetMapping("/counterparts/{id}")
    public Optional<Counterpart> getOne(@PathVariable Long id) {
        return counterpartRepository.findById(id);
    }


    @PostMapping("/counterparts")
    public Counterpart create(@Valid @RequestBody Counterpart counterpart) {
        return counterpartRepository.save(counterpart);
    }

    @PutMapping("/counterparts/{id}")
    public Counterpart update(@PathVariable Long id,
                                   @Valid @RequestBody Counterpart counterpartRequest) {
        return counterpartRepository.findById(id)
                .map(counterpart -> {
                	counterpart.setName(counterpartRequest.getName());
                	counterpart.setAddress(counterpartRequest.getAddress());
                	counterpart.setBankAccountNo(counterpartRequest.getBankAccountNo());
                	counterpart.setComments(counterpartRequest.getComments());
                	counterpart.setContactData(counterpartRequest.getContactData());
                	counterpart.setCounterpart_type(counterpartRequest.getCounterpart_type());
                	counterpart.setCreatedAt(counterpartRequest.getCreatedAt());
                	counterpart.setUpdatedAt(counterpartRequest.getUpdatedAt());
                    return counterpartRepository.save(counterpart);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/counterparts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return counterpartRepository.findById(id)
                .map(counterpart -> {
                	counterpartRepository.delete(counterpart);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
    
    // export to Excel
    
    @Autowired
	private CounterpartService counterpartService;
	
	@GetMapping("/counterparts/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=counterparts_"+ currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Counterpart> listCounterparts = counterpartService.listAll();
		
		CounterpartExcelExporter excelExport = new CounterpartExcelExporter(listCounterparts);
		excelExport.export(response);
	}
	
	//  upload from Excel
	
	@PostMapping("counterparts/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
		String message = "";
		if (CounterpartExcelUpload.hasExcelFormat(file)) {
			try {
				counterpartService.save(file);
				
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
	
	@GetMapping("/counterparts/excel")
	public ResponseEntity<List<Counterpart>> getAllCounterparts(){
		try {
			List<Counterpart> counterparts = counterpartService.listAll();
			
			if (counterparts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(counterparts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
