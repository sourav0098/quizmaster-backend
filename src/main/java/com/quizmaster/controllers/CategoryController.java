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

import com.quizmaster.dtos.CategoryDto;
import com.quizmaster.dtos.PageableResponse;
import com.quizmaster.entities.services.CategoryService;
import com.quizmaster.payload.ApiResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// get category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	// get all categories
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<CategoryDto> response = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy,
				sortDir);
		return new ResponseEntity<PageableResponse<CategoryDto>>(response, HttpStatus.OK);
	}

	// create category
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto savedCategory = this.categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(savedCategory, HttpStatus.CREATED);
	}

	// update category
	@PutMapping("/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable String categoryId) {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryId, categoryDto);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	// delete category by id
	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") String categoryId) {
		this.categoryService.deleteCategory(categoryId);
		ApiResponse response = ApiResponse.builder().message("Category deleted successfully")
				.status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
