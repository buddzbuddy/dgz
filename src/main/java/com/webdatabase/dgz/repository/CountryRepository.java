package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Country;

@Repository
public interface CountryRepository extends JpaRepository <Country, Long> {
}
