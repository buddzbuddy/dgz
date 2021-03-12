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
import com.webdatabase.dgz.model.Taxe_code;
import com.webdatabase.dgz.repository.Taxe_codeRepository;

@RestController
public class Taxe_codeController {
	@Autowired
	private Taxe_codeRepository taxe_codeRepository;
	
	@GetMapping("/taxe_codes")
	public Page<Taxe_code> getAllTaxe_code(Pageable pageable){
		return taxe_codeRepository.findAll(pageable);
	}
	
	@GetMapping("/taxe_codes{id}")
	public Optional<Taxe_code> getOneTaxe_code(@PathVariable Long id){
		return taxe_codeRepository.findById(id);
	}
	
	@PostMapping("/taxe_codes")
	public Taxe_code createTaxe_code(@Valid @RequestBody Taxe_code taxe_code) {
		return taxe_codeRepository.save(taxe_code);
	}
	
	@PutMapping("/taxe_codes{id}")
	public Taxe_code updateTaxe_code(@PathVariable Long id, @Valid @RequestBody Taxe_code taxe_codeRequest) {
		return taxe_codeRepository.findById(id)
				.map(taxe_code ->{
					taxe_code.setCode(taxe_codeRequest.getCode());
					taxe_code.setCreatedAt(taxe_codeRequest.getCreatedAt());
					taxe_code.setDetailName(taxe_codeRequest.getDetailName());
					taxe_code.setName(taxe_codeRequest.getName());
					taxe_code.setUpdatedAt(taxe_codeRequest.getUpdatedAt());
					return taxe_codeRepository.save(taxe_code);
				}).orElseThrow(()-> new ResourceNotFoundException("Taxe code not found with id "+id));
	}
	@DeleteMapping("/taxe_codes{id}")
	public ResponseEntity<?> deleteTaxe_code(@PathVariable Long id){
		return taxe_codeRepository.findById(id)
				.map(taxe_code -> {
					taxe_codeRepository.delete(taxe_code);
					return ResponseEntity.ok().build();
		}).orElseThrow(()-> new ResourceNotFoundException("Taxe code not found with id "+id));
				
	}
}
