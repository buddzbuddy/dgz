package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "taxe_codes")
public class Taxe_code extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "taxe_code_generator")
    @SequenceGenerator(
            name = "taxe_code_generator",
            sequenceName = "taxe_code_sequence",
            initialValue = 1000
    )
	private Long id;
	
	private String code;
	
	private String name;
	
	private String detailName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

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
