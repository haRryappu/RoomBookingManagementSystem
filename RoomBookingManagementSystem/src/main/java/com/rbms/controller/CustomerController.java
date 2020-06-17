package com.rbms.controller;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbms.dao.CustomerDao;
import com.rbms.entity.Customer;
import com.rbms.utils.JsonConverter;

@RestController
@RequestMapping(value = "/api/")
public class CustomerController {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private JsonConverter jsonConvert;

	@RequestMapping(value = "customer", method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws Exception {

		Optional<Customer> dbCustomer1 = customerDao.findByUsername(customer.getUsername());
		int pwdLength = customer.getPassword().length();
		Optional<Customer> dbCustomer2=customerDao.findByEmail(customer.getEmail());
		JSONObject entity = new JSONObject();

		if(dbCustomer2.isPresent())
		{
			entity.put("message", "Email is already present");
			return new ResponseEntity(entity, HttpStatus.BAD_REQUEST);
		}
		else if (dbCustomer1.isPresent() == false && (pwdLength >= 8 && pwdLength <= 10)) {
			customerDao.save(customer);
			return new ResponseEntity(HttpStatus.CREATED);
		}
		entity.put("message", "Password does not fit the criteria or user name already exists");
		return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);

	}

	
	@GetMapping("customer/{username}")
	public ResponseEntity<?> customerDetails(@PathVariable String username) {

		Optional<Customer> customer = customerDao.findByUsername(username);
		if (customer.isPresent()) {
			return new ResponseEntity(jsonConvert.convert(customer),HttpStatus.OK);
		}
		
		JSONObject entity = new JSONObject();
		entity.put("message", "Non matching username");
		return new ResponseEntity(entity,HttpStatus.NOT_FOUND);
	}
	
}
