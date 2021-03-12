package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Counterpart_type;

@Repository
public interface CounterpartTypeRepository extends JpaRepository <Counterpart_type, Long>{

}
