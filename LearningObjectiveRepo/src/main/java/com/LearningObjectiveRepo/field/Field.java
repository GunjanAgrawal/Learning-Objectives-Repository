package com.LearningObjectiveRepo.field;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.LearningObjectiveRepo.domain.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_id")
	private long fieldId;
	
	@Column(name = "field_name")
	private String fieldName;
	
	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="domain_id")
	private Domain domain;

	public long getFieldId() {
		return fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public @JsonIgnore Domain getDomain() {
		return domain;
	}

	public Field() {
		super();
	}

	public Field(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
}