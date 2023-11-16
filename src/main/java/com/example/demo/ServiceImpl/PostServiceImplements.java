package com.example.demo.ServiceImpl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	
	// creating post implementation
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		
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

	//find post by Id
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
		
	}
	//deleting post
	@Override
	public void deletePost(Integer postId) {
	
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepo.delete(post);
		
	}
	
	//pagination,sorting with direction  implemented for get all posts

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")){
			sort=Sort.by(sortBy).ascending();
		}
		else if(sortDir.equalsIgnoreCase("desc")){
			sort=Sort.by(sortBy).descending();
			
		}
		

		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
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
	// get post by id
	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}
	// get post by user
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
	
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		List<Post> posts=this.postRepo.findAllByUser(user);
		List<PostDto> postDtos=posts.stream().
				map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	// get post by category
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
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

		return null;
	}


	

	

}
