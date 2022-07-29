package com.example.demo.Controller;

import java.util.List;

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
import com.fasterxml.jackson.databind.node.ObjectNode;

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
		if (validarToken(token) != null)
			return UDao.getUsers();
		return null;

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
	public List<User> getUser(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {

		if (token == null)
			return null;
		if (Long.parseLong(validarToken(token)) == id)
			return UDao.getUserId(id);
		;
		return null;
	}

	@PostMapping(value = "addFooduser/{id}")
	public String addFU(@PathVariable Long id, @RequestBody ObjectNode json) {
		if (json.has("idFood") && json.has("qFood") && json.has("day") && json.has("formQ")) {
			return UDao.addFood(id, json.get("idFood").asLong(), json.get("qFood").asDouble(), json.get("day").asInt(),
					json.get("formQ").asInt());
		}
		return "Invalid requirement";

	}

	@DeleteMapping(value = "deleteFooduser/{idus}")
	public String deleteFU(@PathVariable Long idus, @RequestParam Long idRel) {
		return UDao.deleteFoodFromUser(idus, idRel);
	}

	@PostMapping(value = "updateFooduser/{idus}")
	public String updateFoodUser(@PathVariable Long idus, @RequestParam Long idRel, @RequestBody ObjectNode json) {
		if (json.has("nQuantity") && json.has("formQ")) {
			return UDao.updateFoodToUser(idus, idRel, json.get("nQuantity").asDouble(), json.get("formQ").asInt());
		}
		return "Invalid requirement";
	}

	@RequestMapping(value = "getOfooduser/{idus}")
	public RelationUF getOFoodUser(@PathVariable Long idus, @RequestParam Long idRel) {
		return UDao.getOFoodToUser(idus, idRel);
	}

	@RequestMapping(value = "getFoodforuser/{idus}")
	public List<RelationUF> getFoodUser(@PathVariable Long idus, @RequestParam(defaultValue = "0") Integer day) {
		return UDao.getFoodByUser(idus, day);
	}

	/*
	 * @PostMapping(value = "addManyFoodtouser/{id}") public List<String>
	 * addManyFoodToUser(@PathVariable Long id, @RequestBody List<Map<String, Long>>
	 * todo) { return todo.stream().map(t -> UDao.addFood(id, t.get("idFood"),
	 * t.get("nQuan"))).collect(Collectors.toList());
	 * 
	 * }
	 */

	// Helpers

	private String validarToken(String token) {
		return jwta.getKey(token);
	}

}
