package com.example.demo.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "users")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class User {

	// User information fields
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "profilephoto")
	private String profilePhoto;
	@Column(name = "nickname", nullable = false)
	private String nickname;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "email", nullable = false)
	private String email;

	// Physic fields of User
	@Column(name = "name")
	private String name;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "birthday")
	private LocalDate birthDay;
	// 1:Male 2:Female
	@Column(name = "gender")
	private int gender;
	@Column(name = "country")
	private String country;

	// Condition User
	@Column(name = "weight")
	private double weight;
	@Column(name = "height")
	private double height;
	// 0:maintenance 1:bulk 2:cut
	@Column(name = "fitnessgoal")
	private int fitnessGoal;
	// 0: no exercise , 1:1/2 time week, 2:2/3 times week, 3:3/5 times week, 4:6/7
	// times week, 5:professional athlete
	@Column(name = "activityday")
	private int activityDay;

	// Registers
	@CreationTimestamp
	@Column(name = "createdat")
	private LocalDate created_at;
	@Column(name = "deletedat")
	@JsonIgnore
	private LocalDate deleted_at;
	@UpdateTimestamp
	@Column(name = "updatedat")
	@JsonIgnore
	private LocalDate updated_at;

	/*
	 * @ManyToMany
	 * 
	 * @JoinTable(name = "relusersfood", joinColumns = @JoinColumn(name =
	 * "id_user"), inverseJoinColumns = @JoinColumn(name = "id_food")) private
	 * List<Food> myFood;
	 */

	@JsonBackReference
	@OneToMany(mappedBy = "us", fetch = FetchType.LAZY)
	private List<RelationUF> myFood;

	@Transient
	private double bmrCalories;
	@Transient
	private int age;
	@Transient
	private double caloriesMaintenance;
	@Transient
	private double caloriesGoal;

	// Contructors
	public User() {
	}

	public User(String profilePhoto, String nickname, String password, String email, String name, String lastname,
			LocalDate birthDay, int gender, String country, double weight, double height, int fitnessGoal,
			int activityDay, LocalDate created_at, LocalDate deleted_at, LocalDate updated_at) {
		super();
		this.profilePhoto = profilePhoto;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.name = name;
		this.lastname = lastname;
		this.birthDay = birthDay;
		this.gender = gender;
		this.country = country;
		this.weight = weight;
		this.height = height;
		this.fitnessGoal = fitnessGoal;
		this.activityDay = activityDay;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
		this.updated_at = updated_at;
	}

	// Methods

	public int calculateAge() {

		if ((this.birthDay != null)) {
			return Period.between(birthDay, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}

	public double calculateBMR(int gender, int years) {
		switch (gender) {
		case 1: { // Male

			return (88.362 + (13.397 * this.weight) + (4.799 * this.height) - (5.677 * years));

		}
		case 2: {// Female
			return 447.593 + (9.247 * this.weight) + (3.098 * this.height) - (4.330 * years);

		}
		default:
			return 0;

		}

	}

	public double estimateCaloriesMaintenance() {
		switch (this.activityDay) {
		case 0: {

			return this.bmrCalories * 1.2;
		}
		case 1: {

			return this.bmrCalories * 1.375;
		}
		case 2: {

			return this.bmrCalories * 1.55;
		}
		case 3: {

			return this.bmrCalories * 1.725;
		}
		case 4: {

			return this.bmrCalories * 1.9;
		}
		case 5: {

			return this.bmrCalories * 2.2;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.activityDay);
		}
	}

	public double estimateCaloriesGoal() {
		switch (this.fitnessGoal) {
		case 1: {

			return this.caloriesMaintenance * 1.1;
		}
		case 0: {

			return this.caloriesMaintenance;
		}
		case 2: {

			return this.caloriesMaintenance * 0.9;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.fitnessGoal);
		}
	}

}
