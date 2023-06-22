package com.quizmaster.entities.services.impl;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.quizmaster.dtos.CategoryDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.entities.Category;
import com.quizmaster.entities.services.CategoryService;
import com.quizmaster.exceptions.ResourceNotFoundException;
import com.quizmaster.helper.Helper;
import com.quizmaster.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto getCategoryById(String categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> page = this.categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);
		return response;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		// Generate UUID
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);

		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {
		// Get category by given category id
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

		// update category object details to categoryDto
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());

		Category updatedCategory = this.categoryRepository.save(category);
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		// Get category by given category id
		this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

		this.categoryRepository.deleteById(categoryId);
	}

}
