package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Appeal;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long>{
}
