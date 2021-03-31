package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Pension_info;

@Repository
public interface Pension_infoRepository extends JpaRepository<Pension_info, Long>{
	List<Pension_info> findBySupplier_memberId(Long supplier_memberId);
}
