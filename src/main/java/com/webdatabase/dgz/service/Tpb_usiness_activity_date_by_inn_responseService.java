package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Tpb_usiness_activity_date_by_inn_responseExcelUpload;
import com.webdatabase.dgz.model.Tpb_usiness_activity_date_by_inn_response;
import com.webdatabase.dgz.repository.Tpb_usiness_activity_date_by_inn_responseRepository;

@Service
@Transactional
public class Tpb_usiness_activity_date_by_inn_responseService {
	@Autowired
	private Tpb_usiness_activity_date_by_inn_responseRepository tpb_usiness_activity_date_by_inn_responseRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Tpb_usiness_activity_date_by_inn_response> tpb_usiness_activity_date_by_inn_responses = Tpb_usiness_activity_date_by_inn_responseExcelUpload.excelToTpb_usiness_activity_date_by_inn_responses(file.getInputStream());
	      tpb_usiness_activity_date_by_inn_responseRepository.saveAll(tpb_usiness_activity_date_by_inn_responses);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Tpb_usiness_activity_date_by_inn_response> listAll(){
		return tpb_usiness_activity_date_by_inn_responseRepository.findAll(Sort.by("Tpb usiness activity date by inn response").ascending());
	}
}
