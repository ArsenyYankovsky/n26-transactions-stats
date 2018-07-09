package com.n26.transactions.statistics.controllers;

import com.n26.transactions.statistics.TestApplication;
import com.n26.transactions.statistics.dtos.StatisticsDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static io.restassured.RestAssured.given;

@EnableAutoConfiguration
public class StatisticsControllerTest extends TestApplication {

    @Before
    public void setup() {}

    @Test
    public void validateInitialStats() throws Exception {
        StatisticsDto statisticsDto = given()
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

        Assert.assertEquals(0.0, statisticsDto.getSum(), 0.0);
        Assert.assertEquals(0.0, statisticsDto.getAvg(), 0.0);
        Assert.assertEquals(0.0, statisticsDto.getMax(), 0.0);
        Assert.assertEquals(0.0, statisticsDto.getMin(), 0.0);
        Assert.assertEquals(0, statisticsDto.getCount(), 0.0);
    }
}