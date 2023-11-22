package com.example.demo.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
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
	
	private Set<CommentDto> comments=new HashSet<>();
	
	
}
