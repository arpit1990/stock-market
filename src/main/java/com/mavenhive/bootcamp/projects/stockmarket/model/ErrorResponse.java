package com.mavenhive.bootcamp.projects.stockmarket.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    /** Error Message */
    private String message;

    /** Response Status Code */
    private Integer statusCode;

    /** Field level validation errors. Only applicable for ConstraintValidationErrors. */
    private List<FieldValidationError> validationErrors = new ArrayList<>();

    /**
     * Constructs an error response object without any message.
     */
    public ErrorResponse() {
        // Default constructor added due to presence of other constructors
    }

    /**
     * Constructs an error response object with specified message.
     *
     * @param errMsg message with which error response need
     */
    public ErrorResponse(final String errMsg, final Integer statusCode) {
        this.message = errMsg;
        this.statusCode = statusCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @return the validationErrors
     */
    public List<FieldValidationError> getValidationErrors() {
        return validationErrors.isEmpty() ? null : validationErrors;
    }

    /**
     * Adds a field level constraint validation error to this error response.
     *
     * @param fieldName name of field having invalid value
     * @param errorMsg containing details of error
     */
    public void addFieldValidationError(final String fieldName, final String errorMsg) {
        validationErrors.add(new FieldValidationError(fieldName, errorMsg));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ErrorResponse [message=" + message + ", validationErrors=" + validationErrors + "]";
    }
}

