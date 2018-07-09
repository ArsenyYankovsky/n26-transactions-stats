package com.n26.transactions.statistics.controllers;

import com.n26.transactions.statistics.dtos.StatisticsDto;
import com.n26.transactions.statistics.services.StatisticsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    /**
     * This is the main controller which returns the stats
     * @return
     */
    @RequestMapping(method = GET)
    @ApiOperation(value = "Produces statistics for all the transactions within 60 sec.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public @ResponseBody StatisticsDto getStatistics() {
        LOGGER.debug("Received a new request for latest statistics on : " + Instant.now());
        return statisticsService.getStats();
    }
}
