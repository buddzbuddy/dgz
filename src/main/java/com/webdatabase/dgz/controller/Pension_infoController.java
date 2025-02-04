package com.webdatabase.dgz.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.webdatabase.dgz.excelExport.Pension_infoExcelExporter;
import com.webdatabase.dgz.excelUpload.Pension_infoExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Pension_info;
import com.webdatabase.dgz.repository.Pension_infoRepository;
import com.webdatabase.dgz.repository.Supplier_memberRepository;
import com.webdatabase.dgz.service.Pension_infoService;

@RestController
public class Pension_infoController {

	@Autowired
	private Pension_infoRepository pension_infoRepository;
	
	@Autowired 
	private Supplier_memberRepository supplier_memberRepository;
	
    @GetMapping("/supplier_members/{supplier_memberId}/pension_infos")
    public List<Pension_info> getPension_infosBySupplier_memberId(@PathVariable Long supplier_memberId) {
        return pension_infoRepository.findBySupplier_memberId(supplier_memberId);
    }


    @PostMapping("/supplier_members/{supplier_memberId}/pension_infos")
    public Pension_info addPension_infos(@PathVariable Long supplier_memberId, @Valid @RequestBody Pension_info pension_info) {
        return supplier_memberRepository.findById(supplier_memberId)
        		.map(supplier_member -> {
        			pension_info.setSupplier_member(supplier_member);
        			return pension_infoRepository.save(pension_info);
        		}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id" + supplier_memberId));
    }

    @PutMapping("/supplier_members/{supplier_memberId}/pension_infos/{pension_infoId}")
    public Pension_info update(@PathVariable Long supplier_memberId, @PathVariable Long pension_infoId,
                                   @Valid @RequestBody Pension_info pension_infoRequest) {
        if(!supplier_memberRepository.existsById(supplier_memberId)) {
        	throw new ResourceNotFoundException("Supplier member not found with id" + supplier_memberId);
        }
    	
    	return pension_infoRepository.findById(pension_infoId)
                .map(pension_info -> {
                	pension_info.setCategoryPension(pension_infoRequest.getCategoryPension());
                	pension_info.setDateFromInitial(pension_infoRequest.getDateFromInitial());
                	pension_info.setKindOfPension(pension_infoRequest.getKindOfPension());
                	pension_info.setNumDossier(pension_infoRequest.getNumDossier());
                	pension_info.setPinPensioner(pension_infoRequest.getPinPensioner());
                	pension_info.setPinRecipient(pension_infoRequest.getPinRecipient());
                	pension_info.setRusf(pension_infoRequest.getRusf());
                	pension_info.setSum(pension_infoRequest.getSum());
                    return pension_infoRepository.save(pension_info);
                }).orElseThrow(() -> new ResourceNotFoundException("Pension info not found with id " + pension_infoId));
    }


    @DeleteMapping("/supplier_members/{supplier_memberId}/pension_infos/{pension_infoId}")
    public ResponseEntity<?> deletePension_info(@PathVariable Long supplier_memberId, @PathVariable Long pension_infoId) {
        if (!supplier_memberRepository.existsById(supplier_memberId)) {
			throw new ResourceNotFoundException("Supplier member not found with id" + supplier_memberId);
		}
    	
    	return pension_infoRepository.findById(pension_infoId)
                .map(pension_info -> {
                	pension_infoRepository.delete(pension_info);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Pension info not found with id " + pension_infoId));
    }
    
    //export to Excel
    
    @Autowired
    private Pension_infoService pension_infoService;
    
    @GetMapping("/pension_infos/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
    	response.setContentType("application/octet-stream");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDate = dateFormat.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String headerValue = "attachment; filename=pension_infos_" + currentDate + ".xlsx";
    	response.setHeader(headerKey, headerValue);
    	
    	List<Pension_info> listPension_infos = pension_infoService.listAll();
    	
    	Pension_infoExcelExporter excelExporter = new Pension_infoExcelExporter(listPension_infos);
    	
    	excelExporter.export(response);
    }
    
    //upload from Excel
    
    @PostMapping("/pension_infos/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Pension_infoExcelUpload.hasExcelFormat(file)) {
          try {
            pension_infoService.save(file);

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
    
    @GetMapping("/pension_infos/excel")
    public ResponseEntity<List<Pension_info>> getAllPension_infos() {
        try {
          List<Pension_info> pension_infos = pension_infoService.listAll();

          if (pension_infos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(pension_infos, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
