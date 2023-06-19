package com.quizmaster.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizmaster.dtos.CreateUserDto;
import com.quizmaster.dtos.UserResponseDto;
import com.quizmaster.entities.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;

	//	Get a user by user id
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {
		// Return the user in a ResponseEntity with HttpStatus.OK
		return new ResponseEntity<UserResponseDto>(this.userService.getUserById(userId), HttpStatus.OK);
	}

	//	Get a user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
		return new ResponseEntity<UserResponseDto>(this.userService.getUserByEmail(email), HttpStatus.OK);
	}

	// Create new user
	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto userDto) {
		UserResponseDto user = this.userService.createUser(userDto);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	// Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userId") String userId,
			@Valid @RequestBody CreateUserDto userDto) {
		UserResponseDto user = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}