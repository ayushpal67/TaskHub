package com.stackhack.taskmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackhack.taskmanagement.entity.Customer;
import com.stackhack.taskmanagement.entity.Tasks;
import com.stackhack.taskmanagement.exception.SignUpException;
import com.stackhack.taskmanagement.repo.CustomerRepo;
import com.stackhack.taskmanagement.repo.TasksRepo;


@Service
public class CustomerService {

	@Autowired
	CustomerRepo custRepo;
	@Autowired
	TasksRepo taskRepo;
	
	public Customer FindUserbyEmail(String email) {
		// TODO Auto-generated method stub
		return custRepo.findCustomerByEmail(email);
	}
	
	public void signUp(Customer customer) {
		try {
			custRepo.save(customer);
		}
		
		catch (Exception e){
			throw new SignUpException("Failure");
		}
	}
	
	
	public List<Tasks> myTaskLists(long cid) {
		// TODO Auto-generated method stub
		try {
			
				List<Tasks> task = taskRepo.findAllTaskByCustomer_id(cid);
                /*List<Tasks> task1 =	task
                							.stream()
                							.filter(p->p.getCustomer()	
                							.getId() == cust.getId())
                							.collect(Collectors.toList());*/
                return task;
			}
			catch(Exception e)
			{
				throw new SignUpException("Failure");
			}
	}
}