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
import com.webdatabase.dgz.model.License_type;
import com.webdatabase.dgz.repository.License_typeRepository;

@RestController
public class License_typeController {

	@Autowired
	private License_typeRepository license_typeRepository;
	
	@GetMapping("/license_types")
    public Page<License_type> getAll(Pageable pageable) {
		return license_typeRepository.findAll(pageable);
    }

    @GetMapping("/license_types/{id}")
    public Optional<License_type> getOne(@PathVariable Long id) {
        return license_typeRepository.findById(id);
    }


    @PostMapping("/license_types")
    public License_type create(@Valid @RequestBody License_type license_type) {
        return license_typeRepository.save(license_type);
    }

    @PutMapping("/license_types/{id}")
    public License_type update(@PathVariable Long id,
                                   @Valid @RequestBody License_type license_typeRequest) {
        return license_typeRepository.findById(id)
                .map(license_type -> {
                	license_type.setName(license_typeRequest.getName());
                	license_type.setCreatedAt(license_typeRequest.getCreatedAt());
                	license_type.setUpdatedAt(license_typeRequest.getUpdatedAt());
                    return license_typeRepository.save(license_type);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/license_types/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return license_typeRepository.findById(id)
                .map(industry -> {
                	license_typeRepository.delete(industry);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
