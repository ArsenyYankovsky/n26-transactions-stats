package com.n26.transactions.statistics.controllers;

import com.n26.transactions.statistics.TestApplication;
import com.n26.transactions.statistics.dtos.StatisticsDto;
import com.n26.transactions.statistics.dtos.TransactionsDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.Instant;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;

@EnableAutoConfiguration
public class TransactionStatisticsControllerTest extends TestApplication {

    @Before
    public void setup() {}

    @Test
    public void testSequentialInserts() throws Exception {
        addExpiredTransactions();
        getInitialStatistics();
        addValidTransactions();
        getFinalStatistics();
    }

    private void addExpiredTransactions() throws Exception {

        IntStream.range(0, 100).asDoubleStream().forEach(dAmount ->
                given()
                        .baseUri("http://localhost")
                        .port(port)
                        .contentType("application/json")
                        .body(getExpiredTransaction(dAmount))
                .when()
                        .post("/transactions")
                .then()
                        .assertThat()
                        .statusCode(204));
    }

    private void getInitialStatistics() throws Exception {

        StatisticsDto statisticsDto =
                given()
                        .baseUri("http://localhost")
                        .port(port)
                        .contentType("application/json")
                .when()
                        .get("/statistics")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .log().ifValidationFails()
                .and()
                        .extract().body().as(StatisticsDto.class);

        Assert.assertEquals(90.00, statisticsDto.getSum(), 0.0);
        Assert.assertEquals(90.00, statisticsDto.getAvg(), 0.0);
        Assert.assertEquals(90.00, statisticsDto.getMax(), 0.0);
        Assert.assertEquals(90.00, statisticsDto.getMin(), 0.0);
        Assert.assertEquals(1, statisticsDto.getCount(), 0.0);
    }

    private void addValidTransactions() throws Exception {

        IntStream.range(0, 100).asDoubleStream().forEach(dAmount ->
                given()
                        .baseUri("http://localhost")
                        .port(port)
                        .contentType("application/json")
                        .body(getNewTransaction(dAmount))
                .when()
                        .post("/transactions")
                .then()
                        .assertThat()
                        .statusCode(201));
    }

    private void getFinalStatistics() throws Exception {

        StatisticsDto statisticsDto =
                given()
                        .baseUri("http://localhost")
                        .port(port)
                        .contentType("application/json")
                .when()
                        .get("/statistics")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .log().body()
                .and()
                        .extract().body().as(StatisticsDto.class);

        Assert.assertEquals(5040.0, statisticsDto.getSum(), 0.0);
        Assert.assertEquals(50.00, statisticsDto.getAvg(), 0.5);
        Assert.assertEquals(99.00, statisticsDto.getMax(), 0.0);
        Assert.assertEquals(0.00, statisticsDto.getMin(), 0.0);
        Assert.assertEquals(101, statisticsDto.getCount(), 0.0);
    }

    private TransactionsDto getNewTransaction(double dAmount) {
        return new TransactionsDto().withAmount(dAmount).withTimestamp((Instant.now().getEpochSecond() * 1000) - 30000);
    }

    private TransactionsDto getExpiredTransaction(double dAmount) {
        return new TransactionsDto().withAmount(dAmount).withTimestamp((Instant.now().getEpochSecond() * 1000) - 70000);
    }
}