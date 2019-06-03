package com.em.n26transactionsstats.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * All the configurations goes hear.
 * 
 * @author nwalisundara
 *
 */
@Component
public class ConfigReader {

	/**
	 * read the property statistics.reportsize-in-seconds if explicitly defined. 
	 * default is 60 s
	 */
	@Value("${statistics.reportsize-in-seconds:60}")
	private  long reportSizeInSeconds;

	public long getReportSizeInSeconds() {
		return this.reportSizeInSeconds;
	}

}
