package com.em.n26transactionsstats.model.domain;

import com.em.n26transactionsstats.model.custom.QuantumViseStat;

public class StatisticDTO {

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

}
