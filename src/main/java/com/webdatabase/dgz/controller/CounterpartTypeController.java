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
import com.webdatabase.dgz.model.Counterpart_type;
import com.webdatabase.dgz.repository.CounterpartTypeRepository;

@RestController
public class CounterpartTypeController {
	
	@Autowired
	private CounterpartTypeRepository counterpartTypeRepository;
	
	@GetMapping("/counterpart_types")
	public Page<Counterpart_type> getAllCounterpartType(Pageable pageable){
		return counterpartTypeRepository.findAll(pageable);
	}
	
	@GetMapping("/counterpart_types{id}")
	public Optional<Counterpart_type> getOneCounterpartType(@PathVariable Long id){
		return counterpartTypeRepository.findById(id);
	}
	
	@PostMapping("/counterpart_types")
	public Counterpart_type createCounterpartType(@Valid @RequestBody Counterpart_type counterpart_type) {
		return counterpartTypeRepository.save(counterpart_type);
	}
	
	@PutMapping("/counterpart_types{id}")
	public Counterpart_type updateCounterpartType(@PathVariable Long id, @Valid @RequestBody Counterpart_type counterpart_typeRequest) {
		return counterpartTypeRepository.findById(id)
				.map(counterpart_type -> {
					counterpart_type.setName(counterpart_typeRequest.getName());
					return counterpartTypeRepository.save(counterpart_type);
				}).orElseThrow(() -> new ResourceNotFoundException("Counterpart type not found with id "+ id));
	}
	
	@DeleteMapping("/counterpart_types{id}")
	public ResponseEntity<?> deleteCounterpartType(@PathVariable Long id){
		return counterpartTypeRepository.findById(id)
			.map(counterpart_type -> {
				counterpartTypeRepository.delete(counterpart_type);
				return ResponseEntity.ok().build();
			}).orElseThrow(() -> new ResourceNotFoundException("Counterpart type not found with id "+ id));
	}
}
