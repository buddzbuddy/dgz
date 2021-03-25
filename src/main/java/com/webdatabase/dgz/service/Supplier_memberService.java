package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Supplier_memberExcelUpload;
import com.webdatabase.dgz.model.Supplier_member;
import com.webdatabase.dgz.repository.Supplier_memberRepository;

@Service
@Transactional
public class Supplier_memberService {
	@Autowired
	private Supplier_memberRepository supplier_memberRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Supplier_member> supplier_members = Supplier_memberExcelUpload.excelToSupplier_members(file.getInputStream());
	      supplier_memberRepository.saveAll(supplier_members);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Supplier_member> listAll(){
		return supplier_memberRepository.findAll(Sort.by("Supplier member").ascending());
	}
}
