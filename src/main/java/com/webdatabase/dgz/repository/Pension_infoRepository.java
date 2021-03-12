package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Pension_info;

@Repository
public interface Pension_infoRepository extends JpaRepository<Pension_info, Long>{

}
