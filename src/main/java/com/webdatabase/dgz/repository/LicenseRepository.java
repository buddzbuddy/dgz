package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long>{

}
