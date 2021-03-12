package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Counterpart;

@Repository
public interface CounterpartRepository extends JpaRepository<Counterpart, Long>{
}
