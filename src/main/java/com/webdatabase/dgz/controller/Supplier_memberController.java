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

import com.webdatabase.dgz.excelExport.Supplier_memberExcelExporter;
import com.webdatabase.dgz.excelUpload.Supplier_memberExcelUpload;
import com.webdatabase.dgz.exception.ResourceNotFoundException;
import com.webdatabase.dgz.message.ResponseMessage;
import com.webdatabase.dgz.model.Supplier_member;
import com.webdatabase.dgz.repository.Member_typeRepository;
import com.webdatabase.dgz.repository.SupplierRepository;
import com.webdatabase.dgz.repository.Supplier_memberRepository;
import com.webdatabase.dgz.service.Supplier_memberService;

@RestController
public class Supplier_memberController {
	@Autowired
	private Supplier_memberRepository supplier_memberRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private Member_typeRepository member_typeRepository;
	
	@GetMapping("/supplier_members")
	public Page<Supplier_member> getAllSupplier_member(Pageable pageable){
		return supplier_memberRepository.findAll(pageable);
	}
	
	@GetMapping("/supplier/{supplierId}/supplier_members")
	public List<Supplier_member> getSupplier_memberSupplier(@PathVariable Long supplierId){
		return supplier_memberRepository.findBySupplierId(supplierId);
	}
	
	@PostMapping("/supplier/{supplierId}/supplier_members")
	public Supplier_member addSupplier_memberSupplier(@PathVariable Long supplierId, @Valid @RequestBody Supplier_member supplier_member) {
		return supplierRepository.findById(supplierId)
				.map(supplier -> {
					supplier_member.setSupplier(supplier);
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id "+ supplierId));
	}
	
	@PutMapping("/supplier/{supplierId}/supplier_members/{supplier_memberId}")
	public Supplier_member updateSupplier_memberSupplier(@PathVariable Long supplier_memberId, @PathVariable Long supplierId, @Valid @RequestBody Supplier_member supplier_memberRequest) {
		if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id "+ supplierId);
		}
		
		return supplier_memberRepository.findById(supplier_memberId)
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
					supplier_member.setSurname(supplier_memberRequest.getSurname());
					supplier_member.setUpdatedAt(supplier_memberRequest.getUpdatedAt());
					supplier_member.setVoidStatus(supplier_memberRequest.getVoidStatus());
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ supplier_memberId));
	}
	
	@DeleteMapping("/supplier/{supplierId}/supplier_members/{supplier_memberId}")
	public ResponseEntity<?> deleteSuppler_memberSupplier(@PathVariable Long supplier_memberId, @PathVariable Long supplierId){
		if (!supplierRepository.existsById(supplierId)) {
			throw new ResourceNotFoundException("Supplier not found with id "+ supplierId);
		}
		return supplier_memberRepository.findById(supplier_memberId)
				.map(supplier_member -> {
					supplier_memberRepository.delete(supplier_member);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ supplier_memberId));
	}
	
	
	/*----------------------*/
	
	
	@GetMapping("/member_types/{member_typeId}/supplier_members")
	public List<Supplier_member> getSupplier_memberMember_type(@PathVariable Long member_typeId){
		return supplier_memberRepository.findByMember_typeId(member_typeId);
	}
	
	@PostMapping("/member_types/{member_typeId}/supplier_members")
	public Supplier_member addSupplier_memberMember_type(@PathVariable Long member_typeId, @Valid @RequestBody Supplier_member supplier_member) {
		return member_typeRepository.findById(member_typeId)
				.map(member_type -> {
					supplier_member.setMember_type(member_type);
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id "+ member_typeId));
	}
	
	@PutMapping("/member_types/{member_typeId}/supplier_members/{supplier_memberId}")
	public Supplier_member updateSupplier_memberMember_type(@PathVariable Long supplier_memberId, @PathVariable Long member_typeId, @Valid @RequestBody Supplier_member supplier_memberRequest) {
		if (!member_typeRepository.existsById(member_typeId)) {
			throw new ResourceNotFoundException("Member type not found with id "+ member_typeId);
		}
		
		return supplier_memberRepository.findById(supplier_memberId)
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
					supplier_member.setSurname(supplier_memberRequest.getSurname());
					supplier_member.setUpdatedAt(supplier_memberRequest.getUpdatedAt());
					supplier_member.setVoidStatus(supplier_memberRequest.getVoidStatus());
					return supplier_memberRepository.save(supplier_member);
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ supplier_memberId));
	}
	
	@DeleteMapping("/member_types/{member_typeId}/supplier_members/{supplier_memberId}")
	public ResponseEntity<?> deleteSuppler_memberMember_type(@PathVariable Long supplier_memberId, @PathVariable Long member_typeId){
		if (!member_typeRepository.existsById(member_typeId)) {
			throw new ResourceNotFoundException("Member type not found with id "+ member_typeId);
		}
		return supplier_memberRepository.findById(supplier_memberId)
				.map(supplier_member -> {
					supplier_memberRepository.delete(supplier_member);
					return ResponseEntity.ok().build();
				}).orElseThrow(() -> new ResourceNotFoundException("Supplier member not found with id "+ supplier_memberId));
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
