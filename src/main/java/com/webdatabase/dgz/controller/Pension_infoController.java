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
import com.webdatabase.dgz.model.Pension_info;
import com.webdatabase.dgz.repository.Pension_infoRepository;

@RestController
public class Pension_infoController {

	@Autowired
	private Pension_infoRepository pension_infoRepository;
	
	@GetMapping("/pension_infos")
    public Page<Pension_info> getAll(Pageable pageable) {
        return pension_infoRepository.findAll(pageable);
    }

    @GetMapping("/pension_infos/{id}")
    public Optional<Pension_info> getOne(@PathVariable Long id) {
        return pension_infoRepository.findById(id);
    }


    @PostMapping("/pension_infos")
    public Pension_info create(@Valid @RequestBody Pension_info pension_info) {
        return pension_infoRepository.save(pension_info);
    }

    @PutMapping("/pension_infos/{id}")
    public Pension_info update(@PathVariable Long id,
                                   @Valid @RequestBody Pension_info pension_infoRequest) {
        return pension_infoRepository.findById(id)
                .map(pension_info -> {
                	pension_info.setCategoryPension(pension_infoRequest.getCategoryPension());
                	pension_info.setCreatedAt(pension_infoRequest.getCreatedAt());
                	pension_info.setDateFromInitial(pension_infoRequest.getDateFromInitial());
                	pension_info.setKindOfPension(pension_infoRequest.getKindOfPension());
                	pension_info.setNumDossier(pension_infoRequest.getNumDossier());
                	pension_info.setPinPensioner(pension_infoRequest.getPinPensioner());
                	pension_info.setPinRecipient(pension_infoRequest.getPinRecipient());
                	pension_info.setRusf(pension_infoRequest.getRusf());
                	pension_info.setSum(pension_infoRequest.getSum());
                	pension_info.setSupplier_member(pension_infoRequest.getSupplier_member());;
                	pension_info.setUpdatedAt(pension_infoRequest.getUpdatedAt());
                    return pension_infoRepository.save(pension_info);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/pension_infos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return pension_infoRepository.findById(id)
                .map(pension_info -> {
                	pension_infoRepository.delete(pension_info);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
