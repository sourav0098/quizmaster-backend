package com.quizmaster.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quizmaster.dtos.CreateQuestionDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuestionDto;
import com.quizmaster.dtos.QuestionResponseDto;
import com.quizmaster.dtos.QuestionsWithoutAnswerDto;
import com.quizmaster.dtos.QuizDto;
import com.quizmaster.entities.services.QuestionService;
import com.quizmaster.entities.services.QuizService;
import com.quizmaster.payload.ApiResponse;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuizService quizService;

	@GetMapping("/{questionId}")
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable String questionId) {
		QuestionDto questionDto = this.questionService.getQuestionById(questionId);
		return new ResponseEntity<QuestionDto>(questionDto, HttpStatus.OK);
	}

	@GetMapping("quiz/{quizId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageableResponse<QuestionDto>> getAllQuestionByQuiz(@PathVariable String quizId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

		QuizDto quizDto = this.quizService.getQuizById(quizId);

		PageableResponse<QuestionDto> response = this.questionService.getQuestionByQuiz(quizId,
				quizDto.getNumberOfQuestions(), pageNumber, pageSize);
		return new ResponseEntity<PageableResponse<QuestionDto>>(response, HttpStatus.OK);
	}

	@GetMapping("quiz/{quizId}/user-attempt")
	@PreAuthorize("hasRole('NORMAL') or hasRole('ADMIN')")
	public ResponseEntity<PageableResponse<QuestionsWithoutAnswerDto>> getQuestionsByQuizForUser(
			@PathVariable String quizId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

		QuizDto quizDto = this.quizService.getQuizById(quizId);

		PageableResponse<QuestionsWithoutAnswerDto> response = this.questionService.getQuestionByQuizForUser(quizId,
				quizDto.getNumberOfQuestions(), pageNumber, pageSize);
		return new ResponseEntity<PageableResponse<QuestionsWithoutAnswerDto>>(response, HttpStatus.OK);
	}

	@PostMapping("eval-quiz")
	@PreAuthorize("hasRole('NORMAL') or hasRole('ADMIN')")
	public ResponseEntity<?> evaluateQuiz(@RequestBody List<QuestionResponseDto> questions) {

		Integer correctAnswers = 0;
		Integer questionsAttempted = 0;

		for (QuestionResponseDto q : questions) {
			QuestionDto questionDto = this.questionService.getQuestionById(q.getQuestionId());
			if (questionDto.getAnswer().trim().equals(q.getGivenAnswer().trim())) {
				// correct answer
				correctAnswers++;
			}

			if (q.getGivenAnswer() != null && !q.getGivenAnswer().trim().equals("")) {
				questionsAttempted++;
			}
		}
		Map<String, Object> response = Map.of("correctAnswers", correctAnswers, "questionsAttempted",
				questionsAttempted);
		return ResponseEntity.ok(response);

	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuestionDto> addQuestion(@Valid @RequestBody CreateQuestionDto createQuestionDto) {
		QuestionDto savedQuestion = this.questionService.addQuestion(createQuestionDto);
		return new ResponseEntity<QuestionDto>(savedQuestion, HttpStatus.CREATED);
	}

	@PutMapping("/{questionId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuestionDto> updateQuestion(@Valid @RequestBody QuestionDto questionDto,
			@PathVariable String questionId) {
		QuestionDto updatedQuestion = this.questionService.updateQuestion(questionDto, questionId);
		return new ResponseEntity<QuestionDto>(updatedQuestion, HttpStatus.OK);
	}

	@DeleteMapping("/{questionId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable String questionId) {
		this.questionService.deleteQuestion(questionId);
		ApiResponse response = ApiResponse.builder().message("Question deleted successfully")
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
