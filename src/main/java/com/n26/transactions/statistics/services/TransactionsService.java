package com.n26.transactions.statistics.services;

import com.n26.transactions.statistics.dtos.TransactionsDto;

public interface TransactionsService {

    void addTransaction(TransactionsDto transaction);
}
