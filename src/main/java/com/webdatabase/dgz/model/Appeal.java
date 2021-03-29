package com.webdatabase.dgz.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appeals")
public class Appeal extends AuditModel{

	@Id
    @GeneratedValue(generator = "appeal_generator")
    @SequenceGenerator(
            name = "appeal_generator",
            sequenceName = "appeal_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
	
	private Long id;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Supplier supplier;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procuring_entity_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Procuring_entity procuring_entity;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Procuring_entity getProcuring_entity() {
		return procuring_entity;
	}

	public void setProcuring_entity(Procuring_entity procuring_entity) {
		this.procuring_entity = procuring_entity;
	}
	
	
}
