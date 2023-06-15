package com.quizmaster.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
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
	private boolean enabled;
	private Date createdAt;
	private Date updatedAt;
}
