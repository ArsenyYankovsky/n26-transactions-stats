package com.n26.transactions.statistics.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public void handleIoException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParameterException() {
        LOGGER.warn("Client is sending requests with missing parameters");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public void handleRequestNotSupported() {
        LOGGER.warn("Client is sending bad requests");
    }

    /**
     * Bad Request.
     * @param req request
     * @param e exception
     * @return Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> rulesGeneric400Exception(HttpServletRequest req, Exception e) {
        LOGGER.debug("GlobalExceptionHandler.rulesGeneric400Exception - Started");
        List<ErrorInformation> errorList = new ArrayList<ErrorInformation>();
        ErrorInformation error = new ErrorInformation();
        error.setErrorCode("400");
        error.setAdvice("Bad Request");
        error.setTitle("Bad Request");
        error.setStatusCode("400");
        error.setMessage(e.getMessage());
        error.setStackMessage(e.getMessage());
        errorList.add(error);
        Error err = new Error(errorList);
        LOGGER.debug("GlobalExceptionHandler.rulesGeneric400Exception - Ended");
        LOGGER.error("Bad Request");
        return new ResponseEntity<Error>(err, HttpStatus.BAD_REQUEST);
    }
}
