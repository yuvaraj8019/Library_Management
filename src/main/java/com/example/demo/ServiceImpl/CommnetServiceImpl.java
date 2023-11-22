package com.example.demo.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Repositories.CommentRepo;
import com.example.demo.Repositories.PostRepo;
import com.example.demo.Services.CommentService;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Post;
import com.example.demo.payloads.CommentDto;
@Service
public class CommnetServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired 
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setContent(commentDto.getContent());
		Comment savedComment = this.commentRepo.save(comment);
		
		
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment=this.commentRepo.findById(commentId).
				orElseThrow(()->new ResourceNotFoundException("Commenmt", "comment ID", commentId));
		this.commentRepo.delete(comment);
		
	}

}
