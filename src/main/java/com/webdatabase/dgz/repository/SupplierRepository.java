package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	/*List<Supplier> findByOwnership_typeId(Long ownership_typeId);*/
	List<Supplier> findByIndustryId(Long industryId);
}
