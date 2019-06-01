package com.em.n26transactionsstats.service.inmemory;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.custom.QuantumViseStat;
import com.em.n26transactionsstats.model.domain.StatisticDTO;
import com.em.n26transactionsstats.model.domain.TransactionDTO;
import com.em.n26transactionsstats.util.ConfigReader;
import com.em.n26transactionsstats.util.ErrorType;

@Component
class CacheService {
	protected Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
	private ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
	QuantumViseStat quantumViseStat[];
	@Autowired
	ConfigReader config;

	/**
	 * this cache service is based on array .i.e the number of seconds supported by
	 * the implementation is limit to Integer.MAX_VALUE
	 * 
	 * @throws BusnessException
	 */
	@PostConstruct
	public void init() throws BusnessException {
		final int _reportSize = (int) config.getReportSizeInSeconds();
		if (_reportSize < 01) {
			throw new BusnessException(ErrorType.INVALID_REPORT_SIZE);
		}
		quantumViseStat = new QuantumViseStat[_reportSize];

	}

	public ZonedDateTime getCurrentDateTime() {
		return this.currentDateTime;
	}

	public void add(final TransactionDTO txDTO) throws BusnessException {

		int _index = (int) (txDTO.getTxInSeconds() % config.getReportSizeInSeconds());
		QuantumViseStat _quantumViseStat = quantumViseStat[_index];
		if (_quantumViseStat == null) {
			_quantumViseStat = new QuantumViseStat();
			quantumViseStat[_index] = _quantumViseStat;
		}
		synchronized (_quantumViseStat) {
		_quantumViseStat.add(txDTO);
		}

	}

	/**
	 * this will reset the Quantum vies statistics to zero. The reset_start_index =
	 * current_ time_in_seconds modules reportSize_in_second. from reset_start_index
	 * to 1st all the quantum vise statistics set to zero. This is thread safe
	 * operation
	 * 
	 * @param currentTime
	 */
	public synchronized void clearCache(final ZonedDateTime newTimeWindow) {
		final int _resetIndex = (int) (newTimeWindow.toEpochSecond() % config.getReportSizeInSeconds());
		LOGGER.debug("cache clearing started from index :" + _resetIndex + " to 1");
		for (int x = _resetIndex; x > 0; x--) {
			QuantumViseStat quantumStat = quantumViseStat[x];
			if (quantumStat != null) {
				quantumStat.rest();
			}
		}
		LOGGER.debug(" re set the cache time window from " + this.currentDateTime + " TO :" + newTimeWindow);
		// re-set current time window with new time window
		this.currentDateTime = newTimeWindow;

	}

	/**
	 * return the statistics for past time window specified as reportingSize.
	 * 
	 * @return
	 */
	public synchronized StatisticDTO getStatistics() {
		final long _lowerBound = ZonedDateTime.now(ZoneOffset.UTC).toInstant().getEpochSecond() - (config.getReportSizeInSeconds());
		StatisticDTO _returnDto = new StatisticDTO();

		 
		Arrays.stream(quantumViseStat)
				.parallel()
				.filter(e -> (e != null && e.getTimeInseconds() >= _lowerBound)) //filter out all Quantumvise statistics which are in current report window
				.forEach(e ->_returnDto.add(e)); //Add to the statistic dto

		return _returnDto;
	}
}
