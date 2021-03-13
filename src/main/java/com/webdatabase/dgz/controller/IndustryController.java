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
import com.webdatabase.dgz.model.Industry;
import com.webdatabase.dgz.repository.IndustryRepository;

@RestController
public class IndustryController {

	@Autowired
	private IndustryRepository industryRepository;
	
	@GetMapping("/industries")
    public Page<Industry> getAll(Pageable pageable) {
		return industryRepository.findAll(pageable);
    }

    @GetMapping("/industries/{id}")
    public Optional<Industry> getOne(@PathVariable Long id) {
        return industryRepository.findById(id);
    }


    @PostMapping("/industries")
    public Industry create(@Valid @RequestBody Industry industry) {
        return industryRepository.save(industry);
    }

    @PutMapping("/industries/{id}")
    public Industry update(@PathVariable Long id,
                                   @Valid @RequestBody Industry industryRequest) {
        return industryRepository.findById(id)
                .map(industry -> {
                	industry.setName(industryRequest.getName());
                	industry.setCreatedAt(industryRequest.getCreatedAt());
                	industry.setUpdatedAt(industryRequest.getUpdatedAt());
                    return industryRepository.save(industry);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/industries/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return industryRepository.findById(id)
                .map(industry -> {
                	industryRepository.delete(industry);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
