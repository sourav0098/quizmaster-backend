package com.quizmaster.entities.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.dtos.QuestionDto;
import com.quizmaster.entities.Question;
import com.quizmaster.entities.Quiz;
import com.quizmaster.entities.services.QuestionService;
import com.quizmaster.exceptions.ResourceNotFoundException;
import com.quizmaster.helper.Helper;
import com.quizmaster.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public QuestionDto getQuestionById(String questionId) {
		Question quiz = this.questionRepository.findById(questionId)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found!"));
		return modelMapper.map(quiz, QuestionDto.class);

	}

	@Override
	public QuestionDto addQuestion(QuestionDto questionDto) {
		String questionId = UUID.randomUUID().toString();
		questionDto.setQuestionId(questionId);

		Question question = modelMapper.map(questionDto, Question.class);
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
		question.setImage(questionDto.getImage());

		Question updatedQuestion = this.questionRepository.save(question);
		return modelMapper.map(updatedQuestion, QuestionDto.class);
	}

	@Override
	public PageableResponse<QuestionDto> getQuestionByQuiz(Quiz quiz, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Question> page = this.questionRepository.findByQuiz(quiz, pageable);
		PageableResponse<QuestionDto> response = Helper.getPageableResponse(page, QuestionDto.class);
		return response;
	}
}
