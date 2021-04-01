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

import com.webdatabase.dgz.excelExport.Procuring_entityExcelExporter;
import com.webdatabase.dgz.excelUpload.Procuring_entityExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.repository.Procuring_entityRepository;
import com.webdatabase.dgz.service.Procuring_entityService;

@RestController
public class Procuring_entityController {
	@Autowired
	private Procuring_entityRepository procuring_entityRepository;
	
	@GetMapping("/procuring_entities")
	public Page<Procuring_entity> getAllProcuring_entity(Pageable pageable){
		return procuring_entityRepository.findAll(pageable);
	}
	
	@GetMapping("/procuring_entities/{procuring_entityId}")
	public Optional<Procuring_entity> getOneProcuring_entity(@PathVariable Long procuring_entityId){
		return procuring_entityRepository.findById(procuring_entityId);
	}
	
	@PostMapping("/procuring_entities")
	public Procuring_entity createProcuring_entity(@Valid @RequestBody Procuring_entity procuring_entity) {
		return procuring_entityRepository.save(procuring_entity);
	}
	
	@PutMapping("/procuring_entities/{procuring_entityId}")
	public Procuring_entity updateProcuring_entity(@PathVariable Long procuring_entityId, @Valid @RequestBody Procuring_entity procuring_entityRequest) {
		return procuring_entityRepository.findById(procuring_entityId)
				.map(procuring_entity -> {
					procuring_entity.setAddress(procuring_entityRequest.getAddress());
					procuring_entity.setContactData(procuring_entityRequest.getContactData());
					procuring_entity.setInn(procuring_entityRequest.getInn());
					procuring_entity.setName(procuring_entityRequest.getName());
					return procuring_entityRepository.save(procuring_entity);
				}).orElseThrow(()-> new ResourceNotFoundException("Procuring entity not found with id "+procuring_entityId));
	}
	@DeleteMapping("/procuring_entities/{procuring_entityId}")
	public ResponseEntity<?> deleteProcuring_entity(@PathVariable Long procuring_entityId){
		return procuring_entityRepository.findById(procuring_entityId)
				.map(procuring_e -> {
					procuring_entityRepository.delete(procuring_e);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Procuring entity not found with id "+procuring_entityId));
	}
	
	// export to Excel
	
	@Autowired
	private Procuring_entityService procuring_entityService;
	
	@GetMapping("/procuring_entities/export/excel")
	public void excelExporter(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=procuring_entities_" + currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Procuring_entity> listProcuring_entities = procuring_entityService.listAll();
		
		Procuring_entityExcelExporter excelExporter = new Procuring_entityExcelExporter(listProcuring_entities);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/procuring_entities/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Procuring_entityExcelUpload.hasExcelFormat(file)) {
          try {
            procuring_entityService.save(file);

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
    
    @GetMapping("/procuring_entities/excel")
    public ResponseEntity<List<Procuring_entity>> getAllProcuring_entities() {
        try {
          List<Procuring_entity> procuring_entities = procuring_entityService.listAll();

          if (procuring_entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(procuring_entities, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
