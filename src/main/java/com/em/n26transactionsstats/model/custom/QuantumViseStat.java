package com.em.n26transactionsstats.model.custom;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.em.n26transactionsstats.model.domain.TransactionDTO;

public class QuantumViseStat {
	protected  Logger LOGGER = LoggerFactory.getLogger(QuantumViseStat.class);
	private double sum;
	private long count;
	private final ZonedDateTime createdDateTime;

	public QuantumViseStat() {
		createdDateTime = ZonedDateTime.now(ZoneOffset.UTC);
	}

	public long getTimeInseconds() {
		return createdDateTime.toInstant().getEpochSecond();
	}
	public long getTimeInMillis() {
		return createdDateTime.toInstant().toEpochMilli();
	}

	public void add(TransactionDTO txDTO) {
	
		this.count++;
		this.sum += txDTO.getAmount();
	}

	public void rest() {
		this.sum = 0d;
		this.count = 0l;
	}

	public double getSum() {

		return sum;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "QuantumViseStat [sum=" + sum + ", count=" + count + ", createdDateTime=" + createdDateTime + "]";
	}

	
}
