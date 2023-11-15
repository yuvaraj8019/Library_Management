package com.example.demo.Services;

import java.util.List;

import com.example.demo.Payloads.PostDto;
import com.example.demo.entities.Post;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer UserId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postid);
	
	//Get all Posts
	List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
	
	//Get Post By Id
	PostDto getPostById(Integer postId);
	
	//Get all Post By user
	List<PostDto> getPostByUser(Integer userId);
	
	//Get all Post By category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//Search post
	List<Post> searchPost(String keyword);
	

}
