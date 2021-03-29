package com.webdatabase.dgz.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "msec_details")
public class Msec_detail extends AuditModel {

	@Id
    @GeneratedValue(generator = "msec_detail_generator")
    @SequenceGenerator(
            name = "msec_detail_generator",
            sequenceName = "msec_detail_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String orgnizationName;
    
    @Nullable
    private Date examinationDate;
    
    private String examinationtype;
    
    private String disabilityGroup;
    
    @Nullable
    private Date fromDate;
    
    @Nullable
    private Date toDate;
    
    private String reExamination;
    
    @Nullable
    private int supplier_member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgnizationName() {
		return orgnizationName;
	}

	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}

	public Date getExaminationDate() {
		return examinationDate;
	}

	public void setExaminationDate(Date examinationDate) {
		this.examinationDate = examinationDate;
	}

	public String getExaminationtype() {
		return examinationtype;
	}

	public void setExaminationtype(String examinationtype) {
		this.examinationtype = examinationtype;
	}

	public String getDisabilityGroup() {
		return disabilityGroup;
	}

	public void setDisabilityGroup(String disabilityGroup) {
		this.disabilityGroup = disabilityGroup;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to) {
		this.toDate = to;
	}

	public String getReExamination() {
		return reExamination;
	}

	public void setReExamination(String reExamination) {
		this.reExamination = reExamination;
	}

	public int getSupplier_member() {
		return supplier_member;
	}

	public void setSupplier_member(int supplier_member) {
		this.supplier_member = supplier_member;
	}
}
