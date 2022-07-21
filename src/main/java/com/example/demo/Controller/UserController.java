package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDaoImp;
import com.example.demo.model.RelationUF;
import com.example.demo.model.User;
import com.example.demo.utils.AuthenticatorJWT;

@CrossOrigin(value = "*")
@RestController()
@RequestMapping(value = "api")
public class UserController {

	@Autowired
	private UserDaoImp UDao;

	@Autowired
	private AuthenticatorJWT jwta;

	/*
	 * @Autowired private UserRepository Urep;
	 */

	/**
	 * 
	 * @return List of users that are available in DB
	 */
	@RequestMapping(value = "getUsers")
	public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {
		if (token == null)
			return null;
		if (!validarToken(token))
			return null;
		return UDao.getUsers();

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

		return UDao.getUserId(id);

	}

	@RequestMapping(value = "addFooduser/{id}")
	public String addFU(@PathVariable Long id, @RequestParam Long idFood, @RequestParam double qFood) {
		return UDao.addFood(id, idFood, qFood);
	}

	@DeleteMapping(value = "deleteFooduser/{idus}")
	public String deleteFU(@PathVariable Long idus, @RequestParam Long idRel) {
		return UDao.deleteFoodFromUser(idus, idRel);
	}

	@RequestMapping(value = "updateFooduser/{idus}")
	public String updateFoodUser(@PathVariable Long idus, @RequestParam Long idRel, @RequestParam double nQuantity) {
		return UDao.updateFoodToUser(idus, idRel, nQuantity);
	}

	@RequestMapping(value = "getFoodforuser/{idus}")
	public List<RelationUF> getFoodUser(@PathVariable Long idus, @RequestParam(defaultValue = "0") Integer day) {
		return UDao.getFoodByUser(idus, day);
	}

	@PostMapping(value = "addManyFoodtouser/{id}")
	public List<String> addManyFoodToUser(@PathVariable Long id, @RequestBody List<Map<String, Long>> todo) {
		return todo.stream().map(t -> UDao.addFood(id, t.get("idFood"), t.get("nQuan"))).collect(Collectors.toList());

	}

	// Helpers

	private boolean validarToken(String token) {
		String usuarioId = jwta.getKey(token);
		return usuarioId != null;
	}

}
