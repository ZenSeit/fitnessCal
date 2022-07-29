package com.example.demo.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.Food;

@Repository
@Service
public class FoodDaoImp implements FoodDao {

	@Autowired
	private FoodRepository dataFood;

	@Override
	public String addFood(Food fd) {

		if (fd.getName() == null) {
			return "You need to text a name if you want to add new food";
		}

		try {
			dataFood.save(fd);
			return "Food add to our data base.";
		} catch (IllegalArgumentException e) {
			e.getMessage();
			return "In this moment we can't add this food";
		}

	}

	@Override
	public String deleteFood(Long id) {

		try {
			Food fd = dataFood.findById(id).get();
			if (fd != null && fd.getDeletedat() == null) {
				// fd.setDeletedat(LocalDate.now());
				dataFood.delete(fd);

				return "Food deleted";
			}

			return "We can't find this register";
		} catch (IllegalArgumentException e) {
			e.getMessage();
			return "Something was wrong!. I trying to solve this trouble.";
		} catch (NoSuchElementException e) {
			return "This register doesn't exist";
		}
	}

	@Override
	public String updateFood(Long id, Food fdU) {
		try {
			System.out.println(fdU);

			Food fd = dataFood.findById(id).get();
			if (fd != null && fd.getDeletedat() == null) {
				if (fdU.getProteinQGr() != 0) {
					fd.setProteinQGr(fdU.getProteinQGr());
				}
				if (fdU.getCarbsQGr() != 0) {
					fd.setCarbsQGr(fdU.getCarbsQGr());
				}
				if (fdU.getFatQGr() != 0) {
					fd.setFatQGr(fdU.getFatQGr());
				}
				if (fdU.getReferenceQuantity() != 0) {
					fd.setReferenceQuantity(fdU.getReferenceQuantity());
				}
				if (fdU.getDescription() != null) {
					fd.setDescription(fdU.getDescription());
				}
				dataFood.save(fd);
				return "Fodd succesfully update";
			}

			return "Food don't exist";
		} catch (IllegalArgumentException e) {
			e.getMessage();
			return "Something was wrong!. I trying to solve this trouble.";
		} catch (NoSuchElementException e) {
			return "This register doesn't exist";
		}
	}

	@Override
	public List<Food> listFood() {
		return dataFood.findAll().stream().peek(f -> f.calculateCalories()).collect(Collectors.toList());
	}

	@Override
	public Optional<Food> oneFood(Long id) {
		if (id != null) {
			return dataFood.findById(id);
		}
		return null;
	}

}
