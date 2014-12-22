package com.simply.functional.mm.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simply.functional.mm.entity.MoneyTransactionCategory;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @RequestMapping("/find_all")
    public @ResponseBody List<MoneyTransactionCategory> getCategories() {
	List<MoneyTransactionCategory> categories = new ArrayList<>();

	MoneyTransactionCategory moneyTransactionCategory = new MoneyTransactionCategory();
	moneyTransactionCategory.setId(1);
	moneyTransactionCategory.setName("Entrada");
	moneyTransactionCategory.setBackgroundColor("#A1DDA1");
	moneyTransactionCategory.setFontColor("black");

	MoneyTransactionCategory moneyTransactionCategory2 = new MoneyTransactionCategory();
	moneyTransactionCategory2.setId(2);
	moneyTransactionCategory2.setName("Saída");
	moneyTransactionCategory2.setBackgroundColor("#FB9B9B");
	moneyTransactionCategory2.setFontColor("black");

	MoneyTransactionCategory c3 = new MoneyTransactionCategory();
	c3.setId(3);
	c3.setName("Fixo");
	c3.setFontColor("white");
	c3.setBackgroundColor("gray");

	MoneyTransactionCategory c4 = new MoneyTransactionCategory();
	c4.setId(4);
	c4.setName("Variável");
	c4.setFontColor("red");
	c4.setBackgroundColor("black");

	categories.add(moneyTransactionCategory);
	categories.add(moneyTransactionCategory2);
	categories.add(c3);
	categories.add(c4);

	return categories;
    }
    
}
