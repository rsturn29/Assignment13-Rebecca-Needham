package com.coderscampus.assignment13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transRepo;
	


	public void deleteById(Long transactionId) {
		transRepo.deleteById(transactionId);
		
	}
	
}
