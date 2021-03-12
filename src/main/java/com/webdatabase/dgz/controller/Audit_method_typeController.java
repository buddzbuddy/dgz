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
import com.webdatabase.dgz.model.Audit_method_type;
import com.webdatabase.dgz.repository.Audit_method_typeRepository;

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
                	audit_method_type.setCreatedAt(audit_method_typeRequest.getCreatedAt());
                	audit_method_type.setUpdatedAt(audit_method_typeRequest.getUpdatedAt());
                    return audit_method_typeRepository.save(audit_method_type);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/audit_method_types/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return audit_method_typeRepository.findById(id)
                .map(counterpart -> {
                	audit_method_typeRepository.delete(counterpart);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
