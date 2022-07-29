package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Food;

public interface FoodDao {

	List<Food> listFood();

	String addFood(Food fd);

	String deleteFood(Long id);

	String updateFood(Long id, Food fdU);

	Optional<Food> oneFood(Long id);

}
