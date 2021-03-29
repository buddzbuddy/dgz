package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pension_infos")
public class Pension_info extends AuditModel{

	@Id
    @GeneratedValue(generator = "pension_info_generator")
    @SequenceGenerator(
            name = "pension_info_generator",
            sequenceName = "pension_info_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_member_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Supplier_member supplier_member;
	
	private String rusf;
	
	private String numDossier;
	
	private String pinPensioner;
	
	private String pinRecipient;
	
	private String dateFromInitial;
	
	private String sum;
	
	private String kindOfPension;
	
	private String categoryPension;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier_member getSupplier_member() {
		return supplier_member;
	}

	public void setSupplier_member(Supplier_member supplier_member) {
		this.supplier_member = supplier_member;
	}

	public String getRusf() {
		return rusf;
	}

	public void setRusf(String rusf) {
		this.rusf = rusf;
	}

	public String getNumDossier() {
		return numDossier;
	}

	public void setNumDossier(String numDossier) {
		this.numDossier = numDossier;
	}

	public String getPinPensioner() {
		return pinPensioner;
	}

	public void setPinPensioner(String pinPensioner) {
		this.pinPensioner = pinPensioner;
	}

	public String getPinRecipient() {
		return pinRecipient;
	}

	public void setPinRecipient(String pinRecipient) {
		this.pinRecipient = pinRecipient;
	}

	public String getDateFromInitial() {
		return dateFromInitial;
	}

	public void setDateFromInitial(String dateFromInitial) {
		this.dateFromInitial = dateFromInitial;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getKindOfPension() {
		return kindOfPension;
	}

	public void setKindOfPension(String kindOfPension) {
		this.kindOfPension = kindOfPension;
	}

	public String getCategoryPension() {
		return categoryPension;
	}

	public void setCategoryPension(String categoryPension) {
		this.categoryPension = categoryPension;
	}
}
