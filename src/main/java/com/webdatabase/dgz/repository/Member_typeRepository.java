package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Member_type;

@Repository
public interface Member_typeRepository extends JpaRepository<Member_type, Long>{

}
