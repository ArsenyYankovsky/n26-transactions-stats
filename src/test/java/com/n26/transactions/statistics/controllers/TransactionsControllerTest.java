package com.n26.transactions.statistics.controllers;

import com.n26.transactions.statistics.TestApplication;
import com.n26.transactions.statistics.dtos.TransactionsDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.Instant;

import static io.restassured.RestAssured.given;

@EnableAutoConfiguration
public class TransactionsControllerTest extends TestApplication {

    @Before
    public void setup() {}

    @Test
    public void insertNewTransaction() throws Exception {
        given()
                .baseUri("http://localhost")
                .port(port)
                .contentType("application/json")
                .body(getNewTransaction())
        .when()
                .post("/transactions")
        .then()
                .assertThat()
                .statusCode(201);
    }

    private TransactionsDto getNewTransaction() {
        return new TransactionsDto().withTimestamp(Instant.now().getEpochSecond() * 1000 ).withAmount(90.00);
    }
}