package com.em.n26transactionsstats.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.em.n26transactionsstats.exception.BusnessException;
import com.em.n26transactionsstats.model.domain.TransactionDTO;
import com.em.n26transactionsstats.service.StatisticService;
import com.em.n26transactionsstats.service.TransactionsService;
import com.em.n26transactionsstats.util.ErrorType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TransactionsController {

	@Autowired
	private TransactionsService transactionsService;
	@Autowired
	private StatisticService statisticsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsController.class);

	@RequestMapping(method = POST, value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create transactions records", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Record Created Successfully"),
			@ApiResponse(code = 204, message = "Transaction is older than 60 seconds") })
	public ResponseEntity<String> createTransaction(@RequestBody @Valid TransactionDTO transactionsDto) {

		LOGGER.info("Timestamp received is : " + transactionsDto);

		try {
			transactionsService.add(transactionsDto);
			return ResponseEntity.ok("Record Created Successfully");
		} catch (BusnessException e) {
			LOGGER.error(e.toString());
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			if (e.getErrorType().equals(ErrorType.OLD_TX)) {
				status = HttpStatus.NO_CONTENT;
			}
			throw new ResponseStatusException(status, e.getErrorType().toString(), e);
		}

	}

	@RequestMapping(method = GET, value = "statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Produces statistics for all the transactions within 60 sec.", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public @ResponseBody ResponseEntity<?> getStatistics() {
		LOGGER.debug("Received a new request for latest statistics on : "
				+ ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond());
		try {
			return ResponseEntity.ok(statisticsService.getStatics());
		} catch (BusnessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorType().toString(), e);
		}
	}

}