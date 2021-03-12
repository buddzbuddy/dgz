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
import com.webdatabase.dgz.model.Msec_detail;
import com.webdatabase.dgz.repository.Msec_detailRepository;

@RestController
public class Msec_detailController {
	
	@Autowired
	private Msec_detailRepository msec_detailRepository;
	
	@GetMapping("msec_details")
	public Page<Msec_detail> getAllMsec_detail(Pageable pageable){
		return msec_detailRepository.findAll(pageable);
	}
	
	@GetMapping("msec_details{id}")
	public Optional<Msec_detail> getOneMsec_detail(@PathVariable Long id){
		return msec_detailRepository.findById(id);
	}
	
	@PostMapping("msec_details")
	public Msec_detail createMsec_detail(@Valid @RequestBody Msec_detail msec_detail) {
		return msec_detailRepository.save(msec_detail);
	}
	
	@PutMapping("msec_details{id}")
	public Msec_detail updateMsec_detail(@PathVariable Long id, @Valid @RequestBody Msec_detail msec_detailRequest) {
		return msec_detailRepository.findById(id)
				.map(msec_detail ->{
					msec_detail.setCreatedAt(msec_detailRequest.getCreatedAt());
					msec_detail.setDisabilityGroup(msec_detailRequest.getDisabilityGroup());
					msec_detail.setExaminationDate(msec_detailRequest.getExaminationDate());
					msec_detail.setExaminationtype(msec_detailRequest.getExaminationtype());
					msec_detail.setFrom(msec_detailRequest.getFrom());
					msec_detail.setOrgnizationName(msec_detailRequest.getOrgnizationName());
					msec_detail.setReExamination(msec_detailRequest.getReExamination());
					msec_detail.setSupplier_member(msec_detailRequest.getSupplier_member());
					msec_detail.setTo(msec_detailRequest.getTo());
					msec_detailRequest.setUpdatedAt(msec_detailRequest.getUpdatedAt());
					return msec_detailRepository.save(msec_detail);
				}).orElseThrow(()-> new ResourceNotFoundException("Msec detail not found with id "+ id));
	}
	
	@DeleteMapping("msec_details{id}")
	public ResponseEntity<?> deleteMsec_detail(@PathVariable Long id){
		return msec_detailRepository.findById(id)
				.map(msec_detail -> {
					msec_detailRepository.delete(msec_detail);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Msec detail not found with id "+id));
	}
	
}
