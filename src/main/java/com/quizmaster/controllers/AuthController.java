package com.quizmaster.controllers;


import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quizmaster.dtos.JwtRequestDto;
import com.quizmaster.dtos.JwtResponseDto;
import com.quizmaster.dtos.UserResponseDto;
import com.quizmaster.entities.services.UserService;
import com.quizmaster.exceptions.UnauthorizedException;
import com.quizmaster.security.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {
	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtHelper jwtHelper;

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto request) {
		// call method to authenticate email and password
		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());

		// generate jwt token and refresh token
		String jwtToken = this.jwtHelper.generateToken(userDetails);
		String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);

		UserResponseDto userResponseDto = modelMapper.map(userDetails, UserResponseDto.class);

		JwtResponseDto response = JwtResponseDto.builder().accessToken(jwtToken).refreshToken(refreshToken)
				.user(userResponseDto).build();
		return new ResponseEntity<JwtResponseDto>(response, HttpStatus.OK);
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtResponseDto> refreshToken(HttpServletRequest request) {
		// Check if "Authorization" header is available
		String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String username = null;
		String refreshToken = null;

		// If the header is present, the code checks if token starts with the string
		// "Bearer". The code extracts the token from the header by removing the first
		// seven characters, which correspond to the "Bearer " prefix.
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			refreshToken = requestHeader.substring(7);
			try {
				// get username from token
				username = this.jwtHelper.getUsernameFromToken(refreshToken);
			} catch (IllegalArgumentException ex) {
				log.info("Illegal argument while fetching username");
				ex.printStackTrace();
			} catch (ExpiredJwtException ex) {
				log.info("JWT Token is expired");
				ex.printStackTrace();
			} catch (MalformedJwtException ex) {
				log.info("Invalid token! Some changes has done in token");
				ex.printStackTrace();
			}
		} else {
			log.info("Invalid token");
		}

		// Check if username is not null and there is no authentication object inside
		// security context
		if (username != null) {
			// fetch user details from username
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
			UserResponseDto userDto = modelMapper.map(userDetails, UserResponseDto.class);

			// Validate token
			Boolean validateToken = this.jwtHelper.validateToken(refreshToken, userDetails);
			if (validateToken) {
				var accessToken = this.jwtHelper.generateToken(userDetails);
				JwtResponseDto jwtResponse = JwtResponseDto.builder().accessToken(accessToken)
						.refreshToken(refreshToken).user(userDto).build();
				return new ResponseEntity<JwtResponseDto>(jwtResponse, HttpStatus.OK);
			} else {
				log.info("Validation failed");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	private void doAuthenticate(String email, String password) {
		// check if email and password are correct
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			this.manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			// throw this exception if invalid email or password
			throw new UnauthorizedException("Invalid email or password!");
		}
	}

}
