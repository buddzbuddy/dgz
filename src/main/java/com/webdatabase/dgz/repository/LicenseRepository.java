package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.License;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long>{
	List<License> findBySupplierId(Long supplierId);
	/*List<License> findByLicense_typeId(Long license_typeId);*/
}
