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

import com.webdatabase.dgz.excelExport.SupplierExcelExporter;
import com.webdatabase.dgz.excelUpload.SupplierExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.SupplierRepository;
import com.webdatabase.dgz.service.SupplierService;

@RestController
public class SupplierController {
	@Autowired
	private SupplierRepository supplierRepository;
	
	@GetMapping("/suppliers")
	public Page<Supplier> getAllSupplier(Pageable pageable){
		return supplierRepository.findAll(pageable);
	}
	
	@GetMapping("/suppliers{id}")
	public Optional<Supplier> getOneSupplier(@PathVariable Long id){
		return supplierRepository.findById(id);
	}
	
	@PostMapping("/suppliers")
	public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
		return supplierRepository.save(supplier);
	}
	
	@PutMapping("/suppliers{id}")
	public Supplier updateSupplier(@PathVariable Long id, @Valid @RequestBody Supplier supplierRequest) {
		return supplierRepository.findById(id)
				.map(supplier -> {
					supplier.setBankAccount(supplierRequest.getBankAccount());
					supplier.setBankName(supplierRequest.getBankName());
					supplier.setBic(supplierRequest.getBic());
					supplier.setCreatedAt(supplierRequest.getCreatedAt());
					supplier.setFactAddress(supplierRequest.getFactAddress());
					supplier.setInn(supplierRequest.getInn());
					supplier.setIsResident(supplierRequest.getIsResident());
					supplier.setLegalAddress(supplierRequest.getLegalAddress());
					supplier.setName(supplierRequest.getName());
					supplier.setRayonCode(supplierRequest.getRayonCode());
					supplier.setTelephone(supplierRequest.getTelephone());
					supplier.setUpdatedAt(supplierRequest.getUpdatedAt());
					supplier.setZip(supplierRequest.getZip());
					supplier.setIsBlack(supplierRequest.getIsBlack());
					supplier.set_Ownership_type(supplierRequest.get_Ownership_type());
					supplier.setIndustry(supplierRequest.getIndustry());
					return supplierRepository.save(supplier);
				}).orElseThrow(()-> new ResourceNotFoundException("Supplier not found with id "+id));
	}
	
	@DeleteMapping("/suppliers{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
		return supplierRepository.findById(id)
				.map(supplier -> {
					supplierRepository.delete(supplier);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Supplier not found with id "+id));
	}
	
	//export to Excel
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("suppliers/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=suppliers_" + currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Supplier> listSuppliers = supplierService.listAll();
		
		SupplierExcelExporter excelExporter = new SupplierExcelExporter(listSuppliers);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/suppliers/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (SupplierExcelUpload.hasExcelFormat(file)) {
          try {
            supplierService.save(file);

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
    
    @GetMapping("/suppliers/excel")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        try {
          List<Supplier> suppliers = supplierService.listAll();

          if (suppliers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(suppliers, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
