package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

	List<Food> findBydeletedatIsNull();

}
