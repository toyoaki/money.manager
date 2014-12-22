package com.simply.functional.mm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "money_transaction")
public class MoneyTransaction {

    @Id
    private Integer id;

    @Column
    private Date date;

    @Column
    private BigDecimal value;

    @Column
    private String description;

    @Transient
    private List<MoneyTransactionCategory> categories;

    public MoneyTransaction() {
    }

    public MoneyTransaction(Date date, BigDecimal value, String description) {
	super();
	this.date = date;
	this.value = value;
	this.description = description;
    }

    @Override
    public String toString() {
	return "Transaction [id=" + id + ", date=" + date + ", value=" + value + ", description=" + description + "]";
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public BigDecimal getValue() {
	return value;
    }

    public void setValue(BigDecimal value) {
	this.value = value;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<MoneyTransactionCategory> getCategories() {
	return categories;
    }

    public void setCategories(List<MoneyTransactionCategory> categories) {
	this.categories = categories;
    }

}
