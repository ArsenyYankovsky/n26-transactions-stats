package com.em.n26transactionsstats.service;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.em.n26transactionsstats.controller.TransactionsController;
import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.TransactionDTO;
import com.em.n26transactionsstats.util.ConfigReader;
import com.em.n26transactionsstats.util.DateUtil;
import com.em.n26transactionsstats.util.ErrorType;

abstract public class AbstractTxHandler implements TransactionsService {
	protected  Logger LOGGER;
	@Autowired
	protected ConfigReader configurations;
	@Autowired
	protected DateUtil dateUtil;

	/**
	 * validate the received transaction against the acceptable criteria. eg:
	 * transaction cannot be older than 60s if there is any rule violation throws a
	 * BusnessException
	 * 
	 * @param txDTO
	 * @throws BusnessException
	 */
	protected void validate(final TransactionDTO txDTO, final ZonedDateTime compairedWith) throws BusnessException {

		if (dateUtil.isTooOld(txDTO, compairedWith)) {

			throw new BusnessException(ErrorType.OLD_TX);
		}
	}

	/**
	 * valid transactions added to respective time Quantum.
	 * 
	 * @param txDtoc
	 * @throws BusnessException
	 */
	abstract protected void acceptTx(final TransactionDTO txDtoc, final ZonedDateTime compairedWith)
			throws BusnessException;

	final public void add(final TransactionDTO txDto) throws BusnessException {
		final ZonedDateTime compairedWith = dateUtil.now();
		validate(txDto, compairedWith);
		acceptTx(txDto, compairedWith);
	}
}
