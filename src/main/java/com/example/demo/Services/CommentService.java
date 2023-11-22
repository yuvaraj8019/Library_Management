package com.example.demo.Services;

import com.example.demo.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postID);
	
	void deleteComment(Integer commentId);

}
