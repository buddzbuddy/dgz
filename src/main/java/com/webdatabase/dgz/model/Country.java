package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "countries")
public class Country extends AuditModel {
	
	
	@Id
    @GeneratedValue(
    	generator = "country_generator"
    	
    )
    @SequenceGenerator(
            name = "country_generator",
            sequenceName = "country_sequence",
            initialValue = 1000,
            allocationSize = 1
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
