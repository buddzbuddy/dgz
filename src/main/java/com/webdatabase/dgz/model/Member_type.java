package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "member_types")
public class Member_type extends AuditModel{
	
	@Id
    @GeneratedValue(generator = "member_type_generator")
    @SequenceGenerator(
            name = "member_type_generator",
            sequenceName = "member_type_sequence",
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
