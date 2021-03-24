package com.webdatabase.dgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.model.DatasourceField;
import com.webdatabase.dgz.repository.DataSourceRepository;
import com.webdatabase.dgz.repository.DatasourceFieldRepository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
public class DatasourceFieldController {

    @Autowired
    private DatasourceFieldRepository datasourceFieldRepository;
    
    @Autowired
    private DataSourceRepository datasourceRepo;

    @GetMapping("/datasources/{datasourceId}/fields")
    public List<DatasourceField> getFieldsByDatasourceId(@PathVariable Long datasourceId) {
        return datasourceFieldRepository.findByDatasourceId(datasourceId);
    }

    @PostMapping("/datasources/{datasourceId}/fields")
    public DatasourceField addField(@PathVariable Long datasourceId,
    		@Valid @RequestBody DatasourceField datasourceField) {
    	return datasourceRepo.findById(datasourceId)
                .map(datasource -> {
                    datasourceField.setDatasource(datasource);
                    return datasourceFieldRepository.save(datasourceField);
                }).orElseThrow(() -> new ResourceNotFoundException("datasource not found with id " + datasourceId));
    }

    @PutMapping("/datasources/{datasourceId}/fields/{id}")
    public DatasourceField updateField(
    		@PathVariable Long datasourceId,
    		@PathVariable Long id,
    		@Valid @RequestBody DatasourceField datasourceFieldRequest) {
        if(!datasourceRepo.existsById(datasourceId)) {
        	throw new ResourceNotFoundException("Datasource not found with id " + datasourceId);
        }
    	return datasourceFieldRepository.findById(id)
                .map(datasourceField -> {
                	datasourceField.setName(datasourceFieldRequest.getName());
                	datasourceField.setLabel(datasourceFieldRequest.getLabel());
                	datasourceField.setDataType(datasourceFieldRequest.getDataType());
                    return datasourceFieldRepository.save(datasourceField);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/datasources/{datasourceId}/fields/{id}")
    public ResponseEntity<?> deleteField(@PathVariable Long datasourceId,
    		@PathVariable Long id) {
    	if(!datasourceRepo.existsById(datasourceId)) {
            throw new ResourceNotFoundException("Datasource not found with id " + datasourceId);
        }
        return datasourceFieldRepository.findById(id)
                .map(datasourceField -> {
                	datasourceFieldRepository.delete(datasourceField);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}
