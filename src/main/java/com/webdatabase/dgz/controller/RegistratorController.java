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
import com.webdatabase.dgz.model.Registrator;
import com.webdatabase.dgz.repository.RegistratorRepository;

@RestController
public class RegistratorController {
	@Autowired
	private RegistratorRepository registratorRepository;
	
	@GetMapping("/registrators")
	public Page<Registrator> getAllRegistrator(Pageable pageable){
		return registratorRepository.findAll(pageable);
	}
	
	@GetMapping("/registrators{id}")
	public Optional<Registrator> getOneRegistrator (@PathVariable Long id){
		return registratorRepository.findById(id);
	}
	
	@PostMapping("/registrators")
	public Registrator createRegistrator(@Valid @RequestBody Registrator registrator) {
		return registratorRepository.save(registrator);
	}
	@PutMapping("/registrators{id}")
	public Registrator updateRegistrator(@PathVariable Long id, @Valid @RequestBody Registrator registratorRequest) {
		return registratorRepository.findById(id)
				.map(registrator ->{
					registrator.setContactData(registratorRequest.getContactData());
					registrator.setCounterpart(registratorRequest.getCounterpart());
					registrator.setCreatedAt(registratorRequest.getCreatedAt());
					registrator.setName(registratorRequest.getName());
					registrator.setUpdatedAt(registratorRequest.getUpdatedAt());
					return registratorRepository.save(registrator);
				}).orElseThrow(()-> new ResourceNotFoundException("Registrator not found with id "+id));
	}
	
	@DeleteMapping("/registrators{id}")
	public ResponseEntity<?> deleteRegistrator(@PathVariable Long id){
		return registratorRepository.findById(id)
				.map(registrator ->{
					registratorRepository.delete(registrator);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Registrator not found with id "+id));
	}
}
