package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	public void testCreateUser(){
		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		assertEquals(newCustomer.getBody().getUsername(), createUserRequest.getUsername());
	}

	@Test
	public void testFindUserById(){
		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		assertEquals(newCustomer.getBody().getUsername(), createUserRequest.getUsername());
		ResponseEntity<User> user = userController.findById(newCustomer.getBody().getId());
		assertEquals(user.getBody().getUsername(), "test");
	}

	@Test
	public void testFindByUserName(){
		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		assertEquals(newCustomer.getBody().getUsername(), createUserRequest.getUsername());
		ResponseEntity<User> user = userController.findByUserName(newCustomer.getBody().getUsername());
		assertEquals(user.getBody().getUsername(), "test");
	}



	private static CreateUserRequest CreateUserRequest() {
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername("test");
		createUserRequest.setPassword("password123");
		createUserRequest.setConfirmPassword("password123");
		return createUserRequest;
	}



}
