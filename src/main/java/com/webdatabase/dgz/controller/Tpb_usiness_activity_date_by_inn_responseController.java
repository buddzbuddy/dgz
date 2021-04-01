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

import com.webdatabase.dgz.excelExport.Tpb_usiness_activity_date_by_inn_responseExcelExporter;
import com.webdatabase.dgz.excelUpload.Tpb_usiness_activity_date_by_inn_responseExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Tpb_usiness_activity_date_by_inn_response;
import com.webdatabase.dgz.repository.Tpb_usiness_activity_date_by_inn_responseRepository;
import com.webdatabase.dgz.service.Tpb_usiness_activity_date_by_inn_responseService;


@RestController
public class Tpb_usiness_activity_date_by_inn_responseController {
	@Autowired
	private Tpb_usiness_activity_date_by_inn_responseRepository tpb_usiness_activity_date_by_inn_responseRepository;

	@GetMapping("/tpb_usiness_activity_date_by_inn_responses")
	public Page<Tpb_usiness_activity_date_by_inn_response> getAll(Pageable pageable){
		return tpb_usiness_activity_date_by_inn_responseRepository.findAll(pageable);
	}
	
	@GetMapping("/tpb_usiness_activity_date_by_inn_responses/{id}")
	public Optional<Tpb_usiness_activity_date_by_inn_response> getOne(@PathVariable Long id){
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id);
	}
	
	@PostMapping("/tpb_usiness_activity_date_by_inn_responses")
	public Tpb_usiness_activity_date_by_inn_response createTpb_usiness_activity_date_by_inn_response(@Valid @RequestBody Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_response) {
		return tpb_usiness_activity_date_by_inn_responseRepository.save(tpb_usiness_activity_date_by_inn_response);
	}
	
	@PutMapping("/tpb_usiness_activity_date_by_inn_responses/{id}")
	public Tpb_usiness_activity_date_by_inn_response updateTpb_usiness_activity_date_by_inn_response(@PathVariable Long id, @Valid @RequestBody Tpb_usiness_activity_date_by_inn_response tpb_usiness_activity_date_by_inn_responseRequest) {
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id)
				.map(tpb_usiness_activity -> {
					tpb_usiness_activity.setLegalAddress(tpb_usiness_activity_date_by_inn_responseRequest.getLegalAddress());
					tpb_usiness_activity.setName(tpb_usiness_activity_date_by_inn_responseRequest.getName());
					tpb_usiness_activity.setRayonCode(tpb_usiness_activity_date_by_inn_responseRequest.getRayonCode());
					tpb_usiness_activity.setRayonName(tpb_usiness_activity_date_by_inn_responseRequest.getRayonName());
					tpb_usiness_activity.setTaxActiveDate(tpb_usiness_activity_date_by_inn_responseRequest.getTaxActiveDate());
					tpb_usiness_activity.setTaxTypeCode(tpb_usiness_activity_date_by_inn_responseRequest.getTaxTypeCode());
					tpb_usiness_activity.setTaxTypeName(tpb_usiness_activity_date_by_inn_responseRequest.getTaxTypeName());
					tpb_usiness_activity.setTin(tpb_usiness_activity_date_by_inn_responseRequest.getTin());
					return tpb_usiness_activity_date_by_inn_responseRepository.save(tpb_usiness_activity);
				}).orElseThrow(() -> new ResourceNotFoundException("Tpb usiness activity date by inn response not found with id "+id));
	}
	
	@DeleteMapping("/tpb_usiness_activity_date_by_inn_responses/{id}")
	public ResponseEntity<?> deleteTpb_usiness_activity_date_by_inn_response(@PathVariable Long id) {
		return tpb_usiness_activity_date_by_inn_responseRepository.findById(id)
				.map(tpb_usiness_activity -> {
					tpb_usiness_activity_date_by_inn_responseRepository.delete(tpb_usiness_activity);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Tpb usiness activity date by inn response not found with id "+id));
	}
	
	//export to Excel
	
	@Autowired
	private Tpb_usiness_activity_date_by_inn_responseService service;
	
	@GetMapping("/tpb_usiness_activity_date_by_inn_responses/export/excel")
	public void exportToExce(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=tpb_usiness_activity_date_by_inn_responses_" + currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Tpb_usiness_activity_date_by_inn_response> listTpb_usiness_activity_date_by_inn_responses = service.listAll();
		
		Tpb_usiness_activity_date_by_inn_responseExcelExporter excelExporter = new Tpb_usiness_activity_date_by_inn_responseExcelExporter(listTpb_usiness_activity_date_by_inn_responses);
		
		excelExporter.export(response);
	}
	
	//upload from Excel
    
    @PostMapping("/tpb_usiness_activity_date_by_inn_responses/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Tpb_usiness_activity_date_by_inn_responseExcelUpload.hasExcelFormat(file)) {
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
    
    @GetMapping("/tpb_usiness_activity_date_by_inn_responses/excel")
    public ResponseEntity<List<Tpb_usiness_activity_date_by_inn_response>> getAll() {
        try {
          List<Tpb_usiness_activity_date_by_inn_response> tpb_usiness_activity_date_by_inn_responses = service.listAll();

          if (tpb_usiness_activity_date_by_inn_responses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(tpb_usiness_activity_date_by_inn_responses, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

