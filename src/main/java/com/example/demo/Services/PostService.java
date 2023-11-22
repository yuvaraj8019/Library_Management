package com.example.demo.Services;

import java.util.List;

import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer UserId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postid);
	
	//Get all Posts
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortrBy,String sortDir);
	
	//Get Post By Id
	PostDto getPostById(Integer postId);
	
	//Get all Post By user
	List<PostDto> getPostByUser(Integer userId);
	
	//Get all Post By category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//Search post
	List<PostDto> searchPost(String keyword);
	

}
