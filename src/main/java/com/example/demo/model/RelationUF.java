package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "relusersfood")
@Getter
@Setter
@ToString
public class RelationUF {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonManagedReference
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "id_user")
	private User us;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "id_food")
	private Food fd;

	private double quantityuser;

	// Day:0 Monday, 1 Tuesday,2 Wednesday,3 Thursday,4 Friday,5 Saturday,6 Sunday
	private int day;

	@Column(name = "formquantity")
	private int formQuantity;

	@Transient
	private double caloriesPerQ;

	public void calculateCaloriesPerQ(double qReference, double cReference) {
		this.caloriesPerQ = this.quantityuser * cReference / qReference;
	}

}
