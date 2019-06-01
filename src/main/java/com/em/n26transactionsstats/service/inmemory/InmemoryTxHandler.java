package com.em.n26transactionsstats.service.inmemory;

import java.time.ZonedDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.TransactionDTO;
import com.em.n26transactionsstats.service.AbstractTxHandler;

@Service
public class InmemoryTxHandler extends AbstractTxHandler {
	@Autowired
	private CacheService cacheService;
	{
		LOGGER = LoggerFactory.getLogger(InmemoryTxHandler.class);
	}
	@Override
	protected void acceptTx(final TransactionDTO txDto, final ZonedDateTime compairedWith) throws BusnessException {
		if (!dateUtil.isInCurrentTimeWindow(cacheService.getCurrentDateTime(), compairedWith)) {
//			synchronized(this)  {
				cacheService.clearCache(compairedWith);
//				cacheService.add(txDto);
//			}
			
		}
//		else {
//			synchronized(this)  {
				cacheService.add(txDto);
//			}
//		}
		

	}
}
