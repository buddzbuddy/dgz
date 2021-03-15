package com.webdatabase.dgz.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.SupplierRepository;

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
}
