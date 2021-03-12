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
import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;
import com.webdatabase.dgz.repository.Tp_data_by_inn_for_business_activity_responseRepository;

@RestController
public class Tp_data_by_inn_for_business_activity_responseController {
	@Autowired
	private Tp_data_by_inn_for_business_activity_responseRepository tp_data_by_inn_for_business_activity_responseRepository;

	@GetMapping("/tp_data_by_inn_for_business_activity_responses")
	public Page<Tp_data_by_inn_for_business_activity_response> getAll(Pageable pageable){
		return tp_data_by_inn_for_business_activity_responseRepository.findAll(pageable);
	}
	
	@GetMapping("/tp_data_by_inn_for_business_activity_responses{id}")
	public Optional<Tp_data_by_inn_for_business_activity_response> getOne(@PathVariable Long id){
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id);
	}
		
	@PostMapping("/tp_data_by_inn_for_business_activity_responses")
	public Tp_data_by_inn_for_business_activity_response create(@Valid @RequestBody Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_response) {
		return tp_data_by_inn_for_business_activity_responseRepository.save(tp_data_by_inn_for_business_activity_response);
	}
	
	@PutMapping("/tp_data_by_inn_for_business_activity_responses{id}")
	public Tp_data_by_inn_for_business_activity_response update(@PathVariable Long id, @Valid @RequestBody Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_responseRequest) {
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id)
				.map(tp_data_by_inn ->{
					tp_data_by_inn.setCreatedAt(tp_data_by_inn_for_business_activity_responseRequest.getCreatedAt());
					tp_data_by_inn.setFullAddress(tp_data_by_inn_for_business_activity_responseRequest.getFullAddress());
					tp_data_by_inn.setFullName(tp_data_by_inn_for_business_activity_responseRequest.getFullName());
					tp_data_by_inn.setInn(tp_data_by_inn_for_business_activity_responseRequest.getInn());
					tp_data_by_inn.setRayonCode(tp_data_by_inn_for_business_activity_responseRequest.getRayonCode());
					tp_data_by_inn.setUpdatedAt(tp_data_by_inn_for_business_activity_responseRequest.getUpdatedAt());
					tp_data_by_inn.setZip(tp_data_by_inn_for_business_activity_responseRequest.getZip());
					return tp_data_by_inn_for_business_activity_responseRepository.save(tp_data_by_inn);
				}).orElseThrow(()-> new ResourceNotFoundException("Tp data by inn for business activity response not found with id "+id));
	}
	
	@DeleteMapping("/tp_data_by_inn_for_business_activity_responses{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id)
				.map(tp_data_by_inn -> {
					tp_data_by_inn_for_business_activity_responseRepository.delete(tp_data_by_inn);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Tp data by inn for business activity response not found with id "+id));
	}
}
