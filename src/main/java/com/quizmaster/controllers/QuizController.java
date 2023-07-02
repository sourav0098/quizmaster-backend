package com.quizmaster.controllers;

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

import com.quizmaster.dtos.CreateQuizDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuizDto;
import com.quizmaster.entities.services.QuizService;
import com.quizmaster.payload.ApiResponse;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	// get quiz by id
	@GetMapping("/{quizId}")
	public ResponseEntity<QuizDto> getQuizById(@PathVariable String quizId) {
		QuizDto quizDto = this.quizService.getQuizById(quizId);
		return new ResponseEntity<QuizDto>(quizDto, HttpStatus.OK);
	}

	// get all quiz
	@GetMapping
	public ResponseEntity<PageableResponse<QuizDto>> getAllQuiz(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<QuizDto> response = this.quizService.getAllQuiz(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<QuizDto>>(response, HttpStatus.OK);
	}

	// get quiz by category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<PageableResponse<QuizDto>> getQuizByCategory(@PathVariable String categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<QuizDto> response = this.quizService.getQuizByCategoryId(categoryId, pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<PageableResponse<QuizDto>>(response, HttpStatus.OK);
	}

	// get active quiz by category
	@GetMapping("/category/{categoryId}/active")
	public ResponseEntity<PageableResponse<QuizDto>> getActiveQuizByCategory(@PathVariable String categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<QuizDto> response = this.quizService.getActiveQuizByCategoryId(categoryId, pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<PageableResponse<QuizDto>>(response, HttpStatus.OK);
	}

	// add a new quiz
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody CreateQuizDto createQuizDto) {
		QuizDto savedQuiz = this.quizService.addQuiz(createQuizDto);
		return new ResponseEntity<QuizDto>(savedQuiz, HttpStatus.CREATED);
	}

	// update a quiz
	@PutMapping("/{quizId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuizDto> updateQuiz(@Valid @RequestBody QuizDto quizDto, @PathVariable String quizId) {
		QuizDto upatedQuiz = this.quizService.updateQuiz(quizDto, quizId);
		return new ResponseEntity<QuizDto>(upatedQuiz, HttpStatus.OK);
	}

	// delete quiz by id
	@DeleteMapping("/{quizId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable("quizId") String quizId) {
		this.quizService.deleteQuiz(quizId);
		ApiResponse response = ApiResponse.builder().message("Quiz deleted successfully").status(HttpStatus.OK.value())
				.timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}