package com.example.demo.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repositories.UserRepo;
import com.example.demo.Services.UserService;
import com.example.demo.entities.User;
import com.example.demo.payloads.UserDto;
@Service
public class UserServiceImpl implements UserService{
	
	//to create a user,delete a user,update a user we need a repository
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user=this.dtoToUser(userDto);
		User saveUser=this.userRepo.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		/*user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail()); 
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());*/
		
		return user;
		
	}
	public UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
		
		/*userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());*/
		
		return userDto;
	}

}
