package com.simply.functional.mm.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.simply.functional.mm.entity.MoneyTransaction;
import com.simply.functional.mm.repository.MoneyTransactionRepository;

@Repository
public class MoneyTransactionRepositoryJpa implements MoneyTransactionRepository {

    @PersistenceContext
    private EntityManager em;

    public MoneyTransaction find() {
	return this.em.find(MoneyTransaction.class, 1);
    }

}
