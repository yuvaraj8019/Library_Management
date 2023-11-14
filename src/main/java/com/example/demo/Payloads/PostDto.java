package com.example.demo.Payloads;

import java.util.Date;

import com.example.demo.entities.Category;
import com.example.demo.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private String postId;

	
	private String title;
	
	private String content;
	
	private Date addedDate;
	

	private String imageName;
	
	private UserDto user;
	
	private CategoryDto category;
	
	
}
