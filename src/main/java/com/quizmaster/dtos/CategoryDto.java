package com.quizmaster.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private String categoryId;
	
	@NotBlank(message = "Please provide a valid title")
	@Length(min = 2, message = "Please add minimum of 2 characters in title")
	private String title;
	
	@NotBlank(message = "Please provide a valid description")
	private String description;

	private Date createdAt;
	private Date updatedAt;
}
