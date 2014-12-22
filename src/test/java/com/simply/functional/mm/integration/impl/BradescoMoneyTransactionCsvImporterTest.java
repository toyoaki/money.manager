package com.simply.functional.mm.integration.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.simply.functional.mm.entity.MoneyTransaction;

public class BradescoMoneyTransactionCsvImporterTest {

    @Test
    public void testTransform() throws FileNotFoundException {
	BradescoMoneyTransactionCsvImporter csvImporter = new BradescoMoneyTransactionCsvImporter();

	InputStream inputData = new FileInputStream("src/test/resources/csv_files/bradesco.csv");

	List<MoneyTransaction> transactions = csvImporter.transform(inputData);

	for (MoneyTransaction t : transactions)
	    System.out.println(t);
    }

}
