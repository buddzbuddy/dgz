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
import com.webdatabase.dgz.model.Tpb_usiness_activity_date_by_inn_response;
import com.webdatabase.dgz.repository.Tpb_usiness_activity_date_by_inn_responseRepository;


@RestController
public class Tpb_usiness_activity_date_by_inn_responseController {
	@Autowired
	private Tpb_usiness_activity_date_by_inn_responseRepository tpb_usiness_activity_date_by_inn_responseRepository;

	@GetMapping("/tpb_usiness_activity_date_by_inn_responses")
	public Page<Tpb_usiness_activity_date_by_inn_response> getAll(Pageable pageable){
		return tpb_usiness_activity_date_by_inn_responseRepository.findAll(pageable);
	}
	
	@GetMapping("/tpb_usiness_activity_date_by_inn_responses{id}")
	public Optional<Tpb_usiness_activity_date_by_inn_response> getOne(@PathVariable Long id){
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id);
	}
	
	@PostMapping("/tpb_usiness_activity_date_by_inn_responses")
	public Tpb_usiness_activity_date_by_inn_response createTpb_usiness_activity_date_by_inn_response(@Valid @RequestBody Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_response) {
		return tpb_usiness_activity_date_by_inn_responseRepository.save(tpb_usiness_activity_date_by_inn_response);
	}
	
	@PutMapping("/tpb_usiness_activity_date_by_inn_responses{id}")
	public Tpb_usiness_activity_date_by_inn_response updateTpb_usiness_activity_date_by_inn_response(@PathVariable Long id, @Valid @RequestBody Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_responseRequest) {
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id)
				.map(tpb_usiness_activity -> {
					tpb_usiness_activity.setCreatedAt(tpb_usiness_activity_date_by_inn_responseRequest.getCreatedAt());
					tpb_usiness_activity.setLegalAddress(tpb_usiness_activity_date_by_inn_responseRequest.getLegalAddress());
					tpb_usiness_activity.setName(tpb_usiness_activity_date_by_inn_responseRequest.getName());
					tpb_usiness_activity.setRayonCode(tpb_usiness_activity_date_by_inn_responseRequest.getRayonCode());
					tpb_usiness_activity.setRayonName(tpb_usiness_activity_date_by_inn_responseRequest.getRayonName());
					tpb_usiness_activity.setTaxActiveDate(tpb_usiness_activity_date_by_inn_responseRequest.getTaxActiveDate());
					tpb_usiness_activity.setTaxTypeCode(tpb_usiness_activity_date_by_inn_responseRequest.getTaxTypeCode());
					tpb_usiness_activity.setTaxTypeName(tpb_usiness_activity_date_by_inn_responseRequest.getTaxTypeName());
					tpb_usiness_activity.setTin(tpb_usiness_activity_date_by_inn_responseRequest.getTin());
					tpb_usiness_activity.setUpdatedAt(tpb_usiness_activity_date_by_inn_responseRequest.getUpdatedAt());
					return tpb_usiness_activity_date_by_inn_responseRepository.save(tpb_usiness_activity);
				}).orElseThrow(() -> new ResourceNotFoundException("Tpb usiness activity date by inn response not found with id "+id));
	}
	
	@DeleteMapping("/tpb_usiness_activity_date_by_inn_responses{id}")
	public ResponseEntity<?> deleteTpb_usiness_activity_date_by_inn_response(@PathVariable Long id) {
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id)
				.map(tpb_usiness_activity -> {
					tpb_usiness_activity_date_by_inn_responseRepository.delete(tpb_usiness_activity);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Tpb usiness activity date by inn response not found with id "+id));
	}
}

