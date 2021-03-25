package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.DataSourceExcelUpload;
import com.webdatabase.dgz.model.DataSource;
import com.webdatabase.dgz.repository.DataSourceRepository;

@Service
@Transactional
public class DataSourceService {
	@Autowired
	private DataSourceRepository dataSourceRepository;
	
	 public void save(MultipartFile file) {
	    try {
	      List<DataSource> dataSources = DataSourceExcelUpload.excelToDataSources(file.getInputStream());
	      dataSourceRepository.saveAll(dataSources);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<DataSource> listAll(){
		return dataSourceRepository.findAll(Sort.by("Data Source").ascending());
	}
}
