package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDaoImp;
import com.example.demo.model.User;
import com.example.demo.utils.AuthenticatorJWT;

@RestController
public class AuthController {

	@Autowired
	private UserDaoImp UDao;

	@Autowired
	private AuthenticatorJWT jwta;

	@PostMapping(value = "api/login")
	public String login(@RequestBody Map<String, String> payload) {

		User uAu = UDao.getUserNickname(payload.get("nickname"));

		if (uAu != null) {
			if (UDao.verifyCredentials(payload.get("password"), uAu.getPassword())) {
				return jwta.create(String.valueOf(uAu.getId()), uAu.getEmail());
			}
		}

		return "Access denied";
	}

}
