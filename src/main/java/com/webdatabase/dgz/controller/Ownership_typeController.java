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

import com.webdatabase.dgz.excelExport.Ownership_typeExcelExporter;
import com.webdatabase.dgz.excelUpload.Ownewship_typeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Ownership_type;
import com.webdatabase.dgz.repository.Ownership_typeRepository;
import com.webdatabase.dgz.service.Ownership_typeService;

@RestController
public class Ownership_typeController {
	@Autowired
	private Ownership_typeRepository ownership_typeRepository;
	
	@GetMapping("ownership_types")
	public Page<Ownership_type> getAllOwnership_type(Pageable pageable){
		return ownership_typeRepository.findAll(pageable);
	}
	
	@GetMapping("ownership_types{id}")
	public Optional<Ownership_type> getOneOwnership_type(@PathVariable Long id){
		return ownership_typeRepository.findById(id);
	}
	
	@PostMapping("ownership_types")
	public Ownership_type createOwnership_type(@Valid @RequestBody Ownership_type ownership_type) {
		return ownership_typeRepository.save(ownership_type);
	}
	
	@PutMapping("ownership_types{id}")
	public Ownership_type updateOwnership_type(@PathVariable Long id, @Valid @RequestBody Ownership_type ownership_typeRequest) {
		return ownership_typeRepository.findById(id)
				.map(ownership_type -> {
					ownership_type.setCreatedAt(ownership_typeRequest.getCreatedAt());
					ownership_type.setName(ownership_typeRequest.getName());
					ownership_type.setUpdatedAt(ownership_typeRequest.getUpdatedAt());
					return ownership_typeRepository.save(ownership_type);
				}).orElseThrow(()-> new ResourceNotFoundException("Ownership type not found with id "+id));
	}
	
	@DeleteMapping("ownership_types{id}")
	public ResponseEntity<?> deleteOwnership_type(@PathVariable Long id){
		return ownership_typeRepository.findById(id)
				.map(ownership_type -> {
					ownership_typeRepository.delete(ownership_type);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Ownership type not found with id "+id));
	}
	
	@Autowired
	private Ownership_typeService ownership_typeService;
	
	@GetMapping("/ownership_types/export/excel")
	public void exportExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ownership_types_"+currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Ownership_type> listOwnership_types = ownership_typeService.listAll();
		
		Ownership_typeExcelExporter excelExporter = new Ownership_typeExcelExporter(listOwnership_types);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/ownership_types/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Ownewship_typeExcelUpload.hasExcelFormat(file)) {
          try {
            ownership_typeService.save(file);

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
    
    @GetMapping("/ownership_types/excel")
    public ResponseEntity<List<Ownership_type>> getAllOwnership_types() {
        try {
          List<Ownership_type> ownership_types = ownership_typeService.listAll();

          if (ownership_types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(ownership_types, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
