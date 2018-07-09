package com.n26.transactions.statistics.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class DateUtils {

    private static long milliSecondsToConsider;

    @Value("${statistics.milliseconds-to-consider:60000}")
    public void setMilliSecondsToConsider(long milliSeconds) {
        milliSecondsToConsider = milliSeconds;
    }

    public static long getTimeToCompare() {
        return (Instant.now().getEpochSecond() * 1000) - milliSecondsToConsider;
    }
}
