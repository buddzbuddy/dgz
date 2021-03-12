package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Registrator;

@Repository
public interface RegistratorRepository extends JpaRepository<Registrator, Long> {
}
