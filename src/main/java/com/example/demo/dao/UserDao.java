package com.example.demo.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.RelationUF;
import com.example.demo.model.User;

public interface UserDao {

	public List<User> getUsers();

	public String registerUser(User us);

	public String deleteUser(Long id);

	public String updateUser(Long id, User us);

	public User getUserNickname(String nickname);

	public List<User> getUserId(Long id);

	String addFood(Long idUs, Long idFood, double qFood, int day, int formQ);

	String deleteFoodFromUser(Long idUs, Long idRel);

	String updateFoodToUser(Long idUs, Long idRel, double nQuantity, int formQ);

	RelationUF getOFoodToUser(Long idUs, Long idRel);

	List<RelationUF> getFoodByUser(Long idUs, int day);

	void saveImage(Long id, MultipartFile imageFile) throws Exception;

	void deleteImage(Long id) throws Exception;

}
