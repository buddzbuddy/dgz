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
import com.webdatabase.dgz.model.Supplier_member;
import com.webdatabase.dgz.repository.Supplier_memberRepository;

@RestController
public class Supplier_memberController {
	@Autowired
	private Supplier_memberRepository supplier_memberRepository;
	
	@GetMapping("/supplier_members")
	public Page<Supplier_member> getAllSupplier_member(Pageable pageable){
		return supplier_memberRepository.findAll(pageable);
	}
	
	@GetMapping("/supplier_members{id}")
	public Optional<Supplier_member> getOneSupplier_member(@PathVariable Long id){
		return supplier_memberRepository.findById(id);
	}
	
	@PostMapping("/supplier_members")
	public Supplier_member createSupplier_member(@Valid @RequestBody Supplier_member supplier_member) {
		return supplier_memberRepository.save(supplier_member);
	}
	
	@PutMapping("/supplier_members{id}")
	public Supplier_member updateSupplier_member(@PathVariable Long id, @Valid @RequestBody Supplier_member supplier_memberRequest) {
		return supplier_memberRepository.findById(id)
				.map(supplier_member -> {
					supplier_member.setAddressHouse(supplier_memberRequest.getAddressHouse());
					supplier_member.setAddressLocality(supplier_memberRequest.getAddressLocality());
					supplier_member.setAddressRegion(supplier_memberRequest.getAddressRegion());
					supplier_member.setAddressStreet(supplier_memberRequest.getAddressStreet());
					supplier_member.setAreaId(supplier_memberRequest.getAreaId());
					supplier_member.setCreatedAt(supplier_memberRequest.getCreatedAt());
					supplier_member.setDateOfBirth(supplier_memberRequest.getDateOfBirth());
					supplier_member.setDistrictId(supplier_memberRequest.getDistrictId());
					supplier_member.setExpiredDate(supplier_memberRequest.getExpiredDate());
					supplier_member.setFamilyStatus(supplier_member.getFamilyStatus());
					supplier_member.setGender(supplier_memberRequest.getGender());
					supplier_member.setHouseId(supplier_memberRequest.getHouseId());
					supplier_member.setInn(supplier_memberRequest.getInn());
					supplier_member.setIssuedDate(supplier_memberRequest.getIssuedDate());
					supplier_member.setMemberTypeId(supplier_memberRequest.getMemberTypeId());
					supplier_member.setName(supplier_memberRequest.getName());
					supplier_member.setNationality(supplier_memberRequest.getNationality());
					supplier_member.setPassportAuthority(supplier_memberRequest.getPassportAuthority());
					supplier_member.setPassportNumber(supplier_memberRequest.getPassportNumber());
					supplier_member.setPassportSeries(supplier_memberRequest.getPassportSeries());
					supplier_member.setPatronymic(supplier_memberRequest.getPatronymic());
					supplier_member.setPin(supplier_memberRequest.getPin());
					supplier_member.setRegionId(supplier_memberRequest.getRegionId());
					supplier_member.setStreetId(supplier_memberRequest.getStreetId());
					supplier_member.setSubareaId(supplier_memberRequest.getSubareaId());
					supplier_member.setSupplier(supplier_memberRequest.getSupplier());
					supplier_member.setSurname(supplier_memberRequest.getSurname());
					supplier_member.setUpdatedAt(supplier_memberRequest.getUpdatedAt());
					supplier_member.setVoidStatus(supplier_memberRequest.getVoidStatus());
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ id));
	}
	
	@DeleteMapping("/supplier_members{id}")
	public ResponseEntity<?> deleteSuppler_member(@PathVariable Long id){
		return supplier_memberRepository.findById(id)
				.map(supplier_member -> {
					supplier_memberRepository.delete(supplier_member);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ id));
	}
}
