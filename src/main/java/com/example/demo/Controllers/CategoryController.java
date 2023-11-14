package com.example.demo.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Payloads.ApiResponse;
import com.example.demo.Payloads.CategoryDto;
import com.example.demo.Services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//create category
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto,HttpStatus.CREATED);
	}
	
	//update category
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		
		CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	//Delete Category
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted succesful",true),HttpStatus.OK);
	}
	
	//Get all categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return  ResponseEntity.ok(this.categoryService.getAllCategories());
	}
	
	//Get Category by id
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategoryByid(@PathVariable Integer catId){
		return ResponseEntity.ok(this.categoryService.getCategory(catId));
		
	}
	
}
