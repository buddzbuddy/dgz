package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Appeal;
import com.webdatabase.dgz.model.Procuring_entity;
import com.webdatabase.dgz.model.Supplier;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long>{
	List<Supplier> findBySuppliersId(Long supplierId);
	List<Procuring_entity> fingByProcuring_entitiesId(Long procuring_entityId);
} 
