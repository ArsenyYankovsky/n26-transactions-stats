package com.em.n26transactionsstats.service;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.TransactionDTO;

public interface TransactionsService {
	public void add(final TransactionDTO dto) throws BusnessException;

}
