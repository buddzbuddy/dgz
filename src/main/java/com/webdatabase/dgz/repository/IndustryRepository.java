package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Industry;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long>{

}
