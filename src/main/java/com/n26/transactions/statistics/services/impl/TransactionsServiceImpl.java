package com.n26.transactions.statistics.services.impl;

import com.n26.transactions.statistics.domain.TransactionsStats;
import com.n26.transactions.statistics.dtos.StatisticsDto;
import com.n26.transactions.statistics.dtos.TransactionsDto;
import com.n26.transactions.statistics.services.TransactionsService;
import com.n26.transactions.statistics.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsStats transactionsStats;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    private ConcurrentSkipListMap<TransactionsDto, StatisticsDto> transactionStatsRecord = new ConcurrentSkipListMap<>();

    public void addTransaction(TransactionsDto transactionDto) {

        transactionStatsRecord.put(transactionDto , new StatisticsDto(0,0,0,0,0));
        transactionsStats.setTransactionsDtoMap(transactionStatsRecord);

        transactionsStats.getTransactionsDtoMap().entrySet().parallelStream()
                .forEach(transaction -> {

                            LOGGER.debug("Time stamp of record : " + transaction.getKey().getTimestamp());
                            LOGGER.debug("ID of record : " + transaction.getKey().getId());
                            LOGGER.debug("Amount of record : " + transaction.getKey().getAmount());
                            LOGGER.debug("Timestamp for eligible records : " + DateUtils.getTimeToCompare());

                            if (transaction.getKey().getTimestamp() < DateUtils.getTimeToCompare()) {

                                transactionsStats.getTransactionsDtoMap().remove(transaction.getKey());
                            } else {

                                DoubleSummaryStatistics statistics =
                                        transactionsStats.getTransactionsDtoMap().entrySet().parallelStream()
                                                .filter(record -> record.getKey().getTimestamp() >=
                                                        DateUtils.getTimeToCompare())
                                                .collect(Collectors.summarizingDouble(e -> e.getKey().getAmount()));

                                LOGGER.debug("Sum : " + statistics.getSum());
                                LOGGER.debug("Avg : " + statistics.getAverage());
                                LOGGER.debug("Max : " + statistics.getMax());
                                LOGGER.debug("Min : " + statistics.getMin());
                                LOGGER.debug("Count : " + statistics.getCount());

                                transactionStatsRecord.put(transactionDto, new StatisticsDto(statistics.getSum(),
                                        statistics.getAverage(), statistics.getMax(), statistics.getMin(),
                                        statistics.getCount()));
                                transactionsStats.setTransactionsDtoMap(transactionStatsRecord);
                            }
                        }
                );
    }
}
