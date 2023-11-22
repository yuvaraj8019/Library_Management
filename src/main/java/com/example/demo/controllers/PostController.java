package com.example.demo.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Services.FileService;
import com.example.demo.Services.PostService;
import com.example.demo.config.AppConstants;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;

import lombok.val;
import net.bytebuddy.asm.Advice.This;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	//create Post
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	//Get Posts By User
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		List<PostDto> posts=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsDtos=this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
	}
	//Get All Posts
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value  ="sortBy",defaultValue = AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
			
			){
	
		
		 PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	//Get Post By Id
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto post=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		
	}
	
	//delete Post
	@DeleteMapping("post/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successful",true),HttpStatus.OK);
	}
	
	//Update post 
	@PostMapping("post/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	//Search Post
	@GetMapping("post/search/{keyward}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyward") String keyword){
		List<PostDto> searchposts = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchposts,HttpStatus.OK);
		
	}
	
	
	//upload post image
	@PostMapping("/post/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	{
		String fileName=this.fileService.uploadImage(path, image);
		PostDto postDto=this.postService.getPostById(postId);
		postDto.setImageName(fileName);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
		
	}
	
	//Method to serve files
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName")String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
		
	}
	
	
	
}
