package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Procuring_entity;

@Repository
public interface Procuring_entityRepository extends JpaRepository<Procuring_entity, Long> {

}
