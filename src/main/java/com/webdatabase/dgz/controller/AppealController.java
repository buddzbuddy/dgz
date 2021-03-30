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
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.AppealRepository;
import com.webdatabase.dgz.repository.Procuring_entityRepository;
import com.webdatabase.dgz.repository.SupplierRepository;
import com.webdatabase.dgz.service.AppealService;

@RestController
public class AppealController {
	
	@Autowired
	private AppealRepository appealRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private Procuring_entityRepository procuring_entityRepository;
	
	@GetMapping("/appeals")
    public Page<Appeal> getAll(Pageable pageable) {
        return appealRepository.findAll(pageable);
    }

	@GetMapping("/supliers/{supplierId}/appeals")
	public List<Supplier> getAppealsBySuppliersId(@PathVariable Long suplierId) {
		return appealRepository.findBySuppliersId(suplierId);
	}
	
	@PostMapping("/suppliers/{supplierId}/appeals")
    public Appeal addAppealSupplier(@PathVariable Long supplierId, @Valid @RequestBody Appeal appeal) {
        return supplierRepository.findById(supplierId)
        		.map(supplier -> {
        			appeal.setSupplier(supplier);
        			return appealRepository.save(appeal);
        		}).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id " + supplierId));
    }
	
	@PutMapping("/suppliers/{supplierId}/appeals/{appealId}")
	public Appeal updateAppealSupplier(@PathVariable Long supplierId, @PathVariable Long appealId, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody Appeal appealRequest) {
		if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id " + supplierId);
		}
		return appealRepository.findById(appealId)
				.map(appeal -> {
					appeal.setDescription(appealRequest.getDescription());
					appeal.setSupplier(appealRequest.getSupplier());
					appeal.setProcuring_entity(appealRequest.getProcuring_entity());
					appeal.setCreatedAt(appealRequest.getCreatedAt());
					appeal.setUpdatedAt(appealRequest.getUpdatedAt());
					return appealRepository.save(appeal);
				}).orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id " + appealId));
	}
	
	@DeleteMapping("/suppliers/{supplierId}/appeals/{appealId}")
    public ResponseEntity<?> deleteAppealSupplier(@PathVariable Long supplierId, @PathVariable Long appealId) {
		if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id " + supplierId);
		}
		return appealRepository.findById(appealId)
                .map(appeal -> {
                	appealRepository.delete(appeal);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id " + appealId));
    }
	
	@GetMapping("/procuring_entities/{procuring_entityId}/appeals")
	public List<Procuring_entity> getAppealsByProcuring_entitiesId(@PathVariable Long procuring_entityId){
		return appealRepository.fingByProcuring_entitiesId(procuring_entityId);
	}

    @PostMapping("/procuring_entities/{procuring_entityId}/appeals")
    public Appeal addAppealProcuring_entity(@PathVariable Long procuring_entityId, @Valid @RequestBody Appeal appeal) {
    	return procuring_entityRepository.findById(procuring_entityId)
    			.map(procuring_entity -> {
    				appeal.setProcuring_entity(procuring_entity);
    				return appealRepository.save(appeal);
    			}).orElseThrow(() -> new ResourceNotFoundException("Procuring entity not found with id" + procuring_entityId));
    }
    
    @PutMapping("/procuring_entities/{procuring_entityId}/appeals/{appealId}")
	public Appeal updateAppealProcuringEntity(@PathVariable Long procuring_entityId, @PathVariable Long appealId, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody Appeal appealRequest) {
		if (!procuring_entityRepository.existsById(procuring_entityId)) {
			throw new ResourceNotFoundException("Procuring entity not found with id " + procuring_entityId);
		}
		return appealRepository.findById(appealId)
				.map(appeal -> {
					appeal.setDescription(appealRequest.getDescription());
					appeal.setSupplier(appealRequest.getSupplier());
					appeal.setProcuring_entity(appealRequest.getProcuring_entity());
					appeal.setCreatedAt(appealRequest.getCreatedAt());
					appeal.setUpdatedAt(appealRequest.getUpdatedAt());
					return appealRepository.save(appeal);
				}).orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id " + appealId));
	}
	
	@DeleteMapping("/procuring_entities/{procuring_entityId}/appeals/{appealId}")
    public ResponseEntity<?> deleteAppealProcuring_entity(@PathVariable Long procuring_entityId, @PathVariable Long appealId) {
		if (!procuring_entityRepository.existsById(procuring_entityId)) {
			throw new ResourceNotFoundException("Procuring entity not found with id " + procuring_entityId);
		}
		return appealRepository.findById(appealId)
                .map(appeal -> {
                	appealRepository.delete(appeal);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Appeal not found with id " + appealId));
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