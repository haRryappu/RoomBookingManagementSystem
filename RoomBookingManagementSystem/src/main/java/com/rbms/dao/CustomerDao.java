package com.rbms.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rbms.entity.Customer;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Long> {

	Optional <Customer> findByUsername(String username);
	Optional <Customer> findByEmail(String email);
}
