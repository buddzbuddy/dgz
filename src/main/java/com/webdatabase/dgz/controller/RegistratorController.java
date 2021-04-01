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

import com.webdatabase.dgz.excelExport.RegistratorExcelExporter;
import com.webdatabase.dgz.excelUpload.RegistratorExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Registrator;
import com.webdatabase.dgz.repository.RegistratorRepository;
import com.webdatabase.dgz.service.RegistratorService;

@RestController
public class RegistratorController {
	@Autowired
	private RegistratorRepository registratorRepository;
	
	@GetMapping("/registrators")
	public Page<Registrator> getAllRegistrator(Pageable pageable){
		return registratorRepository.findAll(pageable);
	}
	
	@GetMapping("/registrators/{id}")
	public Optional<Registrator> getOneRegistrator (@PathVariable Long id){
		return registratorRepository.findById(id);
	}
	
	@PostMapping("/registrators")
	public Registrator createRegistrator(@Valid @RequestBody Registrator registrator) {
		return registratorRepository.save(registrator);
	}
	@PutMapping("/registrators/{id}")
	public Registrator updateRegistrator(@PathVariable Long id, @Valid @RequestBody Registrator registratorRequest) {
		return registratorRepository.findById(id)
				.map(registrator ->{
					registrator.setContactData(registratorRequest.getContactData());
					registrator.setCounterpart(registratorRequest.getCounterpart());
					registrator.setName(registratorRequest.getName());
					return registratorRepository.save(registrator);
				}).orElseThrow(()-> new ResourceNotFoundException("Registrator not found with id "+id));
	}
	
	@DeleteMapping("/registrators/{id}")
	public ResponseEntity<?> deleteRegistrator(@PathVariable Long id){
		return registratorRepository.findById(id)
				.map(registrator ->{
					registratorRepository.delete(registrator);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Registrator not found with id "+id));
	}
	
	//export to Excel
	
	@Autowired
	private RegistratorService registratorService;
	
	@GetMapping("/registrators/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=registrators_"+ currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Registrator> listRegistrators = registratorService.listAll();
		
		RegistratorExcelExporter excelExporter = new RegistratorExcelExporter(listRegistrators);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/registrators/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (RegistratorExcelUpload.hasExcelFormat(file)) {
          try {
            registratorService.save(file);

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
    
    @GetMapping("/registrators/excel")
    public ResponseEntity<List<Registrator>> getAllRegistrators() {
        try {
          List<Registrator> registrators = registratorService.listAll();

          if (registrators.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(registrators, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
