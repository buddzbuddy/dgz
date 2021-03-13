package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier extends AuditModel {
	
	@Id
    @GeneratedValue(generator = "supplier_generator")
    @SequenceGenerator(
            name = "supplier_generator",
            sequenceName = "supplier_sequence",
            initialValue = 1000
    )
	
    private Long id;
	
	private String name;
	
	private int ownership_type;
	
	private String inn;
	
	private String legalAddress;
	
	private String factAddress;
	
	private String telephone;
	
	private String bankName;
	
	private String bankAccount;
	
	private String bic;
	
	private String zip;
	
	private String rayonCode;
	
	private Boolean isResident;

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

	public int getOwnership_type() {
		return ownership_type;
	}

	public void setOwnership_type(int ownership_type) {
		this.ownership_type = ownership_type;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public String getFactAddress() {
		return factAddress;
	}

	public void setFactAddress(String factAddress) {
		this.factAddress = factAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getRayonCode() {
		return rayonCode;
	}

	public void setRayonCode(String rayonCode) {
		this.rayonCode = rayonCode;
	}

	public Boolean getIsResident() {
		return isResident;
	}

	public void setIsResident(Boolean isResident) {
		this.isResident = isResident;
	}
}
