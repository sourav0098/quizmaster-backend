package com.quizmaster.entities.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quizmaster.dtos.CreateUserDto;
import com.quizmaster.dtos.UserResponseDto;
import com.quizmaster.entities.Role;
import com.quizmaster.entities.User;
import com.quizmaster.entities.services.UserService;
import com.quizmaster.exceptions.ConstraintViolationException;
import com.quizmaster.exceptions.ResourceNotFoundException;
import com.quizmaster.repositories.RoleRepository;
import com.quizmaster.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Value("${role.normal.id}")
	private String normalRoleId;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponseDto getUserById(String userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("No user found!"));
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto getUserByEmail(String email) {
		User user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("No user found!"));
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto createUser(CreateUserDto userDto) {
		// Check if some other user is registered with that email
		User emailExists = this.userRepository.findUserByEmailIgnoreCase(userDto.getEmail());
		if (emailExists != null) {
			throw new ConstraintViolationException("Email is already registered");
		}

		// Generate UUID
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);

		// Encoding password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

		// Convert DTO to entity
		User user = modelMapper.map(userDto, User.class);

		// fetch role of normal user and set it to user;
		Role role = this.roleRepository.findById(normalRoleId).get();
		user.getRoles().add(role);

		User savedUser = this.userRepository.save(user);

		// Convert entity to DTO
		return modelMapper.map(savedUser, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(CreateUserDto userDto, String userId) {
		// we will not update email and image through this method
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		user.setFname(userDto.getFname());
		user.setLname(userDto.getLname());

		// update and encrypt password only when it is not null or it is different from
		// previous password

		// NEED TO ENCODE PASSWORD
		if (userDto.getPassword() != null) {
			if (!userDto.getPassword().equalsIgnoreCase(user.getPassword())) {
				user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			}
		}

		User savedUser = this.userRepository.save(user);

		return modelMapper.map(savedUser, UserResponseDto.class);
	}
}
