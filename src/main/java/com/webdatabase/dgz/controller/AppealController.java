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

import com.webdatabase.dgz.excelExport.AppealExcelExporter;
import com.webdatabase.dgz.excelUpload.AppealExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Appeal;
import com.webdatabase.dgz.repository.AppealRepository;
import com.webdatabase.dgz.service.AppealService;

@RestController
public class AppealController {
	
	@Autowired
	private AppealRepository appealRepository;
	
	@GetMapping("/appeals")
    public Page<Appeal> getAll(Pageable pageable) {
        return appealRepository.findAll(pageable);
    }

    @GetMapping("/appeals/{id}")
    public Optional<Appeal> getOne(@PathVariable Long id) {
        return appealRepository.findById(id);
    }


    @PostMapping("/appeals")
    public Appeal create(@Valid @RequestBody Appeal appeal) {
        return appealRepository.save(appeal);
    }

    @PutMapping("/appeals/{id}")
    public Appeal update(@PathVariable Long id,
                                   @Valid @RequestBody Appeal appealRequest) {
        return appealRepository.findById(id)
                .map(appeal -> {
                	appeal.setDescription(appealRequest.getDescription());
                	appeal.setProcuring_entity(appealRequest.getProcuring_entity());
                	appeal.setSupplier(appealRequest.getSupplier());
                	appeal.setCreatedAt(appealRequest.getCreatedAt());
                	appeal.setUpdatedAt(appealRequest.getUpdatedAt());
                    return appealRepository.save(appeal);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/appeals/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return appealRepository.findById(id)
                .map(appeal -> {
                	appealRepository.delete(appeal);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
    
    //Export to excel
    
    @Autowired
	private AppealService appealService;
	
	@GetMapping("/appeals/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=appeals_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List <Appeal> listAppeal = appealService.listAll();
		
		AppealExcelExporter excelExporter = new AppealExcelExporter(listAppeal);
		excelExporter.export(response);
	}
	
	//Upload from Excel
	
	@PostMapping("/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile (@RequestParam("file") MultipartFile file){
		String message = "";
		
		if (AppealExcelUpload.hasExcelFormat(file)) {
			try {
				appealService.save(file);
				
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
	
	@GetMapping("/appeals/excel")
	public ResponseEntity<List<Appeal>> getAllAppeals(){
		try {
			List<Appeal> appeals = appealService.listAll();
			
			if (appeals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(appeals, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}		
	}
}