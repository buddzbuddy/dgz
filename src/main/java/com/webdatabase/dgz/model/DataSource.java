package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "datasources")
public class DataSource extends AuditModel {
	@Id
    @GeneratedValue(generator = "datasource_generator")
    @SequenceGenerator(
            name = "datasource_generator",
            sequenceName = "datasource_sequence",
            initialValue = 1000
    )
    private Long id;
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    
    public String getName()
    {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    @Column(columnDefinition = "text")
    private String description;
    
    public String getDescription()
    {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
}
