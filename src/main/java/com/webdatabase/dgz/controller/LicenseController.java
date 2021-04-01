package com.webdatabase.dgz.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.webdatabase.dgz.repository.License_typeRepository;
import com.webdatabase.dgz.repository.SupplierRepository;
import com.webdatabase.dgz.service.LicenseService;

@RestController
public class LicenseController {

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private License_typeRepository license_typeRepository;
	
	@GetMapping("/licenses")
    public Page<License> getAll(Pageable pageable) {
        return licenseRepository.findAll(pageable);
    }

    @GetMapping("/suppliers/{supplierId}/licenses")
    public List<License> getLicensesBySupplierId(@PathVariable Long supplierId) {
        return licenseRepository.findBySupplierId(supplierId);
    }


    @PostMapping("/suppliers/{supplierId}/licenses")
    public License addLicenseSupplier(@PathVariable Long supplierId, @Valid @RequestBody License license) {
        return supplierRepository.findById(supplierId)
        		.map(supplier -> {
        			license.setSupplier(supplier);
        			return licenseRepository.save(license);
        		}).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id" + supplierId));
    }

    @PutMapping("/suppliers/{supplierId}/licenses/{licenseId}")
    public License updateLicenseSupplier(@PathVariable Long supplierId, @PathVariable Long licenseId,
                                   @Valid @RequestBody License licenseRequest) {
    	if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id" + supplierId);
		}
    	
        return licenseRepository.findById(licenseId)
                .map(license -> {
                	license.setAdditionalInfo(licenseRequest.getAdditionalInfo());
                	license.setExpiryDate(licenseRequest.getExpiryDate());
                	license.setIssueDate(licenseRequest.getIssueDate());
                	license.setIssuer(licenseRequest.getIssuer());
                	license.setNo(licenseRequest.getNo());
                	license.setStatus(licenseRequest.getStatus());
                    return licenseRepository.save(license);
                }).orElseThrow(() -> new ResourceNotFoundException("License not found with id " + licenseId));
    }


    @DeleteMapping("/suppliers/{supplierId}/licenses/{licenseId}")
    public ResponseEntity<?> deleteLicenseSupplier(@PathVariable Long supplierId, @PathVariable Long licenseId) {
        if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id" + supplierId);

		}
    	
    	return licenseRepository.findById(licenseId)
                .map(license ->{
                	licenseRepository.delete(license);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("License not found with id " + licenseId));
    }
    
    
    
    
    
    
    
    
    @GetMapping("/license_types/{license_typeId}/licenses")
    public List<License> getLicensesByLicense_typeId(@PathVariable Long license_typeId) {
        return licenseRepository.findByLicense_typeId(license_typeId);
    }


    @PostMapping("/license_types/{license_typeId}/licenses")
    public License addLicenseLicense_type(@PathVariable Long license_typeId, @Valid @RequestBody License license) {
        return license_typeRepository.findById(license_typeId)
        		.map(license_type -> {
        			license.setLicense_type(license_type);
        			return licenseRepository.save(license);
        		}).orElseThrow(() -> new ResourceNotFoundException("License type not found with id" + license_typeId));
    }

    @PutMapping("/license_types/{license_typeId}/licenses/{licenseId}")
    public License updateLicenseLicense_type(@PathVariable Long license_typeId, @PathVariable Long licenseId,
                                   @Valid @RequestBody License licenseRequest) {
    	if (!license_typeRepository.existsById(license_typeId)) {
			throw new ResourceNotFoundException("License type not found with id" + license_typeId);
		}
    	
        return licenseRepository.findById(licenseId)
                .map(license -> {
                	license.setAdditionalInfo(licenseRequest.getAdditionalInfo());
                	license.setExpiryDate(licenseRequest.getExpiryDate());
                	license.setIssueDate(licenseRequest.getIssueDate());
                	license.setIssuer(licenseRequest.getIssuer());
                	license.setNo(licenseRequest.getNo());
                	license.setStatus(licenseRequest.getStatus());
                    return licenseRepository.save(license);
                }).orElseThrow(() -> new ResourceNotFoundException("License not found with id " + licenseId));
    }


    @DeleteMapping("/supplier/{supplierId}/licenses/{licenseId}")
    public ResponseEntity<?> deleteLicenseLicense_type(@PathVariable Long license_typeId, @PathVariable Long licenseId) {
        if (!license_typeRepository.existsById(license_typeId)) {
			throw new ResourceNotFoundException("License type not found with id" + license_typeId);

		}
    	
    	return licenseRepository.findById(licenseId)
                .map(license ->{
                	licenseRepository.delete(license);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("License not found with id " + licenseId));
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
