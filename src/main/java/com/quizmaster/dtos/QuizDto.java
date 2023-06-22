package com.quizmaster.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;
import com.quizmaster.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
	private String quizId;

	@NotBlank(message = "Please provide a valid title")
	@Length(min = 2, message = "Please add minimum of 2 characters in title")
	private String title;

	@NotBlank(message = "Please provide a valid description")
	private String description;

	@PositiveOrZero(message = "Max score must be a positive number or zero")
	private int maxScore;

	@PositiveOrZero(message = "Number of questions must be a positive number or zero")
	private int numberOfQuestions;

	@DecimalMin(value = "0.0", message = "Quiz duration must be a positive number or zero")
	private double quizDuration;
	private boolean active;

	private Category category = new Category();
}
