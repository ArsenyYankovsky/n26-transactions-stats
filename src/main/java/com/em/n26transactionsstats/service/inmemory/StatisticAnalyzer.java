package com.em.n26transactionsstats.service.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.StatisticDTO;
import com.em.n26transactionsstats.service.StatisticService;

@Service
public class StatisticAnalyzer implements StatisticService {
	protected Logger LOGGER = LoggerFactory.getLogger(InmemoryTxHandler.class);

	@Autowired
	CacheService cacheService;

	@Override
	public StatisticDTO getStatics() throws BusnessException {
		StatisticDTO stat = cacheService.getStatistics();
		LOGGER.debug("getStatics " + stat);
		return stat;
	}

}
