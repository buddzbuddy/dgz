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

import com.webdatabase.dgz.excelExport.Audit_method_typeExcelExporter;
import com.webdatabase.dgz.excelUpload.Audit_method_typeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Audit_method_type;
import com.webdatabase.dgz.repository.Audit_method_typeRepository;
import com.webdatabase.dgz.service.Audit_method_typeService;

@RestController
public class Audit_method_typeController {
	
	@Autowired
	private Audit_method_typeRepository audit_method_typeRepository;
	
	@GetMapping("/audit_method_types")
    public Page<Audit_method_type> getAll(Pageable pageable) {
		return audit_method_typeRepository.findAll(pageable);
    }

    @GetMapping("/audit_method_types/{id}")
    public Optional<Audit_method_type> getOne(@PathVariable Long id) {
        return audit_method_typeRepository.findById(id);
    }


    @PostMapping("/audit_method_types")
    public Audit_method_type create(@Valid @RequestBody Audit_method_type audit_method_type) {
        return audit_method_typeRepository.save(audit_method_type);
    }

    @PutMapping("/audit_method_types/{id}")
    public Audit_method_type update(@PathVariable Long id,
                                   @Valid @RequestBody Audit_method_type audit_method_typeRequest) {
        return audit_method_typeRepository.findById(id)
                .map(audit_method_type -> {
                	audit_method_type.setName(audit_method_typeRequest.getName());
                	audit_method_type.setCode(audit_method_typeRequest.getCode());
                    return audit_method_typeRepository.save(audit_method_type);
                }).orElseThrow(() -> new ResourceNotFoundException("Audit method type not found with id " + id));
    }


    @DeleteMapping("/audit_method_types/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return audit_method_typeRepository.findById(id)
                .map(audit_method_type -> {
                	audit_method_typeRepository.delete(audit_method_type);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Audit method type not found with id " + id));
    }
    
    //Export to Excel
    
    @Autowired
	private Audit_method_typeService auditMethodService;
	
	@GetMapping("/audit_method_types/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/ostet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=audit_method_types_"+ currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Audit_method_type> listAudit_method_types = auditMethodService.listAll();
		
		Audit_method_typeExcelExporter audit_method_typeExcelExporter = new Audit_method_typeExcelExporter(listAudit_method_types);
		
		audit_method_typeExcelExporter.export(response);
	}
	
	//Upload from Excel
	
	@PostMapping("/audit_method_types/upload/excel")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
		String message = "";
		if (Audit_method_typeExcelUpload.hasExcelFormat(file)) {
			try {
				auditMethodService.save(file);
				
				message = "Файл успешно загружен: "+ file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		
		message = "Загрузите файл в формате Excel!";
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	}
	
	@GetMapping("/audit_method_types/excel")
	public ResponseEntity<List<Audit_method_type>> getAllAudit_method_types(){
		try {
			List<Audit_method_type> audit_method_types = auditMethodService.listAll();
			if (audit_method_types.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(audit_method_types, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
