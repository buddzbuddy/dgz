package com.webdatabase.dgz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdatabase.dgz.model.DatasourceField;
import com.webdatabase.dgz.query.utils.SearchCriteria;

@Repository
public interface DatasourceFieldRepository extends JpaRepository<DatasourceField, Long> {
	List<DatasourceField> findByDatasourceId(Long datasourceId);
}
