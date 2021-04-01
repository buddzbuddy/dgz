package com.webdatabase.dgz.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webdatabase.dgz.excelExport.Tp_data_by_inn_for_business_activity_responseExcelExporter;
import com.webdatabase.dgz.excelUpload.Tp_data_by_inn_for_business_activity_responseExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Tp_data_by_inn_for_business_activity_response;
import com.webdatabase.dgz.repository.Tp_data_by_inn_for_business_activity_responseRepository;
import com.webdatabase.dgz.service.Tp_data_by_inn_for_business_activity_responseService;

@RestController
public class Tp_data_by_inn_for_business_activity_responseController {
	@Autowired
	private Tp_data_by_inn_for_business_activity_responseRepository tp_data_by_inn_for_business_activity_responseRepository;

	@GetMapping("/tp_data_by_inn_for_business_activity_responses")
	public Page<Tp_data_by_inn_for_business_activity_response> getAll(Pageable pageable){
		return tp_data_by_inn_for_business_activity_responseRepository.findAll(pageable);
	}
	
	@GetMapping("/tp_data_by_inn_for_business_activity_responses/{id}")
	public Optional<Tp_data_by_inn_for_business_activity_response> getOne(@PathVariable Long id){
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id);
	}
		
	@PostMapping("/tp_data_by_inn_for_business_activity_responses")
	public Tp_data_by_inn_for_business_activity_response create(@Valid @RequestBody Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_response) {
		return tp_data_by_inn_for_business_activity_responseRepository.save(tp_data_by_inn_for_business_activity_response);
	}
	
	@PutMapping("/tp_data_by_inn_for_business_activity_responses/{id}")
	public Tp_data_by_inn_for_business_activity_response update(@PathVariable Long id, @Valid @RequestBody Tp_data_by_inn_for_business_activity_response tp_data_by_inn_for_business_activity_responseRequest) {
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id)
				.map(tp_data_by_inn ->{
					tp_data_by_inn.setFullAddress(tp_data_by_inn_for_business_activity_responseRequest.getFullAddress());
					tp_data_by_inn.setFullName(tp_data_by_inn_for_business_activity_responseRequest.getFullName());
					tp_data_by_inn.setInn(tp_data_by_inn_for_business_activity_responseRequest.getInn());
					tp_data_by_inn.setRayonCode(tp_data_by_inn_for_business_activity_responseRequest.getRayonCode());
					tp_data_by_inn.setZip(tp_data_by_inn_for_business_activity_responseRequest.getZip());
					return tp_data_by_inn_for_business_activity_responseRepository.save(tp_data_by_inn);
				}).orElseThrow(()-> new ResourceNotFoundException("Tp data by inn for business activity response not found with id "+id));
	}
	
	@DeleteMapping("/tp_data_by_inn_for_business_activity_responses/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		return tp_data_by_inn_for_business_activity_responseRepository.findById(id)
				.map(tp_data_by_inn -> {
					tp_data_by_inn_for_business_activity_responseRepository.delete(tp_data_by_inn);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Tp data by inn for business activity response not found with id "+id));
	}
	
	//export to Excel
	
	@Autowired
	private Tp_data_by_inn_for_business_activity_responseService service;
	
	@GetMapping("/tp_data_by_inn_for_business_activity_responses/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=tp_data_by_inn_for_business_activity_responses_" + currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Tp_data_by_inn_for_business_activity_response> listTp_data_by_inn_for_business_activity_responses = service.listAll();
		
		Tp_data_by_inn_for_business_activity_responseExcelExporter excelExporter = new Tp_data_by_inn_for_business_activity_responseExcelExporter(listTp_data_by_inn_for_business_activity_responses);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/tp_data_by_inn_for_business_activity_responses/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Tp_data_by_inn_for_business_activity_responseExcelUpload.hasExcelFormat(file)) {
          try {
            service.save(file);

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
    
    @GetMapping("/tp_data_by_inn_for_business_activity_responses/excel")
    public ResponseEntity<List<Tp_data_by_inn_for_business_activity_response>> getAll() {
        try {
          List<Tp_data_by_inn_for_business_activity_response> tp_data_by_inn_for_business_activity_responses = service.listAll();

          if (tp_data_by_inn_for_business_activity_responses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(tp_data_by_inn_for_business_activity_responses, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
