package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Ownership_type;

@Repository
public interface Ownership_typeRepository extends JpaRepository<Ownership_type, Long> {	
	
}
