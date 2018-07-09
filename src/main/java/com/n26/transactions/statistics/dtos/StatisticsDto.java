package com.n26.transactions.statistics.dtos;

public class StatisticsDto {

    private double sum;

    private double avg;

    private double max;

    private double min;

    private long count;

    public StatisticsDto(double sum, double avg, double max, double min , long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public StatisticsDto withSum(double sum) {
        this.sum = sum;
        return this;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public StatisticsDto withAvg(double avg) {
        this.avg = avg;
        return this;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public StatisticsDto withMax(double max) {
        this.max = max;
        return this;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public StatisticsDto withMin(double min) {
        this.min = min;
        return this;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public StatisticsDto withCount(long count) {
        this.count = count;
        return this;
    }
}
