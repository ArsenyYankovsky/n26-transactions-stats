package com.n26.transactions.statistics.services.impl;

import com.n26.transactions.statistics.domain.TransactionsStats;
import com.n26.transactions.statistics.dtos.StatisticsDto;
import com.n26.transactions.statistics.services.StatisticsService;
import com.n26.transactions.statistics.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    TransactionsStats transactionsStats;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    /**
     * Get latest stats from TransactionStats repository with 0(1) time and space complexity.
     * @return CompletableFuture instance of StatisticsDto.
     */
    @Override
    public StatisticsDto getStats() {

        if (null == transactionsStats.getTransactionsDtoMap() ||
                transactionsStats.getTransactionsDtoMap().isEmpty() ||
                transactionsStats.getTransactionsDtoMap().firstEntry().getKey().getTimestamp() <
                        DateUtils.getTimeToCompare()) {
            LOGGER.info("No Valid Stats found hence returning 0 : ");
            return new StatisticsDto(0, 0, 0, 0, 0);
        } else {
            StatisticsDto statisticsDto = transactionsStats.getTransactionsDtoMap().lastEntry().getValue();
            LOGGER.info("Sum of the latest stats record : " + statisticsDto.getSum());
            LOGGER.info("Avg of the latest stats record : " + statisticsDto.getAvg());
            LOGGER.info("Max of the latest stats record : " + statisticsDto.getMax());
            LOGGER.info("Min of the latest stats record : " + statisticsDto.getMin());
            LOGGER.info("Count of the latest stats record : " + statisticsDto.getCount());
            return statisticsDto;
        }
    }
}