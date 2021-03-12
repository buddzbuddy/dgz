package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;

@Repository
public interface Tp_data_by_inn_for_business_activity_responseRepository extends JpaRepository<Tp_data_by_inn_for_business_activity_response, Long> {

}
