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
import com.webdatabase.dgz.model.Currency;
import com.webdatabase.dgz.repository.CurrencyRepository;

@RestController
public class CurrencyController {
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@GetMapping("/currencies")
	public Page<Currency> getAllCurrencies(Pageable pageable){
		return currencyRepository.findAll(pageable);
	}
	
	@GetMapping("/currencies{id}")
	public Optional<Currency> getOneCurrency(@PathVariable Long id){
		return currencyRepository.findById(id);
	}
	
	@PostMapping("/currencies")
	public Currency createCurrency(@Valid @RequestBody Currency currency) {
		return currencyRepository.save(currency);
	}
	
	@PutMapping("/currencies{id}")
	public Currency updateCurrency(@PathVariable Long id, @Valid @RequestBody Currency currencyRequest) {
		return currencyRepository.findById(id)
				.map(currency -> {
					currency.setCreatedAt(currencyRequest.getCreatedAt());
					currency.setName(currencyRequest.getName());
					currency.setUpdatedAt(currencyRequest.getUpdatedAt());
					return currencyRepository.save(currency);
				}).orElseThrow(()-> new ResourceNotFoundException("Entity not found with id "+ id));
	}
	
	@DeleteMapping("/currencies{id}")
	public ResponseEntity<?> deleteCurrency(@PathVariable Long id){
		return currencyRepository.findById(id)
				.map(currency -> {
					currencyRepository.delete(currency);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id "+ id));
	}
}
