package com.em.n26transactionsstats.service;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.StatisticDTO;

public interface StatisticService {
public StatisticDTO getStatics() throws BusnessException;
}
