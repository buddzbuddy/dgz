package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "procuring_entities")
public class Procuring_entity extends AuditModel {

	@Id
    @GeneratedValue(generator = "procuring_entity_generator")
    @SequenceGenerator(
            name = "procuring_entity_generator",
            sequenceName = "procuring_entity_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	
    private Long id;
	
	private String inn;
	
	private String name;
	
	private String address;
	
	private String contactData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactData() {
		return contactData;
	}

	public void setContactData(String contactData) {
		this.contactData = contactData;
	}
}
