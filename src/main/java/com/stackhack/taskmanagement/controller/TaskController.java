package com.stackhack.taskmanagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackhack.taskmanagement.entity.Tasks;
import com.stackhack.taskmanagement.exception.SignUpException;
import com.stackhack.taskmanagement.service.CustomerService;


@CrossOrigin(origins="*")
@RestController
public class TaskController {


	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/home/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> myTasks(@PathVariable long id)
	{
		try {
		List<Tasks> task=customerService.myTaskLists(id);
		return new ResponseEntity<List<Tasks>>(task, HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new SignUpException("Failure");
		}
	}
	
	@RequestMapping(value="/addtask/{customer_id}/{status_id}/{label_id}", method = RequestMethod.POST)
	public ResponseEntity<?> AddTask(@RequestBody Tasks task, @PathVariable long customer_id, @PathVariable long status_id, @PathVariable long label_id)
	{
		try {
			customerService.AddTask(task,customer_id,status_id,label_id);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new SignUpException("Failure");		}
	}
	
	@RequestMapping(value="/edittask/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> EditTask(@RequestBody Tasks task, @PathVariable long id)
	{
		customerService.EditTask(task,id);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}