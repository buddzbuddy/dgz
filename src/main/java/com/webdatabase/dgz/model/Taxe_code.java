package com.webdatabase.dgz.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class Taxe_code extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "taxe_code_generator")
    @SequenceGenerator(
            name = "taxe_code_generator",
            sequenceName = "taxe_code_sequence",
            initialValue = 1000
    )
	
	private String code;
	
	private String name;
	
	private String detailName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	
	
}
