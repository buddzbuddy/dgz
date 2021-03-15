package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "counterpart_types")
public class Counterpart_type extends AuditModel {
	
	@Id
    @GeneratedValue(generator = "counterpart_type_generator")
    @SequenceGenerator(
            name = "counterpart_type_generator",
            sequenceName = "counterpart_type_sequence",
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
