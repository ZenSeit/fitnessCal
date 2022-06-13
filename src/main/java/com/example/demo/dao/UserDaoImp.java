package com.example.demo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	// @Transactional
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
				eManager.merge(new User());
				return "User successfully registered";
			}
			return "User nickname already exists";

		} catch (IllegalArgumentException e) {
			e.printStackTrace();

			System.out.println("You are trying to send an entity that is not an user " + e.getMessage());
			return "In this moment we can't process your requirement";

		} catch (Exception e) {
			e.getMessage();
			return "La capturamos";
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
			if (uDB != null && uDB.getDeleted_at() == null) {
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
				if (us.getPassword() != null)
					uDB.setPassword(us.getPassword());
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
				return userReturn;
			}

			return null;

		} catch (IllegalArgumentException e) {
			e.getMessage();
			return null;
		}
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

}
