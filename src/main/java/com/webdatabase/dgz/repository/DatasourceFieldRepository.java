package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.DatasourceField;

@Repository
public interface DatasourceFieldRepository extends JpaRepository<DatasourceField, Long> {
	List<DatasourceField> findByDatasourceId(Long datasourceId);
}
