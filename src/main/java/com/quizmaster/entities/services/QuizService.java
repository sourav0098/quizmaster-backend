package com.quizmaster.entities.services;

import com.quizmaster.dtos.CreateQuizDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuizDto;

public interface QuizService {

	public QuizDto getQuizById(String quizId);

	public PageableResponse<QuizDto> getAllQuiz(int pageNumber, int pageSize, String sortBy, String sortDir);

	public PageableResponse<QuizDto> getQuizByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir);

	public PageableResponse<QuizDto> getActiveQuizByCategoryId(String categoryId, int pageNumber, int pageSize,
			String sortBy, String sortDir);

	public QuizDto addQuiz(CreateQuizDto createQuizDto);

	public QuizDto updateQuiz(QuizDto quizDto, String quizId);

	public void deleteQuiz(String quizId);

}
