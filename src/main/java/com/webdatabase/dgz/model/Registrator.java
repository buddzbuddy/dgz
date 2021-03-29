package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "registrators")
public class Registrator extends AuditModel {
	
	@Id
    @GeneratedValue(generator = "registrator_generator",
    		strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "registrator_generator",
            sequenceName = "registrator_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;
	
	private String name;
	
	@Nullable
	private int counterpart;
	
	private String contactData;

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

	public int getCounterpart() {
		return counterpart;
	}

	public void setCounterpart(int counterpart) {
		this.counterpart = counterpart;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}
}
