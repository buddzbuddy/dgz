package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Supplier_member;

@Repository
public interface Supplier_memberRepository extends JpaRepository<Supplier_member, Long> {
	List<Supplier_member> findBySupplierId(Long supplierId);
	List<Supplier_member> findByMember_typeId(Long member_typeId); 
}
