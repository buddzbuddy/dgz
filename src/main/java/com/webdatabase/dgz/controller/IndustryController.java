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

import com.webdatabase.dgz.excelExport.IndustryExcelExporter;
import com.webdatabase.dgz.excelUpload.IndustryExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Industry;
import com.webdatabase.dgz.repository.IndustryRepository;
import com.webdatabase.dgz.service.IndustryService;

@RestController
public class IndustryController {

	@Autowired
	private IndustryRepository industryRepository;
	
	@GetMapping("/industries")
    public Page<Industry> getAll(Pageable pageable) {
		return industryRepository.findAll(pageable);
    }

    @GetMapping("/industries/{industryId}")
    public Optional<Industry> getOne(@PathVariable Long industryId) {
        return industryRepository.findById(industryId);
    }


    @PostMapping("/industries")
    public Industry create(@Valid @RequestBody Industry industry) {
        return industryRepository.save(industry);
    }

    @PutMapping("/industries/{industryId}")
    public Industry update(@PathVariable Long industryId,
                                   @Valid @RequestBody Industry industryRequest) {
        return industryRepository.findById(industryId)
                .map(industry -> {
                	industry.setName(industryRequest.getName());
                    return industryRepository.save(industry);
                }).orElseThrow(() -> new ResourceNotFoundException("Industry not found with id " + industryId));
    }


    @DeleteMapping("/industries/{id}")
    public ResponseEntity<?> delete(@PathVariable Long industryId) {
        return industryRepository.findById(industryId)
                .map(industry -> {
                	industryRepository.delete(industry);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Industry not found with id " + industryId));
    }
    
    // export to Excel
    
    @Autowired
    private IndustryService industryService;
    
    @GetMapping("/industries/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application/octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-HH-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=industries_"+currentDate +".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<Industry> listIndustries = industryService.listAll();
    	
    	IndustryExcelExporter excelExport = new IndustryExcelExporter(listIndustries);
    	
    	excelExport.export(response);
    }
    
    //upload from Excel
    
    @PostMapping("/industries/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (IndustryExcelUpload.hasExcelFormat(file)) {
          try {
            industryService.save(file);

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
    
    @GetMapping("/industries/excel")
    public ResponseEntity<List<Industry>> getAllIndustries() {
        try {
          List<Industry> industries = industryService.listAll();

          if (industries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(industries, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }
}
