package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Appeal;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long>{
	List<Appeal> findBySupplierId(Long supplierId);
	/*List<Appeal> fingByProcuring_entityId(Long procuring_entityId);*/
} 
