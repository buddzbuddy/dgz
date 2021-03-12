package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table (name = "tp_data_by_inn_for_business_activity_responses")

public class Tp_data_by_inn_for_business_activity_response extends AuditModel {
	
	@Id
    @GeneratedValue(generator = "tp_data_by_inn_for_business_activity_response_generator")
    @SequenceGenerator(
            name = "tp_data_by_inn_for_business_activity_response_generator",
            sequenceName = "tp_data_by_inn_for_business_activity_response_sequence",
            initialValue = 1000
    )
	private Long id;

	private String inn;
	
	private String rayonCode;
	
	private String fullName;
	
	private String fullAddress;
	
	private String zip;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
