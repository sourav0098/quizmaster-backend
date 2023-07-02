package com.quizmaster.entities.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quizmaster.dtos.CreateQuizDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuizDto;
import com.quizmaster.entities.Category;
import com.quizmaster.entities.Quiz;
import com.quizmaster.entities.services.QuizService;
import com.quizmaster.exceptions.ResourceNotFoundException;
import com.quizmaster.helper.Helper;
import com.quizmaster.repositories.CategoryRepository;
import com.quizmaster.repositories.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public QuizDto getQuizById(String quizId) {
		Quiz quiz = this.quizRepository.findById(quizId)
				.orElseThrow(() -> new ResourceNotFoundException("Quiz not found!"));
		return modelMapper.map(quiz, QuizDto.class);
	}

	@Override
	public PageableResponse<QuizDto> getAllQuiz(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> page = this.quizRepository.findAll(pageable);
		PageableResponse<QuizDto> response = Helper.getPageableResponse(page, QuizDto.class);
		return response;
	}

	@Override
	public PageableResponse<QuizDto> getActiveQuizByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> page = this.quizRepository.findByCategoryCategoryIdAndActiveTrue(categoryId, pageable);
		PageableResponse<QuizDto> response = Helper.getPageableResponse(page, QuizDto.class);
		return response;
	}
	
	@Override
	public PageableResponse<QuizDto> getQuizByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> page = this.quizRepository.findByCategoryCategoryId(categoryId, pageable);
		PageableResponse<QuizDto> response = Helper.getPageableResponse(page, QuizDto.class);
		return response;
	}

	@Override
	public QuizDto addQuiz(CreateQuizDto createQuizDto) {
		String quizId = UUID.randomUUID().toString();
		Category category = this.categoryRepository.findById(createQuizDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		Quiz quiz = Quiz.builder().quizId(quizId).title(createQuizDto.getTitle())
				.description(createQuizDto.getDescription()).maxScore(createQuizDto.getMaxScore())
				.numberOfQuestions(createQuizDto.getNumberOfQuestions()).quizDuration(createQuizDto.getQuizDuration())
				.active(createQuizDto.isActive()).category(category).build();

		Quiz savedQuiz = this.quizRepository.save(quiz);
		return modelMapper.map(savedQuiz, QuizDto.class);
	}

	@Override
	public QuizDto updateQuiz(QuizDto quizDto, String quizId) {
		Quiz quiz = this.quizRepository.findById(quizId)
				.orElseThrow(() -> new ResourceNotFoundException("Quiz not found!"));

		quiz.setTitle(quizDto.getTitle());
		quiz.setDescription(quizDto.getDescription());
		quiz.setMaxScore(quizDto.getMaxScore());
		quiz.setNumberOfQuestions(quizDto.getNumberOfQuestions());
		quiz.setQuizDuration(quizDto.getQuizDuration());
		quiz.setActive(quizDto.isActive());

		Quiz updatedQuiz = this.quizRepository.save(quiz);
		return modelMapper.map(updatedQuiz, QuizDto.class);
	}

	@Override
	public void deleteQuiz(String quizId) {
		this.quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz not found!"));
		this.quizRepository.deleteById(quizId);

	}

}
