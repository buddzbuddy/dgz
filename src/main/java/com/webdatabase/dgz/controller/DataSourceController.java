package com.webdatabase.dgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelExport.DataSourceExcelExporter;
import com.webdatabase.dgz.excelUpload.DataSourceExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Datasource;
import com.webdatabase.dgz.repository.DatasourceRepository;
import com.webdatabase.dgz.service.DataSourceService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class DataSourceController {

    @Autowired
    private DatasourceRepository dataSourceRepository;

    @GetMapping("/datasources")
    public Page<Datasource> getAll(Pageable pageable) {
        return dataSourceRepository.findAll(pageable);
    }

    @GetMapping("/datasources/{id}")
    public Optional<Datasource> getOne(@PathVariable Long id) {
        return dataSourceRepository.findById(id);
    }


    @PostMapping("/datasources")
    public Datasource create(@Valid @RequestBody Datasource dataSource) {
        return dataSourceRepository.save(dataSource);
    }

    @PutMapping("/datasources/{id}")
    public Datasource update(@PathVariable Long id,
                                   @Valid @RequestBody Datasource datasourceRequest) {
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
    
    //export to Excel
    
    @Autowired
    private DataSourceService dataSourceService;
    
    @GetMapping("/datasources/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application.octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Dispostion";
    	String headerValue = "attachment; filename=dataSource_"+currentDate + ".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<Datasource> listDataSources = dataSourceService.listAll();
    	
    	DataSourceExcelExporter excelExport = new DataSourceExcelExporter(listDataSources);
    	
    	excelExport.export(response);
    }
    
    // upload from Excel
    
    @PostMapping("/datasources/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (DataSourceExcelUpload.hasExcelFormat(file)) {
          try {
            dataSourceService.save(file);

            message = "Файл успешно загружен: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
          } catch (Exception e) {
            message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
          }
        }

        message = "Загрузите файл в формате Excel!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
    
    @GetMapping("/datasources/excel")
    public ResponseEntity<List<Datasource>> getAllDataSources() {
        try {
          List<Datasource> datasources = dataSourceService.listAll();

          if (datasources.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(datasources, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
 	}

}