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

import com.webdatabase.dgz.excelExport.Member_typeExcelExporter;
import com.webdatabase.dgz.excelUpload.Member_typeExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;

import com.webdatabase.dgz.model.Member_type;
import com.webdatabase.dgz.repository.Member_typeRepository;
import com.webdatabase.dgz.service.Member_typeService;


@RestController
public class Member_typeController {

	@Autowired
	private Member_typeRepository member_typeRepository;
	
	@GetMapping("/member_types")
    public Page<Member_type> getAll(Pageable pageable) {
		return member_typeRepository.findAll(pageable);
    }

    @GetMapping("/member_types/{member_typeId}")
    public Optional<Member_type> getOne(@PathVariable Long member_typeId) {
        return member_typeRepository.findById(member_typeId);
    }


    @PostMapping("/member_types")
    public Member_type create(@Valid @RequestBody Member_type member_type) {
        return member_typeRepository.save(member_type);
    }

    @PutMapping("/member_types/{member_typeId}")
    public Member_type update(@PathVariable Long member_typeId,
                                   @Valid @RequestBody Member_type member_typeRequest) {
        return member_typeRepository.findById(member_typeId)
                .map(member_type -> {
                	member_type.setName(member_typeRequest.getName());
                    return member_typeRepository.save(member_type);
                }).orElseThrow(() -> new ResourceNotFoundException("Member type not found with id " + member_typeId));
    }


    @DeleteMapping("/member_types/{member_typeId}")
    public ResponseEntity<?> delete(@PathVariable Long member_typeId) {
        return member_typeRepository.findById(member_typeId)
                .map(member_type -> {
                	member_typeRepository.delete(member_type);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Member type not found with id " + member_typeId));
    }
    
    //export to Excel
    
    @Autowired
    private Member_typeService member_typeService;
    
    @GetMapping("/member_types/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application.octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Dispostion";
    	String headerValue = "attachment; filename=member_type_"+currentDate + ".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<Member_type> listMember_types = member_typeService.listAll();
    	
    	Member_typeExcelExporter excelExport = new Member_typeExcelExporter(listMember_types);
    	
    	excelExport.export(response);
    }
    
    
    //upload from Excel
    
    @PostMapping("/member_types/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Member_typeExcelUpload.hasExcelFormat(file)) {
          try {
            member_typeService.save(file);

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
    
    @GetMapping("/member_types/excel")
    public ResponseEntity<List<Member_type>> getAllMember_types() {
        try {
          List<Member_type> member_types = member_typeService.listAll();

          if (member_types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(member_types, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
