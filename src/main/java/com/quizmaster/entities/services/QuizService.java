package com.quizmaster.entities.services;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuizDto;

public interface QuizService {

	public QuizDto getQuizById(String quizId);

	public PageableResponse<QuizDto> getAllQuiz(int pageNumber, int pageSize, String sortBy, String sortDir);

	public QuizDto addQuiz(QuizDto quizDto);

	public QuizDto updateQuiz(QuizDto quizDto, String quizId);

	public void deleteQuiz(String quizId);

}
