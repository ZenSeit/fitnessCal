package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.JoinFormula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "relusersfood")
@Getter
@Setter
public class RelationUF {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "id_user")
	private User us;

	@ManyToOne
	@JoinColumn(name = "id_food")
	@JoinFormula(value = "SELECT * FROM food where deletedat=null")
	private Food fd;

	private double quantityuser;

	@Transient
	private double caloriesPerQ;

	public void calculateCaloriesPerQ(double qReference, double cReference) {
		this.caloriesPerQ = this.quantityuser * cReference / qReference;
	}

}
