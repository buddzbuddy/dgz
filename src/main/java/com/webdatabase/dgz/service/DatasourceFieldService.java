package com.webdatabase.dgz.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webdatabase.dgz.model.DatasourceField;
import com.webdatabase.dgz.query.utils.DatasourceFieldSearchQueryCriteriaConsumer;
import com.webdatabase.dgz.query.utils.SearchCriteria;
import com.webdatabase.dgz.repository.DatasourceFieldRepository;

@Service
@Transactional
public class DatasourceFieldService {
	@Autowired
	private DatasourceFieldRepository repo;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public List<DatasourceField> searchField(List<SearchCriteria> params) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DatasourceField> query = builder.createQuery(DatasourceField.class);
        Root r = query.from(DatasourceField.class);

        Predicate predicate = builder.conjunction();

        DatasourceFieldSearchQueryCriteriaConsumer searchConsumer = 
          new DatasourceFieldSearchQueryCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<DatasourceField> result = entityManager.createQuery(query).getResultList();
        return result;
	}
}
