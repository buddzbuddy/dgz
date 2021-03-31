package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Datasource;

@Repository
public interface DatasourceRepository extends JpaRepository<Datasource, Long> {
}