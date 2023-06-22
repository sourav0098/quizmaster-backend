package com.quizmaster.entities.services;

import com.quizmaster.dtos.CategoryDto;
import com.quizmaster.dtos.PageableResponse;

public interface CategoryService {

	public CategoryDto getCategoryById(String categoryId);

	public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

	public CategoryDto addCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);

	public void deleteCategory(String categoryId);
}
