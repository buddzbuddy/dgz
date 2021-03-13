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
import com.webdatabase.dgz.model.License;
import com.webdatabase.dgz.repository.LicenseRepository;

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
                	license.setStatus(license.getStatus());
                	license.setSupplier(license.getSupplier());
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
}
