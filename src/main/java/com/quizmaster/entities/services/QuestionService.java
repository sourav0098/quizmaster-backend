package com.quizmaster.entities.services;

import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuestionDto;
import com.quizmaster.entities.Quiz;

public interface QuestionService {

	public QuestionDto getQuestionById(String questionId);

	public QuestionDto addQuestion(QuestionDto questionDto);

	public QuestionDto updateQuestion(QuestionDto questionDto, String questionId);

	public PageableResponse<QuestionDto> getQuestionByQuiz(Quiz quiz, int pageNumber, int pageSize, String sortBy,
			String sortDir);

}
