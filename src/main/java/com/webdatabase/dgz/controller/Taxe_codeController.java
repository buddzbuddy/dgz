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

import com.webdatabase.dgz.excelExport.Taxe_codeExcelExporter;
import com.webdatabase.dgz.excelUpload.Taxe_codeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Taxe_code;
import com.webdatabase.dgz.repository.Taxe_codeRepository;
import com.webdatabase.dgz.service.Taxe_codeService;

@RestController
public class Taxe_codeController {
	@Autowired
	private Taxe_codeRepository taxe_codeRepository;
	
	@GetMapping("/taxe_codes")
	public Page<Taxe_code> getAllTaxe_code(Pageable pageable){
		return taxe_codeRepository.findAll(pageable);
	}
	
	@GetMapping("/taxe_codes{id}")
	public Optional<Taxe_code> getOneTaxe_code(@PathVariable Long id){
		return taxe_codeRepository.findById(id);
	}
	
	@PostMapping("/taxe_codes")
	public Taxe_code createTaxe_code(@Valid @RequestBody Taxe_code taxe_code) {
		return taxe_codeRepository.save(taxe_code);
	}
	
	@PutMapping("/taxe_codes{id}")
	public Taxe_code updateTaxe_code(@PathVariable Long id, @Valid @RequestBody Taxe_code taxe_codeRequest) {
		return taxe_codeRepository.findById(id)
				.map(taxe_code ->{
					taxe_code.setCode(taxe_codeRequest.getCode());
					taxe_code.setCreatedAt(taxe_codeRequest.getCreatedAt());
					taxe_code.setDetailName(taxe_codeRequest.getDetailName());
					taxe_code.setName(taxe_codeRequest.getName());
					taxe_code.setUpdatedAt(taxe_codeRequest.getUpdatedAt());
					return taxe_codeRepository.save(taxe_code);
				}).orElseThrow(()-> new ResourceNotFoundException("Taxe code not found with id "+id));
	}
	@DeleteMapping("/taxe_codes{id}")
	public ResponseEntity<?> deleteTaxe_code(@PathVariable Long id){
		return taxe_codeRepository.findById(id)
				.map(taxe_code -> {
					taxe_codeRepository.delete(taxe_code);
					return ResponseEntity.ok().build();
		}).orElseThrow(()-> new ResourceNotFoundException("Taxe code not found with id "+id));
				
	}
	
	//export to Excel
	
	@Autowired
	private Taxe_codeService taxe_codeService;
	
	@GetMapping("/taxe_codes/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=taxe_codes_" + currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Taxe_code> listTaxe_codes = taxe_codeService.listAll();
		
		Taxe_codeExcelExporter excelExporter = new Taxe_codeExcelExporter(listTaxe_codes);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/taxe_codes/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Taxe_codeExcelUpload.hasExcelFormat(file)) {
          try {
            taxe_codeService.save(file);

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
    
    @GetMapping("/taxe_codes/excel")
    public ResponseEntity<List<Taxe_code>> getAllTaxe_codes() {
        try {
          List<Taxe_code> taxe_codes = taxe_codeService.listAll();

          if (taxe_codes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(taxe_codes, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
