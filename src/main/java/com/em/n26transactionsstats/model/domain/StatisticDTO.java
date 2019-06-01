package com.em.n26transactionsstats.model.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.em.n26transactionsstats.model.custom.QuantumViseStat;

public class StatisticDTO {
	protected  Logger LOGGER = LoggerFactory.getLogger(StatisticDTO.class);
	private double sum;
	private long count;

	public double getSum() {
		return sum;
	}

	public long getCount() {
		return count;
	}

	public void add(QuantumViseStat e) {
		sum += e.getSum();
		count += e.getCount();
	}

	@Override
	public String toString() {
		return "StatisticDTO [sum=" + sum + ", count=" + count + "]";
	}
	
	

}
