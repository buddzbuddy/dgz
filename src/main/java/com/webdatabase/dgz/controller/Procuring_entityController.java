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
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.repository.Procuring_entityRepository;

@RestController
public class Procuring_entityController {
	@Autowired
	private Procuring_entityRepository procuring_entityRepository;
	
	@GetMapping("/procuring_entities")
	public Page<Procuring_entity> getAllProcuring_entity(Pageable pageable){
		return procuring_entityRepository.findAll(pageable);
	}
	
	@GetMapping("/procuring_entities{id}")
	public Optional<Procuring_entity> getOneProcuring_entity(@PathVariable Long id){
		return procuring_entityRepository.findById(id);
	}
	
	@PostMapping("/procuring_entities")
	public Procuring_entity createProcuring_entity(@Valid @RequestBody Procuring_entity procuring_entity) {
		return procuring_entityRepository.save(procuring_entity);
	}
	
	@PutMapping("/procuring_entities{id}")
	public Procuring_entity updateProcuring_entity(@PathVariable Long id, @Valid @RequestBody Procuring_entity procuring_entityRequest) {
		return procuring_entityRepository.findById(id)
				.map(procuring_entity -> {
					procuring_entity.setAddress(procuring_entityRequest.getAddress());
					procuring_entity.setContactData(procuring_entityRequest.getContactData());
					procuring_entity.setCreatedAt(procuring_entityRequest.getCreatedAt());
					procuring_entity.setInn(procuring_entityRequest.getInn());
					procuring_entity.setName(procuring_entityRequest.getName());
					procuring_entity.setUpdatedAt(procuring_entityRequest.getUpdatedAt());
					return procuring_entityRepository.save(procuring_entity);
				}).orElseThrow(()-> new ResourceNotFoundException("Procuring entity not found with id "+id));
	}
	@DeleteMapping("/procuring_entities{id}")
	public ResponseEntity<?> deleteProcuring_entity(@PathVariable Long id){
		return procuring_entityRepository.findById(id)
				.map(procuring_e -> {
					procuring_entityRepository.delete(procuring_e);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Procuring entity not found with id "+id));
	}
}
