package com.quizmaster.dtos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.quizmaster.entities.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
	private String questionId;

	@NotBlank(message = "Please provide a valid question")
	private String question;
	private String image;

	@NotBlank(message = "Please provide a valid option 1")
	private String option1;

	@NotBlank(message = "Please provide a valid option 2")
	private String option2;

	@NotBlank(message = "Please provide a valid option 3")
	private String option3;

	@NotBlank(message = "Please provide a valid option 4")
	private String option4;

	@NotBlank(message = "Please provide a valid answer")
	private String answer;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	private Quiz quiz;
}
