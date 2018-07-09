package com.n26.transactions.statistics.dtos;

import java.util.concurrent.atomic.AtomicLong;

public class TransactionsDto implements Comparable<TransactionsDto> {

    private long timestamp;

    private Double amount;

    private final static AtomicLong sequence = new AtomicLong(1);
    private final long id = sequence.getAndIncrement();

    public TransactionsDto() {}

    public TransactionsDto(long timestamp, Double amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionsDto withTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionsDto withAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public int compareTo(TransactionsDto transactionsDto) {
        return Long.compare(this.id, transactionsDto.id);
    }
}
