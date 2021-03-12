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
import com.webdatabase.dgz.model.Member_type;
import com.webdatabase.dgz.repository.Member_typeRepository;

@RestController
public class Member_typeController {

	@Autowired
	private Member_typeRepository member_typeRepository;
	
	@GetMapping("/member_types")
    public Page<Member_type> getAll(Pageable pageable) {
		return member_typeRepository.findAll(pageable);
    }

    @GetMapping("/member_types/{id}")
    public Optional<Member_type> getOne(@PathVariable Long id) {
        return member_typeRepository.findById(id);
    }


    @PostMapping("/member_types")
    public Member_type create(@Valid @RequestBody Member_type member_type) {
        return member_typeRepository.save(member_type);
    }

    @PutMapping("/member_types/{id}")
    public Member_type update(@PathVariable Long id,
                                   @Valid @RequestBody Member_type member_typeRequest) {
        return member_typeRepository.findById(id)
                .map(member_type -> {
                	member_type.setName(member_typeRequest.getName());
                	member_type.setCreatedAt(member_typeRequest.getCreatedAt());
                	member_type.setUpdatedAt(member_typeRequest.getUpdatedAt());
                    return member_typeRepository.save(member_type);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/member_types/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return member_typeRepository.findById(id)
                .map(member_type -> {
                	member_typeRepository.delete(member_type);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
