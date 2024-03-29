package com.example.demo.dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Food;
import com.example.demo.model.RelationUF;
import com.example.demo.model.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Service
//@Transactional(rollbackFor = Exception.class)
public class UserDaoImp implements UserDao {

	@PersistenceContext
	EntityManager eManager;

	@Override
	public List<User> getUsers() {
		try {
			String query = "From User where deleted_at=null";
			return eManager.createQuery(query).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public String registerUser(User us) {
		try {
			if (this.getUserNickname(us.getNickname()) == null) {
				String pass = this.hashPassword(us.getPassword());
				us.setPassword(pass);
				eManager.merge(us);
				return "User successfully registered";
			}
			return "User nickname already exists";

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

			System.out.println("You are trying to send an entity that is not an user " + e.getMessage());
			return "In this moment we can't process your requirement";

		} catch (Exception e) {
			e.getMessage();
			return "In this moment we can't process your requirement";
		}
	}

	@Override
	@Transactional
	public String deleteUser(Long id) {
		try {
			User us = eManager.find(User.class, id);
			if (us != null && us.getDeleted_at() == null) {
				us.setDeleted_at(LocalDate.now());
				eManager.merge(us);
				return "User has been deleted";
			}
			return "User doens't exist";
		} catch (IllegalArgumentException e) {
			e.getMessage();
			return "Something was wrong, we are trying to solve soon";
		}
	}

	@Override
	@Transactional
	public String updateUser(Long id, User us) {
		try {
			User uDB = eManager.find(User.class, id);

			if (uDB != null && uDB.getDeleted_at() == null) { //
				// uDB.setNickname(us.getNickname());
				if (us.getName() != null)
					uDB.setName(us.getName());
				if (us.getLastname() != null)
					uDB.setLastname(us.getLastname());
				if (us.getCountry() != null)
					uDB.setCountry(us.getCountry());
				if (us.getEmail() != null)
					uDB.setEmail(us.getEmail());
				if (us.getProfilePhoto() != null)
					uDB.setProfilePhoto(us.getProfilePhoto());
				if (us.getPassword() != null) {
					Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
					String hash = argon2.hash(1, 1024, 1, us.getPassword());
					uDB.setPassword(hash);
				}
				// uDB.setPassword(us.getPassword());
				if (us.getFitnessGoal() != 0)
					uDB.setFitnessGoal(us.getFitnessGoal());
				if (us.getActivityDay() != 0)
					uDB.setActivityDay(us.getActivityDay());
				if (us.getHeight() != 0)
					uDB.setHeight(us.getHeight());
				if (us.getWeight() != 0)
					uDB.setWeight(us.getWeight());
				if (us.getGender() != 0)
					uDB.setGender(us.getGender());

				eManager.merge(uDB);
				return "Update succesfully completed";
			}

			return "User doesn't exist. We can't update this resource.";
		} catch (IllegalArgumentException e) {
			e.getMessage();
			return "Something was wrong, try again before";
		}
	}

	@Override
	public User getUserNickname(String nickname) {
		try {
			String query = "From User where nickname=:nickname and deleted_at=null";
			List<User> myUs = eManager.createQuery(query).setParameter("nickname", nickname).getResultList();

			if (myUs.isEmpty()) {
				return null;
			}
			return myUs.get(0);
		} catch (Exception e) {
			System.out.println("Something is wrong" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<User> getUserId(Long id) {
		try {
			List<User> userReturn = new ArrayList<>();
			User myUs = eManager.find(User.class, id);

			if (myUs != null && myUs.getDeleted_at() == null) {

				userReturn.add(myUs);

				userReturn.forEach(us -> {
					us.getMyFood().forEach(f -> {
						f.getFd().calculateCalories();
						f.calculateCaloriesPerQ(f.getFd().getReferenceQuantity(), f.getFd().getCalories());
					});
				});

				return userReturn.stream().peek(this::setSpecialData).collect(Collectors.toList());
			}

			return null;

		} catch (IllegalArgumentException e) {
			e.getMessage();
			return null;
		}
	}

	@Transactional
	@Override
	public String addFood(Long idUs, Long idFood, double qFood, int day, int formQ) {
		try {
			User myUs = eManager.find(User.class, idUs);
			Food myFood = eManager.find(Food.class, idFood);
			if (myUs != null && myFood != null && myUs.getDeleted_at() == null) {
				RelationUF uf = new RelationUF();
				uf.setFd(myFood);
				uf.setUs(myUs);
				uf.setQuantityuser(qFood);
				uf.setDay(day);
				uf.setFormQuantity(formQ);
				eManager.merge(uf);
				return "Food was added.";
			}
			return "There is nothing to add";
		} catch (IllegalArgumentException e) {
			return "In this moment we can't do this action";
		}
	}

	@Transactional
	@Override
	public String deleteFoodFromUser(Long idUs, Long idRel) {

		try {
			User myUs = eManager.find(User.class, idUs);
			if (myUs != null && myUs.getDeleted_at() == null) {
				RelationUF rel = eManager.find(RelationUF.class, idRel);
				if (rel != null) {
					eManager.remove(rel);
					return "Food was remove";
				}
			}

			return "We can't make this accion";
		} catch (IllegalArgumentException e) {
			return "Something was wrong, try again";
		}
	}

	@Transactional
	@Override
	public String updateFoodToUser(Long idUs, Long idRel, double nQuantity, int formQ) {
		try {
			User myUs = eManager.find(User.class, idUs);
			if (myUs != null && myUs.getDeleted_at() == null) {
				RelationUF rel = eManager.find(RelationUF.class, idRel);
				if (rel != null) {
					rel.setQuantityuser(nQuantity);
					rel.setFormQuantity(formQ);
					eManager.merge(rel);
					return "Quantity was update";
				}
			}
			return "This action can't do";
		} catch (IllegalArgumentException e) {
			return "Something was wrong. Try again";
		}
	}

	@Override
	public RelationUF getOFoodToUser(Long idUs, Long idRel) {
		try {
			User myUs = eManager.find(User.class, idUs);
			if (myUs != null && myUs.getDeleted_at() == null) {
				RelationUF rel = eManager.find(RelationUF.class, idRel);
				return rel;
			}
			return null;
		} catch (IllegalArgumentException e) {
			System.out.println("error: " + e.getStackTrace());
			return null;
		}
	}

	@Override
	@Transactional
	public void saveImage(Long id, MultipartFile imageFile) throws Exception {
		String frontFolder = "Images/" + id + "/";

		String folder = "/Users/mayasoft/Documents/ReactPractice/fitnetsscalfront/fitnesscal/src/" + frontFolder;
		Path dirpath = Paths.get(folder);
		if (!Files.exists(dirpath)) {
			Files.createDirectories(dirpath);
		}
		byte[] bytes = imageFile.getBytes();
		Path path = Paths.get(folder + imageFile.getOriginalFilename()); //
		Files.write(path, bytes);

		User us = eManager.find(User.class, id);
		us.setProfilePhoto(frontFolder + imageFile.getOriginalFilename());
		eManager.merge(us);
	}

	@Override
	@Transactional
	public void deleteImage(Long id) throws Exception {
		User us = eManager.find(User.class, id);
		if (us.getProfilePhoto() != null) {
			String mainRoute = "/Users/mayasoft/Documents/ReactPractice/fitnetsscalfront/fitnesscal/src/";
			String SpRoute = mainRoute + us.getProfilePhoto();
			Path dirpath = Paths.get(SpRoute);
			if (Files.deleteIfExists(dirpath) == true) {
				us.setProfilePhoto(null);
				eManager.merge(us);
			}
		}

	}

	@Override
	public List<RelationUF> getFoodByUser(Long idUs, int day) {

		/*
		 * String query = "From RelationUF where id_user=:idUs"; return
		 * eManager.createQuery(query).setParameter("idUs", idUs).getResultList();
		 */

		User us = eManager.find(User.class, idUs);

		if (us != null) {

			/*
			 * us.getMyFood().forEach(f -> { f.getFd().calculateCalories();
			 * f.calculateCaloriesPerQ(f.getFd().getReferenceQuantity(),
			 * f.getFd().getCalories()); });
			 * 
			 * return us.getMyFood();
			 */

			// Code to make a filter a select food for instance per day

			List<RelationUF> filter = us.getMyFood().stream().filter(f -> f.getDay() == day).toList();

			filter.forEach(f -> {
				f.getFd().calculateCalories();
				f.calculateCaloriesPerQ(f.getFd().getReferenceQuantity(), f.getFd().getCalories());
			});

			return filter;

		}
		return null;

	}

	// Helpers

	public String hashPassword(String pass) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		return argon2.hash(1, 1024, 1, pass);
	}

	public boolean verifyCredentials(String pass, String usPass) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		if (argon2.verify(usPass, pass)) {
			return true;
		}
		return false;
	}

	private void setSpecialData(User us) {
		us.setBmrCalories(us.calculateBMR(us.getGender(), us.calculateAge()));
		us.setAge(us.calculateAge());
		us.setCaloriesMaintenance(us.estimateCaloriesMaintenance());
		us.setCaloriesGoal(us.estimateCaloriesGoal());
	}

}
