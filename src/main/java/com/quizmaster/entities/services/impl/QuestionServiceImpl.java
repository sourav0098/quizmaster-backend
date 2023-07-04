package com.quizmaster.entities.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.quizmaster.dtos.CreateQuestionDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuestionDto;
import com.quizmaster.dtos.QuestionsWithoutAnswerDto;
import com.quizmaster.entities.Question;
import com.quizmaster.entities.Quiz;
import com.quizmaster.entities.services.QuestionService;
import com.quizmaster.exceptions.ResourceNotFoundException;
import com.quizmaster.helper.Helper;
import com.quizmaster.repositories.QuestionRepository;
import com.quizmaster.repositories.QuizRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public QuestionDto getQuestionById(String questionId) {
		Question quiz = this.questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found!"));
		return modelMapper.map(quiz, QuestionDto.class);

	}

	@Override
	public QuestionDto addQuestion(CreateQuestionDto createQuestionDto) {
		String questionId = UUID.randomUUID().toString();

		Quiz quiz = this.quizRepository.findById(createQuestionDto.getQuizId())
				.orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

		Question question = Question.builder().questionId(questionId).question(createQuestionDto.getQuestion())
				.option1(createQuestionDto.getOption1()).option2(createQuestionDto.getOption2())
				.option3(createQuestionDto.getOption3()).option4(createQuestionDto.getOption4())
				.answer(createQuestionDto.getAnswer()).quiz(quiz).build();

		Question savedQuestion = this.questionRepository.save(question);
		return modelMapper.map(savedQuestion, QuestionDto.class);
	}

	@Override
	public QuestionDto updateQuestion(QuestionDto questionDto, String questionId) {
		Question question = this.questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found!"));

		question.setQuestion(questionDto.getQuestion());
		question.setOption1(questionDto.getOption1());
		question.setOption2(questionDto.getOption2());
		question.setOption3(questionDto.getOption3());
		question.setOption4(questionDto.getOption4());
		question.setAnswer(questionDto.getAnswer());

		Question updatedQuestion = this.questionRepository.save(question);
		return modelMapper.map(updatedQuestion, QuestionDto.class);
	}

	@Override
	public PageableResponse<QuestionDto> getQuestionByQuiz(String quizId, int numberOfQuestions, int pageNumber,
			int pageSize) {

		// Check if there are enough questions in the database
		long totalQuestions = this.questionRepository.countByQuizQuizId(quizId);
		if (totalQuestions == 0) {
			throw new ResourceNotFoundException("No questions found for the quiz");
		}

		// Adjust the page size if the number of available questions is less than the
		// requested page size
		pageSize = Math.min(pageSize, (int) totalQuestions);

		// Calculate the maximum number of pages needed to retrieve the total
		// numberOfQuestions
		int maxPages = (int) Math.ceil((double) totalQuestions / pageSize);

		// Adjust the pageNumber to ensure it doesn't exceed the maximum number of pages
		// as the page number starts from 0
		int adjustedPageNumber = Math.min(pageNumber, maxPages - 1);

		Pageable pageable = PageRequest.of(adjustedPageNumber, pageSize);

		// Retrieve all questions for the quiz
		List<Question> allQuestions = this.questionRepository.findByQuizQuizId(quizId);

		// Randomly shuffle the questions
		Collections.shuffle(allQuestions);

		// Get the subset of questions for the current page
		int startIndex = adjustedPageNumber * pageSize;
		int endIndex = Math.min(startIndex + pageSize, allQuestions.size());
		List<Question> pageQuestions = allQuestions.subList(startIndex, endIndex);

		// Convert the list of questions to a pageable format
		Page<Question> page = new PageImpl<>(pageQuestions, pageable, totalQuestions);

		PageableResponse<QuestionDto> response = Helper.getPageableResponse(page, QuestionDto.class);
		return response;
	}


	@Override
	public PageableResponse<QuestionsWithoutAnswerDto> getQuestionByQuizForUser(String quizId, int numberOfQuestions,
	        int pageNumber, int pageSize) {

	    // Check if there are enough questions in the database
	    long totalQuestions = this.questionRepository.countByQuizQuizId(quizId);
	    if (totalQuestions == 0) {
	        throw new ResourceNotFoundException("No questions found for the quiz");
	    }

	    // Adjust the page size if the number of available questions is less than the
	    // requested page size
	    pageSize = Math.min(pageSize, (int) totalQuestions);

	    // Calculate the maximum number of pages needed to retrieve the total
	    // numberOfQuestions
	    int maxPages = (int) Math.ceil((double) totalQuestions / pageSize);

	    // Adjust the pageNumber to ensure it doesn't exceed the maximum number of pages
	    // as the page number starts from 0
	    int adjustedPageNumber = Math.min(pageNumber, maxPages - 1);

	    Pageable pageable = PageRequest.of(adjustedPageNumber, pageSize);

	    // Retrieve all questions for the quiz
	    List<Question> allQuestions = this.questionRepository.findByQuizQuizId(quizId);

	    // Randomly shuffle the questions
	    Collections.shuffle(allQuestions);

	    // Get the subset of questions for the current page
	    int startIndex = adjustedPageNumber * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, allQuestions.size());
	    List<Question> pageQuestions = allQuestions.subList(startIndex, endIndex);

	    // Check if the number of questions exceeds the maximum allowed
	    if (pageQuestions.size() > numberOfQuestions) {
	        pageQuestions = pageQuestions.subList(0, numberOfQuestions);
	    }

	    // Convert the list of questions to a pageable format
	    Page<Question> page = new PageImpl<>(pageQuestions, pageable, totalQuestions);

	    PageableResponse<QuestionsWithoutAnswerDto> response = Helper.getPageableResponse(page,
	            QuestionsWithoutAnswerDto.class);
	    return response;
	}


	@Override
	public void deleteQuestion(String questionId) {
		this.questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found"));
		this.questionRepository.deleteById(questionId);
	}

}
