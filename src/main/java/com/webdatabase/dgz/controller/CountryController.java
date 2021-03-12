package com.webdatabase.dgz.controller;

import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webdatabase.dgz.model.Country;
import com.webdatabase.dgz.repository.CountryRepository;
import com.webdatabase.dgz.exception.ResourceNotFoundException;


@RestController
public class CountryController {
	
	@Autowired
	private CountryRepository countriesRepository;
	
	@GetMapping("/countries")
	public Page<Country> getAllCountries(Pageable pageable){
		return countriesRepository.findAll(pageable);
	}
	
	@GetMapping("/countries{Id}")
	public Optional<Country> getOneCountry(@PathVariable Long id){
		return countriesRepository.findById(id);
	}
	
	@PostMapping("/countries")
	public Country createCountry(@Valid @RequestBody Country country) {
		return countriesRepository.save(country);
	}
	
	@PutMapping("/countries{id}")
	public Country updateCountry(@PathVariable Long id, @Valid @RequestBody Country countryRequest) {
		return countriesRepository.findById(id)
				.map(country -> {
					country.setName(countryRequest.getName());
					return countriesRepository.save(country);
				}).orElseThrow(() -> new ResourceNotFoundException("Country not found with id "+ id));
	}
	
	@DeleteMapping("/countries{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable Long id){
		return countriesRepository.findById(id)
			.map(country -> {
				countriesRepository.delete(country);
				return ResponseEntity.ok().build();
			}).orElseThrow(() -> new ResourceNotFoundException("Country not found with id "+ id));
	}

}
