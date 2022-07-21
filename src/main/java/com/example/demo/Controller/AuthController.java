package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDaoImp;
import com.example.demo.model.User;
import com.example.demo.utils.AuthenticatorJWT;

@CrossOrigin(value = "*", exposedHeaders = "Authorization")
@RestController
public class AuthController {

	@Autowired
	private UserDaoImp UDao;

	@Autowired
	private AuthenticatorJWT jwta;

	@PostMapping(value = "api/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> payload) {

		User uAu = UDao.getUserNickname(payload.get("nickname"));

		if (uAu != null) {
			if (UDao.verifyCredentials(payload.get("password"), uAu.getPassword())) {
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(HttpHeaders.AUTHORIZATION,
						jwta.create(String.valueOf(uAu.getId()), uAu.getEmail()));

				return ResponseEntity.ok().headers(responseHeaders)
						.body(jwta.create(String.valueOf(uAu.getId()), uAu.getEmail()));
			}
		}

		return new ResponseEntity<String>("Access denied", HttpStatus.OK);
	}

}
