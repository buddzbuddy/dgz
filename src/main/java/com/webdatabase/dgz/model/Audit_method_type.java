package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "audit_method_types")
public class Audit_method_type extends AuditModel{

	@Id
    @GeneratedValue(generator = "audit_method_type_generator")
    @SequenceGenerator(
            name = "audit_method_type_generator",
            sequenceName = "audit_method_type_sequence",
            initialValue = 1000
    )
	
	private String code;
	
	private String name;

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
	
}
