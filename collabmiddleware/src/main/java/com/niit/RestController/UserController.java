package com.niit.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.UserDao;
import com.niit.model.User;

@RestController
@RequestMapping("api")
public class UserController {
	@Autowired
	UserDao userDao;

	@GetMapping(value = "/allUsers") 
	public ResponseEntity<List<User>> getallUser() {
		List<User> allUsers = userDao.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);

	}

	@PostMapping("/newUser")
	public ResponseEntity<User> newUser(@RequestBody User user) {
			userDao.addUser(user);
			return new ResponseEntity<User>(userDao.getUserByUserName(user.getUserEmailId()), HttpStatus.OK);
		
	}

	@PostMapping("user/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		boolean flag = userDao.editUser(user);
		if (flag) {
			return new ResponseEntity<User>(userDao.getUserByUserName(user.getUserEmailId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "user/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable("name") String userName, User user) {
		try {
			user = userDao.getUserbyUserId(Integer.parseInt(userName));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			user = userDao.getUserByUserName(userName);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "deleteUser/{id}")
	public ResponseEntity deleteUser(@PathVariable("id") int userId) {
		userDao.deleteUser(userId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PostMapping("login")
	public ResponseEntity<User> validateUser(@RequestBody User user) {
		return new ResponseEntity<User>(userDao.validUser(user.getUserName(), user.getUserPassword()), HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload")
	public void handleClientErrors(Exception ex) {

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
	public void handleServerErrors(Exception ex) {

	}
}