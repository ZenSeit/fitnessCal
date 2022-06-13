package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "food")
@Getter
@Setter
@ToString
public class Food {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// facts
	@Column(name = "name")
	private String name;
	@Column(name = "proteinqgr")
	private double proteinQGr;
	@Column(name = "carbsqgr")
	private double carbsQGr;
	@Column(name = "fatqgr")
	private double fatQGr;
	@Column(name = "referencequantity")
	private double referenceQuantity;

	@Column(name = "description")
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "myFood")
	private List<User> users;

	// Tracks
	@CreationTimestamp
	@Column(name = "createdat")
	private LocalDate created_at;
	@Column(name = "deletedat")
	@JsonIgnore
	private LocalDate deletedat;
	@UpdateTimestamp
	@Column(name = "updatedat")
	@JsonIgnore
	private LocalDate updated_at;

}