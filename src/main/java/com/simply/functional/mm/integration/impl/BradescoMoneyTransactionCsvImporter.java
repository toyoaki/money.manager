package com.simply.functional.mm.integration.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.simply.functional.mm.entity.MoneyTransaction;
import com.simply.functional.mm.entity.MoneyTransactionCategory;
import com.simply.functional.mm.integration.MoneyTransactionCsvTransformer;

public class BradescoMoneyTransactionCsvImporter implements MoneyTransactionCsvTransformer {

    private static final String BRADESCO_CSV_FILE_ENCODING = "windows-1252";

    private List<MoneyTransaction> transactions;

    public List<MoneyTransaction> transform(InputStream inputData) {
	transactions = new ArrayList<>();

	try (Scanner scanner = new Scanner(inputData, BRADESCO_CSV_FILE_ENCODING)) {
	    int currentLineNumber = 1;
	    while (scanner.hasNextLine()) {
		String fileLine = scanner.nextLine();
		if (lineIsNotPartOfHeader(currentLineNumber)) {
		    MoneyTransaction transaction = parseLineAndRemoveTransactionIfNecessary(fileLine);

		    if (transaction == null)
			break;

		    transactions.add(transaction);
		}
		currentLineNumber++;
	    }
	}

	fillTransactionsCategories();

	return transactions;
    }

    private int getLastTransactionIndex() {
	return transactions.size() - 1;
    }

    private void removeLastTransactionFromList() {
	int lastTransactionIndex = getLastTransactionIndex();
	transactions.remove(lastTransactionIndex);
    }

    private boolean lineIsNotPartOfHeader(int lineNumber) {
	return lineNumber > 3;
    }

    private MoneyTransaction parseLineAndRemoveTransactionIfNecessary(String fileLine) {
	MoneyTransaction transaction = null;
	Date date = null;
	BigDecimal value = null;

	String[] lineElements = fileLine.split(";");
	String description = StringUtils.trimToNull(lineElements[1]);

	if (!hasReachedFooter(description)) {
	    date = getDate(lineElements[0]);

	    if (date == null) {
		transaction = mergeWithLastTransactionDescription(description);
		removeLastTransactionFromList();
	    } else {
		String valueAsString = getIncomeValue(lineElements);
		valueAsString = valueAsString == null ? getExpenseValue(lineElements) : valueAsString;
		value = getBigDecimal(valueAsString);
		transaction = new MoneyTransaction(date, value, description);
	    }
	}

	return transaction;
    }

    private MoneyTransaction mergeWithLastTransactionDescription(String description) {
	int lastTransactionIndex = getLastTransactionIndex();
	MoneyTransaction lastTransaction = transactions.get(lastTransactionIndex);
	return new MoneyTransaction(lastTransaction.getDate(), lastTransaction.getValue(),
		lastTransaction.getDescription() + " - " + description);
    }

    private boolean hasReachedFooter(String description) {
	return description.equals("Total");
    }

    private String getIncomeValue(String[] lineElements) {
	return StringUtils.trimToNull(lineElements[3]);
    }

    private String getExpenseValue(String[] lineElements) {
	return StringUtils.trimToNull(lineElements[4]);
    }

    private BigDecimal getBigDecimal(String value) {
	value = value.replace("\"", "");
	value = value.replace(".", "");
	value = value.replace(',', '.');

	return new BigDecimal(value);
    }

    private Date getDate(String date) {
	Date convertedDate = null;
	try {
	    convertedDate = new Date(DateUtils.parseDate(StringUtils.trimToNull(date), "dd/MM/yy").getTime());
	} catch (IllegalArgumentException | ParseException e) {
	}
	return convertedDate;
    }

    private void fillTransactionsCategories() {
	for (MoneyTransaction transaction : transactions) {
	    transaction.setCategories(getCategories(transaction.getDescription()));
	}
    }

    private List<MoneyTransactionCategory> getCategories(String fullDescription) {
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

	return Arrays.asList(moneyTransactionCategory, moneyTransactionCategory2);
    }
}
