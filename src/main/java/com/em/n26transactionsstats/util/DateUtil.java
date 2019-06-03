package com.em.n26transactionsstats.util;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.em.n26transactionsstats.model.domain.TransactionDTO;

/**
 * 
 * @author nwalisundara This is a utility class for date/time operation. Bean
 *         scope set to request so that each time it autowired it will
 *         instantiate for each request.
 */
@Component
public class DateUtil {
	@Autowired
	protected ConfigReader configurations;

	// initialize the object created time in UTC time
	public ZonedDateTime now() {
		return ZonedDateTime.now(ZoneOffset.UTC);
	}

	/**
	 * compare the given Transaction is older than the report size i.e. report size
	 * is 60 seconds this will return true if the tx time is older than 60s
	 * 
	 * @param txDTO
	 * @return
	 */
	public boolean isTooOld(final TransactionDTO txDTO, final ZonedDateTime comparingDateTime) {
		return Duration.between(txDTO.getTxTime(), comparingDateTime)
				.toMillis() > (configurations.getReportSizeInSeconds() * 1000);

	}

	/**
	 * return the dateTime which initialize the at the time of object initialization
	 * 
	 * @return
	 *//*
		 * public ZonedDateTime getInitializedTime() { return this.createdDateTime; }
		 */

	/**
	 * The service check weather the given time is within the time window defined by
	 * the reportsize
	 * 
	 * @param currentTimeInSec
	 * @return
	 */
	public boolean isInCurrentTimeWindow(final ZonedDateTime zonedDateTime, final ZonedDateTime createdDateTime) {
		return Duration.between(zonedDateTime, createdDateTime).getSeconds() < configurations.getReportSizeInSeconds();
	}
}
