package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Supplier_member;

@Repository
public interface Supplier_memberRepository extends JpaRepository<Supplier_member, Long> {

}
