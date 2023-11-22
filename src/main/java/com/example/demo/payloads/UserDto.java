package com.example.demo.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("unused")
@NoArgsConstructor
@Data
public class UserDto {
	
	public int id;
	@NotEmpty
	@Size(min=4,message = "user name should be minimun of 4 characters !!")
	public String name;
	@Email(message = "enter valid email address !!")
	public String email;
	@NotEmpty
	@Size(min = 3,max = 10,message = "PASSWORD must be min of 3 chars and max of 10 chars !!")
	public String password;
	@NotEmpty
	public String about;

}
