package com.webdatabase.dgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.model.DataSource;
import com.webdatabase.dgz.repository.DataSourceRepository;

import java.util.Optional;

import javax.validation.Valid;

@RestController
public class DataSourceController {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @GetMapping("/datasources")
    public Page<DataSource> getAll(Pageable pageable) {
        return dataSourceRepository.findAll(pageable);
    }

    @GetMapping("/datasources/{id}")
    public Optional<DataSource> getOne(@PathVariable Long id) {
        return dataSourceRepository.findById(id);
    }


    @PostMapping("/datasources")
    public DataSource create(@Valid @RequestBody DataSource dataSource) {
        return dataSourceRepository.save(dataSource);
    }

    @PutMapping("/datasources/{id}")
    public DataSource update(@PathVariable Long id,
                                   @Valid @RequestBody DataSource datasourceRequest) {
        return dataSourceRepository.findById(id)
                .map(datasource -> {
                	datasource.setName(datasourceRequest.getName());
                	datasource.setDescription(datasourceRequest.getDescription());
                    return dataSourceRepository.save(datasource);
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }


    @DeleteMapping("/datasources/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return dataSourceRepository.findById(id)
                .map(datasource -> {
                	dataSourceRepository.delete(datasource);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id " + id));
    }
}