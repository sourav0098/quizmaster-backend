package com.quizmaster.entities.services;

import com.quizmaster.dtos.CreateQuestionDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuestionDto;
import com.quizmaster.dtos.QuestionsWithoutAnswerDto;

public interface QuestionService {

	public QuestionDto getQuestionById(String questionId);

	public QuestionDto addQuestion(CreateQuestionDto questionDto);

	public QuestionDto updateQuestion(QuestionDto questionDto, String questionId);

	public void deleteQuestion(String questionId);

	public PageableResponse<QuestionDto> getQuestionByQuiz(String quizId, int numberOfQuestions, int pageNumber,
			int pageSize);
	
	public PageableResponse<QuestionsWithoutAnswerDto> getQuestionByQuizForUser(String quizId, int numberOfQuestions, int pageNumber,
			int pageSize);

}
