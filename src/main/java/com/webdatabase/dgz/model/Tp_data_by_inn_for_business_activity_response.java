package com.webdatabase.dgz.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class Tp_data_by_inn_for_business_activity_response {
	
	@Id
    @GeneratedValue(generator = "tp_data_by_inn_for_business_activity_response_generator")
    @SequenceGenerator(
            name = "tp_data_by_inn_for_business_activity_response_generator",
            sequenceName = "tp_data_by_inn_for_business_activity_response_sequence",
            initialValue = 1000
    )

	private String inn;
	
	private String rayonCode;
	
	private String fullName;
	
	private String fullAddress;
	
	private String zip;

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
