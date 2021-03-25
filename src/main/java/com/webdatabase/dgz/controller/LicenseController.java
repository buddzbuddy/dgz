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

import com.webdatabase.dgz.excelExport.LicenseExcelExporter;
import com.webdatabase.dgz.excelUpload.LicenseExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.License;
import com.webdatabase.dgz.repository.LicenseRepository;
import com.webdatabase.dgz.service.LicenseService;

@RestController
public class LicenseController {

	@Autowired
	private LicenseRepository licenseRepository;
	
	@GetMapping("/licenses")
    public Page<License> getAll(Pageable pageable) {
        return licenseRepository.findAll(pageable);
    }

    @GetMapping("/licenses/{id}")
    public Optional<License> getOne(@PathVariable Long id) {
        return licenseRepository.findById(id);
    }


    @PostMapping("/licenses")
    public License create(@Valid @RequestBody License license) {
        return licenseRepository.save(license);
    }

    @PutMapping("/licenses/{id}")
    public License update(@PathVariable Long id,
                                   @Valid @RequestBody License licenseRequest) {
        return licenseRepository.findById(id)
                .map(license -> {
                	license.setAdditionalInfo(licenseRequest.getAdditionalInfo());
                	license.setExpiryDate(licenseRequest.getExpiryDate());
                	license.setIssueDate(licenseRequest.getIssueDate());
                	license.setIssuer(licenseRequest.getIssuer());
                	license.setLicense_type(licenseRequest.getLicense_type());
                	license.setNo(licenseRequest.getNo());
                	license.setStatus(licenseRequest.getStatus());
                	license.setSupplier(licenseRequest.getSupplier());
                	license.setCreatedAt(licenseRequest.getCreatedAt());
                	license.setUpdatedAt(licenseRequest.getUpdatedAt());
                    return licenseRepository.save(license);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return licenseRepository.findById(id)
                .map(counterpart -> {
                	licenseRepository.delete(counterpart);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
    
    //export to Excel
    
    @Autowired
    private LicenseService licenseService;
    
    @GetMapping("/licenses/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application/octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String headervalue = "attachment; filename=licenses_"+currentDate +".xlsx";
    	response.setHeader(headerKey, headervalue);
    	
    	List<License> listLicenses = licenseService.listAll();
    	
    	LicenseExcelExporter excelExport = new LicenseExcelExporter(listLicenses);
    	
    	excelExport.export(response);
    }
    
 
    //upload from Excel
    
    @PostMapping("/licenses/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (LicenseExcelUpload.hasExcelFormat(file)) {
          try {
            licenseService.save(file);

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
    
    @GetMapping("/licenses/excel")
    public ResponseEntity<List<License>> getAllLicenses() {
        try {
          List<License> licenses = licenseService.listAll();

          if (licenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(licenses, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
 	}
}
