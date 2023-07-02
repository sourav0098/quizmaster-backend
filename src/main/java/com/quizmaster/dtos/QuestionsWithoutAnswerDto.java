package com.quizmaster.dtos;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsWithoutAnswerDto {
	private String questionId;

	@NotBlank(message = "Please provide a valid question")
	private String question;

	@NotBlank(message = "Please provide a valid option 1")
	private String option1;

	@NotBlank(message = "Please provide a valid option 2")
	private String option2;

	@NotBlank(message = "Please provide a valid option 3")
	private String option3;

	@NotBlank(message = "Please provide a valid option 4")
	private String option4;
}
