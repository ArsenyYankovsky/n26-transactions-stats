package com.n26.transactions.statistics.controllers;

import com.n26.transactions.statistics.dtos.TransactionsDto;
import com.n26.transactions.statistics.services.TransactionsService;
import com.n26.transactions.statistics.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

	@Autowired
	private TransactionsService transactionsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsController.class);

    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create transactions records", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Record Created Successfully"),
            @ApiResponse(code = 204, message = "Transaction is older than 60 seconds")
    })
	public @ResponseBody ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionsDto transactionsDto) {

        LOGGER.info("Timestamp received is : " + transactionsDto.getTimestamp());
        LOGGER.debug("Amount of transaction received is : " + transactionsDto.getAmount());

		if(transactionsDto.getTimestamp() < DateUtils.getTimeToCompare()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

        transactionsService.addTransaction(transactionsDto);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}