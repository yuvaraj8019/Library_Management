package com.example.demo.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotEmpty
	@Size(min=4,message = "category title should be minimun of 4 characters !!")
	private String categoryTitle;
	@NotEmpty
	@Size(min=4,message = "category description should be minimun of 4 characters !!")
	private String categoryDescription;
	}
