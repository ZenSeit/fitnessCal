package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDaoImp;
import com.example.demo.model.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@CrossOrigin(value = "*")
@RestController()
@RequestMapping(value = "api")
public class UserController {

	@Autowired
	private UserDaoImp UDao;

	/*
	 * @Autowired private UserRepository Urep;
	 */

	/**
	 * 
	 * @return List of user that are available in DB
	 */
	@RequestMapping(value = "getUsers")
	public List<User> getUsers() {
		return UDao.getUsers().stream().peek(this::setSpecialData).collect(Collectors.toList());

	}

	/**
	 * 
	 * @param user that is in the body, is necessary to send nickname,email and
	 *             password to create a user in DB
	 * @return response of transaction, 201 if was created, 200 if nickname exist
	 *         and conflict if data passed as user is not an entity
	 */
	@PostMapping(value = "registerUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendUser(@RequestBody User user) {

		// check that exist nickname,password and email to create an user
		if (user.getNickname() == null || user.getPassword() == null || user.getEmail() == null) {
			return new ResponseEntity<String>("You need to fill all fields", HttpStatus.I_AM_A_TEAPOT);
		}

		return new ResponseEntity<String>(UDao.registerUser(user), HttpStatus.I_AM_A_TEAPOT);
	}

	@DeleteMapping(value = "delUser/{id}")
	public String deleteUserById(@PathVariable Long id) {

		return UDao.deleteUser(id);

	}

	@PatchMapping(value = "updateUser/{id}")
	public String UpdateUserById(@PathVariable Long id, @RequestBody User us) {
		if (us.getPassword() != null) {
			Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
			String hash = argon2.hash(1, 1024, 1, us.getPassword());
			us.setPassword(hash);
		}
		return UDao.updateUser(id, us);

	}

	@RequestMapping(value = "lookforuser/{nick}")
	public User lookNickname(@PathVariable String nick) {
		return UDao.getUserNickname(nick);
	}

	@RequestMapping(value = "getUser/{id}")
	public List<User> getUser(@PathVariable Long id) {
		if (UDao.getUserId(id) == null) {
			return null;
		}
		return UDao.getUserId(id).stream().peek(this::setSpecialData).collect(Collectors.toList());
	}

	// Helpers

	private void setSpecialData(User us) {
		us.setBmrCalories(us.calculateBMR(us.getGender(), us.calculateAge()));
		us.setAge(us.calculateAge());
		us.setCaloriesMaintance(us.estimateCaloriesMaintance());
		us.setCaloriesGoal(us.estimateCaloriesGoal());
	}

}