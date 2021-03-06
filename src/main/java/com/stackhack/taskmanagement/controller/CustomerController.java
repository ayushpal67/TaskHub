package com.stackhack.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stackhack.taskmanagement.entity.Customer;
import com.stackhack.taskmanagement.exception.AllException;
import com.stackhack.taskmanagement.response.Response;
import com.stackhack.taskmanagement.service.CustomerService;


@CrossOrigin(origins="*")
@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signupDetails(@Valid @RequestBody Customer customer) {
		try {
			Customer cust = customerService.FindUserbyEmail(customer.getEmail());
			if(cust != null)
			{
				Response conflict=new Response("Email already exists", 409);
				return new ResponseEntity<Response>(conflict,HttpStatus.CONFLICT);
			}
			else {
				customerService.signUp(customer);
				Response success=new Response("Successfull",200);
				return new ResponseEntity<Response>(success,HttpStatus.OK);
			}
		}
		catch (Exception e){
			throw new AllException("Failure");
		}
	}
	@RequestMapping(value="/login/{email}/{password}", method = RequestMethod.GET)
	public ResponseEntity<?> LogIn(@PathVariable String email, @PathVariable String password)
	{
		try {
			Customer cust = customerService.FindUserbyEmail(email);
			if(cust == null)
			{
				Response notfound = new Response("Email Not Found", 403);
				return new ResponseEntity<Response>(notfound,HttpStatus.FORBIDDEN);
			}
			if(!cust.getPassword().equals(password))
			{
				Response notauth = new Response("Wrong Password", 401);
				return new ResponseEntity<Response>(notauth,HttpStatus.UNAUTHORIZED);
			}
			else
			{
				//Response success=new Response("Successfull",200);
				return new ResponseEntity<Customer>(cust, HttpStatus.OK);
			}
		}
		catch(Exception e)
		{
			throw new AllException("Failure");
		}
	}
}
