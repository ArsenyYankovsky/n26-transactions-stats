package com.em.n26transactionsstats.model.domain;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TransactionDTO {

	private ZonedDateTime timestamp;

	private double amount;

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
		;
	}

	/**
	 * return the transaction time as ZonedDateTime in the UTC zone
	 * 
	 * @return
	 */
	@JsonIgnore
	public ZonedDateTime getTxTime() {
		return timestamp;
	}

	/**
	 * return the transaction time as seconds in the UTC zone
	 * 
	 * @return
	 */
	@JsonIgnore
	public long getTxInSeconds() {
		return timestamp.toInstant().getEpochSecond();
	}

	public double getAmount() {
		// TODO Auto-generated method stub
		return this.amount;
	}



	@Override
	public String toString() {
		return "TransactionDTO [timestamp =" + timestamp.toInstant().toEpochMilli() + " Ms , amount=" + amount + "]";
	}
	
}
