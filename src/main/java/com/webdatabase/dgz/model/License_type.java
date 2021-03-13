package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "license_types")
public class License_type extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "license_type_generator")
    @SequenceGenerator(
            name = "license_type_generator",
            sequenceName = "license_type_sequence",
            initialValue = 1000
    )
	
	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
