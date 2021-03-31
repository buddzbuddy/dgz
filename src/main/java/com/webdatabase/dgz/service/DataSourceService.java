package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.DataSourceExcelUpload;
import com.webdatabase.dgz.model.Datasource;
import com.webdatabase.dgz.repository.DatasourceRepository;

@Service
@Transactional
public class DataSourceService {
	@Autowired
	private DatasourceRepository datasourceRepository;
	
	 public void save(MultipartFile file) {
	    try {
	      List<Datasource> datasources = DataSourceExcelUpload.excelToDataSources(file.getInputStream());
	      datasourceRepository.saveAll(datasources);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Datasource> listAll(){
		return datasourceRepository.findAll(Sort.by("Data Source").ascending());
	}
}
