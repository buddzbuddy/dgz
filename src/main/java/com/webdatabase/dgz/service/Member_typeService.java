package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Member_typeExcelUpload;
import com.webdatabase.dgz.model.Member_type;
import com.webdatabase.dgz.repository.Member_typeRepository;

@Service
@Transactional
public class Member_typeService {
	@Autowired
	private Member_typeRepository member_typeRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Member_type> member_types = Member_typeExcelUpload.excelToMember_types(file.getInputStream());
	      member_typeRepository.saveAll(member_types);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Member_type> listAll(){
		return member_typeRepository.findAll(Sort.by("Member type").ascending());
	}
}
