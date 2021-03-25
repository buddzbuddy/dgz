package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.SupplierExcelUpload;
import com.webdatabase.dgz.model.Supplier;
import com.webdatabase.dgz.repository.SupplierRepository;

@Service
@Transactional
public class SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Supplier> suppliers = SupplierExcelUpload.excelToSuppliers(file.getInputStream());
	      supplierRepository.saveAll(suppliers);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Supplier> listAll(){
		return supplierRepository.findAll(Sort.by("Supplier").ascending());
	}
}
