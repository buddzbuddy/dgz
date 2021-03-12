package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Audit_method_type;

@Repository
public interface Audit_method_typeRepository extends JpaRepository<Audit_method_type, Long>{

}
