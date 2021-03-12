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
import com.webdatabase.dgz.model.Appeal;
import com.webdatabase.dgz.repository.AppealRepository;

@RestController
public class AppealController {
	
	@Autowired
	private AppealRepository appealRepository;
	
	@GetMapping("/appeals")
    public Page<Appeal> getAll(Pageable pageable) {
        return appealRepository.findAll(pageable);
    }

    @GetMapping("/appeals/{id}")
    public Optional<Appeal> getOne(@PathVariable Long id) {
        return appealRepository.findById(id);
    }


    @PostMapping("/appeals")
    public Appeal create(@Valid @RequestBody Appeal appeal) {
        return appealRepository.save(appeal);
    }

    @PutMapping("/appeals/{id}")
    public Appeal update(@PathVariable Long id,
                                   @Valid @RequestBody Appeal appealRequest) {
        return appealRepository.findById(id)
                .map(appeal -> {
                	appeal.setDescription(appealRequest.getDescription());
                	appeal.setProcuring_entity(appealRequest.getProcuring_entity());
                	appeal.setSupplier(appealRequest.getSupplier());
                	appeal.setCreatedAt(appealRequest.getCreatedAt());
                	appeal.setUpdatedAt(appealRequest.getUpdatedAt());
                    return appealRepository.save(appeal);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/appeals/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return appealRepository.findById(id)
                .map(appeal -> {
                	appealRepository.delete(appeal);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
