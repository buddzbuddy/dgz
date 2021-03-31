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

import com.webdatabase.dgz.excelExport.License_typeExcelExporter;
import com.webdatabase.dgz.excelUpload.License_typeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.License_type;
import com.webdatabase.dgz.repository.License_typeRepository;
import com.webdatabase.dgz.service.License_typeService;

@RestController
public class License_typeController {

	@Autowired
	private License_typeRepository license_typeRepository;
	
	@GetMapping("/license_types")
    public Page<License_type> getAll(Pageable pageable) {
		return license_typeRepository.findAll(pageable);
    }

    @GetMapping("/license_types/{license_typeId}")
    public Optional<License_type> getOne(@PathVariable Long id) {
        return license_typeRepository.findById(id);
    }


    @PostMapping("/license_types")
    public License_type create(@Valid @RequestBody License_type license_type) {
        return license_typeRepository.save(license_type);
    }

    @PutMapping("/license_types/{license_typeId}")
    public License_type update(@PathVariable Long license_typeId,
                                   @Valid @RequestBody License_type license_typeRequest) {
        return license_typeRepository.findById(license_typeId)
                .map(license_type -> {
                	license_type.setName(license_typeRequest.getName());
                    return license_typeRepository.save(license_type);
                }).orElseThrow(() -> new ResourceNotFoundException("License type not found with id " + license_typeId));
    }


    @DeleteMapping("/license_types/{license_typeId}")
    public ResponseEntity<?> delete(@PathVariable Long license_typeId) {
        return license_typeRepository.findById(license_typeId)
                .map(license_type -> {
                	license_typeRepository.delete(license_type);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("License type not found with id " + license_typeId));
    }
    
    //export to Excel
    
    @Autowired
    private License_typeService licenseTypeService;
    
    @GetMapping("/license_types/export/excel")
    public void export(HttpServletResponse response) throws IOException{
    	response.setContentType("application/octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=license_type_"+ currentDate +".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<License_type> listLicense_types = licenseTypeService.listAll();
    	
    	License_typeExcelExporter excelExport = new License_typeExcelExporter(listLicense_types);
    	
    	excelExport.export(response);
    }
    
    //upload from Excel
    
    @PostMapping("/license_types/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (License_typeExcelUpload.hasExcelFormat(file)) {
          try {
            licenseTypeService.save(file);

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
    
    @GetMapping("/license_types/excel")
    public ResponseEntity<List<License_type>> getAllLicense_types() {
        try {
          List<License_type> license_types = licenseTypeService.listAll();

          if (license_types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(license_types, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
