package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.FoodDaoImp;
import com.example.demo.model.Food;

@CrossOrigin(value = "*")
@RestController()
@RequestMapping(value = "api")
public class FoodController {

	@Autowired
	private FoodDaoImp foodDao;

	@RequestMapping(value = "getFood")
	public List<Food> getAllFood() {

		return foodDao.listFood();

	}

	@PostMapping(value = "addFood")
	public String addFood(@RequestBody Food fd) {

		return foodDao.addFood(fd);
	}

	@GetMapping(value = "getFood/{id}")
	public Optional<Food> getFood(@PathVariable Long id) {
		return foodDao.oneFood(id);
	}

	@DeleteMapping(value = "deleteFood/{id}")
	public String deleteFood(@PathVariable Long id) {

		return foodDao.deleteFood(id);
	}

	@PatchMapping(value = "updateFood/{id}")
	public String updateFood(@PathVariable Long id, @RequestBody Food fd) {
		return foodDao.updateFood(id, fd);
	}

}
