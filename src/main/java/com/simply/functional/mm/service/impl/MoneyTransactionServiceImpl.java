package com.simply.functional.mm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simply.functional.mm.entity.MoneyTransaction;
import com.simply.functional.mm.repository.MoneyTransactionRepository;
import com.simply.functional.mm.service.MoneyTransactionService;

@Service
public class MoneyTransactionServiceImpl implements MoneyTransactionService {

    @Autowired
    private MoneyTransactionRepository transactionRepository;

    public MoneyTransaction find() {
	return transactionRepository.find();
    }

}
