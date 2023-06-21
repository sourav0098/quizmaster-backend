package com.quizmaster.entities.services;

import com.quizmaster.dtos.CreateUserDto;
import com.quizmaster.dtos.UpdateUserDto;
import com.quizmaster.dtos.UserResponseDto;

public interface UserService {
	// Get User By user Id
	UserResponseDto getUserById(String userId);

	// Get User By user email
	UserResponseDto getUserByEmail(String email);

	// Create a new user
	UserResponseDto createUser(CreateUserDto userDto);

	// Update a user
	UserResponseDto updateUser(UpdateUserDto userDto, String userId);
}
