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
import com.webdatabase.dgz.model.Counterpart;
import com.webdatabase.dgz.repository.CounterpartRepository;


@RestController
public class CounterpartController {
	
	@Autowired
	private CounterpartRepository counterpartRepository;
	
	@GetMapping("/counterparts")
    public Page<Counterpart> getAll(Pageable pageable) {
        return counterpartRepository.findAll(pageable);
    }

    @GetMapping("/counterparts/{id}")
    public Optional<Counterpart> getOne(@PathVariable Long id) {
        return counterpartRepository.findById(id);
    }


    @PostMapping("/counterparts")
    public Counterpart create(@Valid @RequestBody Counterpart counterpart) {
        return counterpartRepository.save(counterpart);
    }

    @PutMapping("/counterparts/{id}")
    public Counterpart update(@PathVariable Long id,
                                   @Valid @RequestBody Counterpart counterpartRequest) {
        return counterpartRepository.findById(id)
                .map(counterpart -> {
                	counterpart.setName(counterpartRequest.getName());
                	counterpart.setAddress(counterpartRequest.getAddress());
                	counterpart.setBankAccountNo(counterpartRequest.getBankAccountNo());
                	counterpart.setComments(counterpartRequest.getComments());
                	counterpart.setContactData(counterpartRequest.getContactData());
                	counterpart.setCounterpart_type(counterpartRequest.getCounterpart_type());
                	counterpart.setCreatedAt(counterpartRequest.getCreatedAt());
                	counterpart.setUpdatedAt(counterpartRequest.getUpdatedAt());
                    return counterpartRepository.save(counterpart);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/counterparts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return counterpartRepository.findById(id)
                .map(counterpart -> {
                	counterpartRepository.delete(counterpart);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
