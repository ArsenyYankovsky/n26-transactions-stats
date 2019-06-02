package com.em.n26transactionsstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class N26TransactionsStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(N26TransactionsStatsApplication.class, args);
	}

}
