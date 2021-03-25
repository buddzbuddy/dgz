package com.webdatabase.dgz.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelUpload.Tp_data_by_inn_for_business_activity_responseExcelUpload;
import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;
import com.webdatabase.dgz.repository.Tp_data_by_inn_for_business_activity_responseRepository;

@Service
@Transactional
public class Tp_data_by_inn_for_business_activity_responseService {
	@Autowired
	private Tp_data_by_inn_for_business_activity_responseRepository tp_data_by_inn_for_business_activity_responseRepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<Tp_data_by_inn_for_business_activity_response> tp_data_by_inn_for_business_activity_responses = Tp_data_by_inn_for_business_activity_responseExcelUpload.excelToTp_data_by_inn_for_business_activity_responses(file.getInputStream());
	      tp_data_by_inn_for_business_activity_responseRepository.saveAll(tp_data_by_inn_for_business_activity_responses);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	}
	
	public List<Tp_data_by_inn_for_business_activity_response> listAll(){
		return tp_data_by_inn_for_business_activity_responseRepository.findAll(Sort.by("Tp data by inn for business activity response").ascending());
	}
}
