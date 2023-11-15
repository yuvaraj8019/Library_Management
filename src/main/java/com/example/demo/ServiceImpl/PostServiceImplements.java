package com.example.demo.ServiceImpl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Payloads.PostDto;
import com.example.demo.Payloads.PostResponse;
import com.example.demo.Repositories.CategoryRepo;
import com.example.demo.Repositories.PostRepo;
import com.example.demo.Repositories.UserRepo;
import com.example.demo.Services.PostService;
import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
@Service
public class PostServiceImplements implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		//User user=this.userRepo.findById(userID).orElseThrow(()->new ResourceNotFoundException("User", "user id", userID));
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		//Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoruy id", categoryId));
		Category cat=this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("category", "category Id", categoryId));
		
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		Post newPost=this.postRepo.save(post);
		
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
		
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize) {
		// TODO Auto-generated method stub

		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> allPosts=pagePost.getContent();
		
		List<PostDto> postDtos=allPosts.stream().
				map((allPost)-> this.modelMapper.map(allPost, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		List<Post> posts=this.postRepo.findAllByUser(user);
		List<PostDto> postDtos=posts.stream().
				map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts= this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=posts.stream().
				map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	//sorting the posts
	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}


	

	

}
