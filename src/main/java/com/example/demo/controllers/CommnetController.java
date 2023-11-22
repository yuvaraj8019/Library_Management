package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.CommentService;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CommentDto;

@RestController
@RequestMapping("/api/")
public class CommnetController {
	@Autowired
	private CommentService commentService;
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createCommnet(@RequestBody CommentDto comment,@PathVariable Integer postId){
		CommentDto createComment=this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/delete/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable  Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successful",true),HttpStatus.OK);
	}
	

}
