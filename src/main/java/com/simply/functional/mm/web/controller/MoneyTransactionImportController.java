package com.simply.functional.mm.web.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.simply.functional.mm.entity.MoneyTransaction;
import com.simply.functional.mm.integration.impl.BradescoMoneyTransactionCsvImporter;
import com.simply.functional.mm.service.MoneyTransactionService;

@Controller
@RequestMapping("/money_transaction_import")
public class MoneyTransactionImportController {

    @Autowired
    private MoneyTransactionService transactionService;

    @RequestMapping("/")
    public String goToPage() {
	return "money_transaction_import";
    }

    @RequestMapping("/test")
    public @ResponseBody MoneyTransaction test() {
	return transactionService.find();
    }

    @RequestMapping(value = "/upload_file", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<List<MoneyTransaction>> handleFileUpload(@RequestParam("file") MultipartFile file) {
	try {
	    List<MoneyTransaction> transactions = new ArrayList<>();
	    if (!file.isEmpty()) {
		byte[] bytes = file.getBytes();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		transactions = new BradescoMoneyTransactionCsvImporter().transform(inputStream);
	    }
	    return new ResponseEntity<List<MoneyTransaction>>(transactions, HttpStatus.OK);
	} catch (Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity<List<MoneyTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

}
