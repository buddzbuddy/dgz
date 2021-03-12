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
import com.webdatabase.dgz.model.Ownership_type;
import com.webdatabase.dgz.repository.Ownership_typeRepository;

@RestController
public class Ownership_typeController {
	@Autowired
	private Ownership_typeRepository ownership_typeRepository;
	
	@GetMapping("ownership_types")
	public Page<Ownership_type> getAllOwnership_type(Pageable pageable){
		return ownership_typeRepository.findAll(pageable);
	}
	
	@GetMapping("ownership_types{id}")
	public Optional<Ownership_type> getOneOwnership_type(@PathVariable Long id){
		return ownership_typeRepository.findById(id);
	}
	
	@PostMapping("ownership_types")
	public Ownership_type createOwnership_type(@Valid @RequestBody Ownership_type ownership_type) {
		return ownership_typeRepository.save(ownership_type);
	}
	
	@PutMapping("ownership_types{id}")
	public Ownership_type updateOwnership_type(@PathVariable Long id, @Valid @RequestBody Ownership_type ownership_typeRequest) {
		return ownership_typeRepository.findById(id)
				.map(ownership_type -> {
					ownership_type.setCreatedAt(ownership_typeRequest.getCreatedAt());
					ownership_type.setName(ownership_typeRequest.getName());
					ownership_type.setUpdatedAt(ownership_typeRequest.getUpdatedAt());
					return ownership_typeRepository.save(ownership_type);
				}).orElseThrow(()-> new ResourceNotFoundException("Ownership type not found with id "+id));
	}
	
	@DeleteMapping("ownership_types{id}")
	public ResponseEntity<?> deleteOwnership_type(@PathVariable Long id){
		return ownership_typeRepository.findById(id)
				.map(ownership_type -> {
					ownership_typeRepository.delete(ownership_type);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Ownership type not found with id "+id));
	}
}
