/**********************************************************************************
 * File Name        : ErrorInformation.java
 *
 * Description      : POJO class for ErrorInformation.
 *
 * Modification Log :
 * -----------------------------------------------------------------------
 * Ver #    	Date             Author             Modification
 * -----------------------------------------------------------------------
 * 1.0         11/07/2016     Ankur Malik         Baseline version.
 * -----------------------------------------------------------------------
 **********************************************************************************/
package com.n26.transactions.statistics.Exceptions;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorInformation {

    /**
     * class level logger variable.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInformation.class);

    /**
     * statusCode.
     */
    @ApiModelProperty(required = true)
    String statusCd;

    /**
     * errorCode.
     */
    String errorCd;

    /**
     * title.
     */
    @ApiModelProperty(required = true)
    String ttl;

    /**
     * message.
     */
    @ApiModelProperty(required = true)
    String msg;
    /**
     * message.
     */
    @ApiModelProperty(required = true)
    String stackMessage;

    /**
     * advice.
     */
    String advc;

    /**
     * constructor.
     */
    public ErrorInformation() {
        LOGGER.debug("ErrorInformation default constructor has been initialized.");
    }

    /**
     * getStatusCode.
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCd;
    }

    /**
     * setStatusCode.
     * @param statusCode statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCd = statusCode;
    }

    /**
     * getErrorCode.
     * @return errorCode
     */
    public String getErrorCode() {
        return errorCd;
    }

    /**
     * setErrorCode.
     * @param errorCode errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCd = errorCode;
    }

    /**
     * getTitle.
     * @return title
     */
    public String getTitle() {
        return ttl;
    }

    /**
     * setTitle.
     * @param title title
     */
    public void setTitle(String title) {
        this.ttl = title;
    }

    /**
     * getMessage.
     * @return message
     */
    public String getMessage() {
        return msg;
    }

    /**
     * setMessage.
     * @param stackMessage message
     */
    public void setStackMessage(String stackMessage) {
        this.stackMessage = stackMessage;
    }

    /**
     * getMessage.
     * @return message
     */
    public String getStackMessage() {
        return stackMessage;
    }

    /**
     * setMessage.
     * @param message message
     */
    public void setMessage(String message) {
        this.msg = message;
    }

    /**
     * getAdvice.
     * @return advice
     */
    public String getAdvice() {
        return advc;
    }

    /**
     * setAdvice.
     * @param advice advice
     */
    public void setAdvice(String advice) {
        this.advc = advice;
    }
}
