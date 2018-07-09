package com.n26.transactions.statistics.domain;

import com.n26.transactions.statistics.dtos.StatisticsDto;
import com.n26.transactions.statistics.dtos.TransactionsDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class TransactionsStats {

    private static volatile ConcurrentSkipListMap<TransactionsDto, StatisticsDto> transactionsDtoMap;

    public TransactionsStats() {
        if (null == transactionsDtoMap) transactionsDtoMap = new ConcurrentSkipListMap<>();
    }

    public void setTransactionsDtoMap(ConcurrentSkipListMap<TransactionsDto, StatisticsDto> transactionsRecord) {
        transactionsDtoMap = transactionsRecord;
    }

    public ConcurrentSkipListMap<TransactionsDto, StatisticsDto> getTransactionsDtoMap() {
        return transactionsDtoMap;
    }
}
