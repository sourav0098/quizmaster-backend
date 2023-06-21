package com.quizmaster.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
	private String userId;

	@Size(max = 100, message = "First name cant be longer than 100 characters")
	@NotBlank(message = "Please provide a first name")
	private String fname;

	@Size(max = 100, message = "Last name cant be longer than 100 characters")
	@NotBlank(message = "Please provide a last name")
	private String lname;

	@Email(message = "Please provide a valid email")
	@NotBlank(message = "Please provide an email")
	private String email;

	@Size(min = 8, message = "Password must be at least 8 characters")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,15}$", message = "Password should contain atleast one uppercase, one lowercase, one number and one special character")
	private String password;

	private boolean enabled = true;
	private Date createdAt;
	private Date updatedAt;
}
