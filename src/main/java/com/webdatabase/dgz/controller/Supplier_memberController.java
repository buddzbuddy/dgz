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

import com.webdatabase.dgz.excelExport.Supplier_memberExcelExporter;
import com.webdatabase.dgz.excelUpload.Supplier_memberExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Supplier_member;
import com.webdatabase.dgz.repository.Supplier_memberRepository;
import com.webdatabase.dgz.service.Supplier_memberService;

@RestController
public class Supplier_memberController {
	@Autowired
	private Supplier_memberRepository supplier_memberRepository;
	
	@GetMapping("/supplier_members")
	public Page<Supplier_member> getAllSupplier_member(Pageable pageable){
		return supplier_memberRepository.findAll(pageable);
	}
	
	@GetMapping("/supplier_members{id}")
	public Optional<Supplier_member> getOneSupplier_member(@PathVariable Long id){
		return supplier_memberRepository.findById(id);
	}
	
	@PostMapping("/supplier_members")
	public Supplier_member createSupplier_member(@Valid @RequestBody Supplier_member supplier_member) {
		return supplier_memberRepository.save(supplier_member);
	}
	
	@PutMapping("/supplier_members{id}")
	public Supplier_member updateSupplier_member(@PathVariable Long id, @Valid @RequestBody Supplier_member supplier_memberRequest) {
		return supplier_memberRepository.findById(id)
				.map(supplier_member -> {
					supplier_member.setAddressHouse(supplier_memberRequest.getAddressHouse());
					supplier_member.setAddressLocality(supplier_memberRequest.getAddressLocality());
					supplier_member.setAddressRegion(supplier_memberRequest.getAddressRegion());
					supplier_member.setAddressStreet(supplier_memberRequest.getAddressStreet());
					supplier_member.setAreaId(supplier_memberRequest.getAreaId());
					supplier_member.setCreatedAt(supplier_memberRequest.getCreatedAt());
					supplier_member.setDateOfBirth(supplier_memberRequest.getDateOfBirth());
					supplier_member.setDistrictId(supplier_memberRequest.getDistrictId());
					supplier_member.setExpiredDate(supplier_memberRequest.getExpiredDate());
					supplier_member.setFamilyStatus(supplier_member.getFamilyStatus());
					supplier_member.setGender(supplier_memberRequest.getGender());
					supplier_member.setHouseId(supplier_memberRequest.getHouseId());
					supplier_member.setInn(supplier_memberRequest.getInn());
					supplier_member.setIssuedDate(supplier_memberRequest.getIssuedDate());
					supplier_member.setMemberTypeId(supplier_memberRequest.getMemberTypeId());
					supplier_member.setName(supplier_memberRequest.getName());
					supplier_member.setNationality(supplier_memberRequest.getNationality());
					supplier_member.setPassportAuthority(supplier_memberRequest.getPassportAuthority());
					supplier_member.setPassportNumber(supplier_memberRequest.getPassportNumber());
					supplier_member.setPassportSeries(supplier_memberRequest.getPassportSeries());
					supplier_member.setPatronymic(supplier_memberRequest.getPatronymic());
					supplier_member.setPin(supplier_memberRequest.getPin());
					supplier_member.setRegionId(supplier_memberRequest.getRegionId());
					supplier_member.setStreetId(supplier_memberRequest.getStreetId());
					supplier_member.setSubareaId(supplier_memberRequest.getSubareaId());
					supplier_member.setMember_type(supplier_memberRequest.getMember_type());
					supplier_member.setSurname(supplier_memberRequest.getSurname());
					supplier_member.setUpdatedAt(supplier_memberRequest.getUpdatedAt());
					supplier_member.setVoidStatus(supplier_memberRequest.getVoidStatus());
					supplier_member.set_supplier(supplier_memberRequest.get_supplier());
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ id));
	}
	
	@DeleteMapping("/supplier_members{id}")
	public ResponseEntity<?> deleteSuppler_member(@PathVariable Long id){
		return supplier_memberRepository.findById(id)
				.map(supplier_member -> {
					supplier_memberRepository.delete(supplier_member);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ id));
	}
	
	//export to Excel
	
	@Autowired
	private Supplier_memberService supplier_memberService;
	
	@GetMapping("/supplier_members/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDate = dateFormat.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=supplier_member_"+ currentDate + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Supplier_member> listSupplier_members = supplier_memberService.listAll();
		
		Supplier_memberExcelExporter excelExporter = new Supplier_memberExcelExporter(listSupplier_members);
		
		excelExporter.export(response);
	}
	
	
	//upload from Excel
    
    @PostMapping("/supplier_members/upload/excel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Supplier_memberExcelUpload.hasExcelFormat(file)) {
          try {
            supplier_memberService.save(file);

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
    
    @GetMapping("/supplier_members/excel")
    public ResponseEntity<List<Supplier_member>> getAllSupplier_members() {
        try {
          List<Supplier_member> supplier_members = supplier_memberService.listAll();

          if (supplier_members.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(supplier_members, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
