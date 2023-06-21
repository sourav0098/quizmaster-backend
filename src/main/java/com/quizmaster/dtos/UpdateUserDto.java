package com.quizmaster.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
	@Size(max = 100, message = "First name cant be longer than 100 characters")
	@NotBlank(message = "Please provide a first name")
	private String fname;

	@Size(max = 100, message = "Last name cant be longer than 100 characters")
	@NotBlank(message = "Please provide a last name")
	private String lname;

	private boolean enabled = true;
}