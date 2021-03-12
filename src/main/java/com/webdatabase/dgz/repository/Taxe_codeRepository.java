package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Taxe_code;

@Repository
public interface Taxe_codeRepository extends JpaRepository<Taxe_code, Long> {

}
