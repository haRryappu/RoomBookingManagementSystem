package com.rbms.utils;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.rbms.entity.Customer;

@Component
public class JsonConverter {
	public static JSONObject convert(Optional<Customer> customer) {
		JSONObject entity = new JSONObject();
		entity.put("username", customer.get().getUsername());
		entity.put("firstname", customer.get().getFirstname());
		entity.put("lastname", customer.get().getLastname());
		entity.put("dob", customer.get().getDob());
		entity.put("email", customer.get().getEmail());
		entity.put("password", customer.get().getPassword());
		return entity;
	}
}
