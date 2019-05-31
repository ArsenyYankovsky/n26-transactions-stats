package com.em.n26transactionsstats.service.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.StatisticDTO;
import com.em.n26transactionsstats.service.StatisticService;

@Service
public class StatisticAnalyzer implements StatisticService {
	@Autowired
	CacheService cacheService;

	@Override
	public StatisticDTO getStatics() throws BusnessException {
		return cacheService.getStatistics();
	}

}
