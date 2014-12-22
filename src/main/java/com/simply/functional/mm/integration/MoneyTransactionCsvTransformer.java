package com.simply.functional.mm.integration;

import java.io.InputStream;
import java.util.List;

import com.simply.functional.mm.entity.MoneyTransaction;

public interface MoneyTransactionCsvTransformer {

    public List<MoneyTransaction> transform(InputStream inputData);

}
