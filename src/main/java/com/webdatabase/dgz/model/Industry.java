package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "industries")
public class Industry extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "industry_generator")
    @SequenceGenerator(
            name = "industry_generator",
            sequenceName = "industry_sequence",
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
