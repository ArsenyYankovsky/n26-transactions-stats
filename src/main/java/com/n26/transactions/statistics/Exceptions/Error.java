/**********************************************************************************
 * File Name        : Error.java
 *
 * Description      : POJO class for error.
 *
 * Modification Log :
 * -----------------------------------------------------------------------
 * Ver #    	Date             Author             Modification
 * -----------------------------------------------------------------------
 * 1.0         11/07/2016     Ankur Malik         Baseline version.
 * -----------------------------------------------------------------------
 **********************************************************************************/
package com.n26.transactions.statistics.Exceptions;

import com.fasterxml.jackson.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "ErrorID",
        "Message"
})
public class Error {

    /**
     * class level logger variable.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Error.class);

    /**
     * Message.
     */
    @JsonProperty("Message")
    private String msg;

    /**
     * ErrorID.
     */
    @JsonProperty("ErrorID")
    private String errorId;

    /**
     * additionalProperties.
     */
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * errors.
     */
    @JsonProperty("errors")
    @Valid
    List<ErrorInformation> errors = new ArrayList<ErrorInformation>();

    /**
     * No args constructor for use in serialization.
     */
    public Error() {
        LOGGER.debug("Error default constructor has been initialized");
    }

    /**
     * constructor.
     * @param errors errors
     */
    public Error(List<ErrorInformation> errors) {
        this.errors = errors;
    }

    /**
     * getErrors.
     * @return ErrorInformation
     */
    public List<ErrorInformation> getErrors() {
        return errors;
    }

    /**
     * setErrors.
     * @param errors ErrorInformation
     */
    public void setErrors(List<ErrorInformation> errors) {
        this.errors = errors;
    }

    /**
     * Error constructor.
     * @param errorID ErrorID
     * @param message Message
     */
    public Error(String errorID,String message) {
        this.errorId = errorID;
        this.msg = message;
    }

    /**
     * getErrorID.
     * @return The ErrorID
     */
    @JsonProperty("ErrorID")
    public String getErrorID() {
        return errorId;
    }

    /**
     * setErrorID.
     * @param errorID The ErrorID
     */
    @JsonProperty("ErrorID")
    public void setErrorID(String errorID) {
        this.errorId = errorID;
    }

    /**
     * withErrorID.
     * @param errorID ErrorID
     * @return Error
     */
    public Error withErrorID(String errorID) {
        this.errorId = errorID;
        return this;
    }

    /**
     * getMessage.
     * @return The Message
     */
    @JsonProperty("Message")
    public String getMessage() {
        return msg;
    }

    /**
     * setMessage.
     * @param message The Message
     */
    @JsonProperty("Message")
    public void setMessage(String message) {
        this.msg = message;
    }

    /**
     * withMessage.
     * @param message Message
     * @return Error
     */
    public Error withMessage(String message) {
        this.msg = message;
        return this;
    }


    /**
     * getAdditionalProperties.
     * @return additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * setAdditionalProperty.
     * @param name name
     * @param value value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     * withAdditionalProperty.
     * @param name name
     * @param value value
     * @return Error
     */
    public Error withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
