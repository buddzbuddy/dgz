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

import com.webdatabase.dgz.excelExport.Msec_detailExcelExporter;
import com.webdatabase.dgz.excelUpload.Msec_detailExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Msec_detail;
import com.webdatabase.dgz.repository.Msec_detailRepository;
import com.webdatabase.dgz.service.Msec_detailService;

@RestController
public class Msec_detailController {
	
	@Autowired
	private Msec_detailRepository msec_detailRepository;
	
	@GetMapping("msec_details")
	public Page<Msec_detail> getAllMsec_detail(Pageable pageable){
		return msec_detailRepository.findAll(pageable);
	}
	
	@GetMapping("msec_details{id}")
	public Optional<Msec_detail> getOneMsec_detail(@PathVariable Long id){
		return msec_detailRepository.findById(id);
	}
	
	@PostMapping("msec_details")
	public Msec_detail createMsec_detail(@Valid @RequestBody Msec_detail msec_detail) {
		return msec_detailRepository.save(msec_detail);
	}
	
	@PutMapping("msec_details{id}")
	public Msec_detail updateMsec_detail(@PathVariable Long id, @Valid @RequestBody Msec_detail msec_detailRequest) {
		return msec_detailRepository.findById(id)
				.map(msec_detail ->{
					msec_detail.setDisabilityGroup(msec_detailRequest.getDisabilityGroup());
					msec_detail.setExaminationDate(msec_detailRequest.getExaminationDate());
					msec_detail.setExaminationtype(msec_detailRequest.getExaminationtype());
					msec_detail.setFromDate(msec_detailRequest.getFromDate());
					msec_detail.setOrgnizationName(msec_detailRequest.getOrgnizationName());
					msec_detail.setReExamination(msec_detailRequest.getReExamination());
					msec_detail.setSupplier_member(msec_detailRequest.getSupplier_member());
					msec_detail.setToDate(msec_detailRequest.getToDate());
					return msec_detailRepository.save(msec_detail);
				}).orElseThrow(()-> new ResourceNotFoundException("Msec detail not found with id "+ id));
	}
	
	@DeleteMapping("msec_details{id}")
	public ResponseEntity<?> deleteMsec_detail(@PathVariable Long id){
		return msec_detailRepository.findById(id)
				.map(msec_detail -> {
					msec_detailRepository.delete(msec_detail);
					return ResponseEntity.ok().build();
				}).orElseThrow(()-> new ResourceNotFoundException("Msec detail not found with id "+id));
	}
	//export to Excel
	
	@Autowired
    private Msec_detailService msec_detailService;
    
    @GetMapping("/msec_details/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application.octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Dispostion";
    	String headerValue = "attachment; filename=msec_details_"+currentDate + ".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<Msec_detail> listMsec_details = msec_detailService.listAll();
    	
    	Msec_detailExcelExporter excelExport = new Msec_detailExcelExporter(listMsec_details);
    	
    	excelExport.export(response);
    }
    
    //upload from Excel
    
    @PostMapping("/msec_details/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Msec_detailExcelUpload.hasExcelFormat(file)) {
          try {
            msec_detailService.save(file);

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
    
    @GetMapping("/msec_details/excel")
    public ResponseEntity<List<Msec_detail>> getAllMsec_details() {
        try {
          List<Msec_detail> msec_details = msec_detailService.listAll();

          if (msec_details.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(msec_details, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
 	}
	
}
