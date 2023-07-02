package com.quizmaster.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizDto {
	private String quizId;

	@NotBlank(message = "Please provide a category")
	private String categoryId;

	@NotBlank(message = "Please provide a valid title")
	@Length(min = 2, message = "Please add minimum of 2 characters in title")
	private String title;

	@NotBlank(message = "Please provide a valid description")
	private String description;

	@Range(min=0,message="Max scrore should be more than 0")
	@Positive(message = "Max score must be a positive number")
	private int maxScore;

	@Range(min=0,message="Number of questions should be more than 0")
	@Positive(message = "Number of questions must be a positive number")
	private int numberOfQuestions;

	@DecimalMin(value = "0.0", message = "Quiz duration must be a positive number")
	@Range(min=0,message="Quiz duration should be more than 0")
	private double quizDuration;
	private boolean active;

}
