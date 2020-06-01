package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private ItemController itemController;

	@Autowired
	private CartController cartController;

	@Autowired
	private OrderController orderController;

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

	@Test
	public void testListItems(){
		ResponseEntity<List<Item>> items = itemController.getItems();

		assertEquals(items.getBody().size(), 2);
	}

	@Test
	public void testGetItemById(){
		ResponseEntity<Item> item = itemController.getItemById(1L);
		assertEquals(item.getBody().getName(), "Round Widget");
	}

	@Test
	public void testGetItemByName(){
		ResponseEntity<List<Item>> item = itemController.getItemsByName("Round Widget");
		assertTrue(item.getBody().size()>0 );
	}

	@Test
	public void testAddToCart(){

		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		ModifyCartRequest modifyCartRequest = ModifyCartRequest(1L, 2,newCustomer.getBody().getUsername());

		ResponseEntity<Cart> cart = cartController.addTocart(modifyCartRequest);
		assertEquals(cart.getBody().getItems().size() , 2);

	}

	@Test
	public void testRemoveFromCart(){

		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		ModifyCartRequest modifyCartRequest = ModifyCartRequest(1L, 2, newCustomer.getBody().getUsername());

		ResponseEntity<Cart> cart = cartController.addTocart(modifyCartRequest);
		assertEquals(cart.getBody().getItems().size() , 2L);

		ModifyCartRequest removeFromCart = ModifyCartRequest(1L, 1, newCustomer.getBody().getUsername());
		ResponseEntity<Cart> cartRemove = cartController.removeFromcart(removeFromCart);
		assertEquals(cartRemove.getBody().getItems().size(), 1L);

	}

	@Test
	public void testSubmitOrder(){
		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		ModifyCartRequest modifyCartRequest = ModifyCartRequest(1L, 2, newCustomer.getBody().getUsername());

		ResponseEntity<Cart> cart = cartController.addTocart(modifyCartRequest);
		assertEquals(cart.getBody().getItems().size() , 2L);

		ResponseEntity<UserOrder> order = orderController.submit(newCustomer.getBody().getUsername());

		assertEquals(order.getBody().getItems().size() , 2L);

	}

	@Test
	public void testGetOrdersForUser(){
		CreateUserRequest createUserRequest = CreateUserRequest();
		ResponseEntity<User> newCustomer = userController.createUser(createUserRequest);
		ModifyCartRequest modifyCartRequest = ModifyCartRequest(1L, 2, newCustomer.getBody().getUsername());

		ResponseEntity<Cart> cart = cartController.addTocart(modifyCartRequest);
		assertEquals(cart.getBody().getItems().size() , 2L);


		 orderController.submit(newCustomer.getBody().getUsername());
		orderController.submit(newCustomer.getBody().getUsername());
		ResponseEntity<List<UserOrder>> orderList = orderController.getOrdersForUser(newCustomer.getBody().getUsername());

		assertEquals(orderList.getBody().size() , 2L);

	}



	public static ModifyCartRequest ModifyCartRequest(Long Id, int quantity, String username){
		ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
		modifyCartRequest.setItemId(Id);
		modifyCartRequest.setQuantity(quantity);
		modifyCartRequest.setUsername(username);
		return modifyCartRequest;
	}


	private static CreateUserRequest CreateUserRequest() {
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername("test");
		createUserRequest.setPassword("password123");
		createUserRequest.setConfirmPassword("password123");
		return createUserRequest;
	}



}
