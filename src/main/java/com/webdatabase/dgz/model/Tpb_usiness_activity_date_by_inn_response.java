package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class Tpb_usiness_activity_date_by_inn_response extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "tpb_usiness_activity_date_by_inn_response_generator")
    @SequenceGenerator(
            name = "tpb_usiness_activity_date_by_inn_response_generator",
            sequenceName = "tpb_usiness_activity_date_by_inn_response_sequence",
            initialValue = 1000
    )
	
	private String legalAddress;
	
	private String name;
	
	private String rayonCode;
	
	private String rayonName;
	 
	private Date taxActiveDate;
	
	private String taxTypeCode;
	
	private String taxTypeName;
	
	private String tin;

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public String getRayonName() {
		return rayonName;
	}

	public void setRayonName(String rayonName) {
		this.rayonName = rayonName;
	}

	public Date getTaxActiveDate() {
		return taxActiveDate;
	}

	public void setTaxActiveDate(Date taxActiveDate) {
		this.taxActiveDate = taxActiveDate;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
	
}
