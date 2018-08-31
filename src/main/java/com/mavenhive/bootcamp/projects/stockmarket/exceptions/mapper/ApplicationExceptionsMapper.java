package com.mavenhive.bootcamp.projects.stockmarket.exceptions.mapper;

import com.mavenhive.bootcamp.projects.stockmarket.exceptions.IncorrectUserCredentialsException;
import com.mavenhive.bootcamp.projects.stockmarket.exceptions.UnauthorizedAccessException;
import com.mavenhive.bootcamp.projects.stockmarket.model.ErrorResponse;
import org.glassfish.jersey.server.ResourceConfig;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

public class ApplicationExceptionsMapper {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionsMapper.class);

    /**
     * private constructor to prevent creation of instances of this class.
     */
    private ApplicationExceptionsMapper() {
    }

    /**
     * {@code ApplicationExceptionMapper} provides a generic implementation of
     * {@link ExceptionMapper} to define how error response should be created in
     * case of any exceptions thrown by services.
     */
    private static class ApplicationExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

        /** Default Status object containing status code */
        private final Status status;

        /** Default error message */
        private final String message;

        /**
         * flag to check whether complete exception stack trace should be
         * printed. Introduced to reduce noise in log files
         */
        private final boolean logExceptionTrace;

        /**
         * Constructs a new exception mapper that will derive message and status
         * code from exception to create an error response.
         */
        ApplicationExceptionMapper() {
            this(null, null, true);
        }

        /**
         * Constructs a new exception mapper that will utilize specified status
         * and derive message from exception to create an error response.
         *
         * @param status
         *            Status object containing statusCode to be utilized for
         *            creating error response.
         */
        ApplicationExceptionMapper(final Status status) {
            this(status, null, true);
        }

        /**
         * Constructs a new exception mapper that will utilize specified status
         * and derive message from exception to create an error response.
         *
         * @param status
         *            Status object containing statusCode to be utilized for
         *            creating error response.
         * @param logExceptionTrace
         *            whether complete exception stack trace needs to be printed
         */
        ApplicationExceptionMapper(final Status status, final boolean logExceptionTrace) {
            this(status, null, logExceptionTrace);
        }

        /**
         * Constructs a new exception mapper that will utilize specified status
         * and message to create an error response.
         *
         * @param status
         *            Status object containing statusCode to be utilized for
         *            creating error response.
         * @param message
         *            text to be utilized for creating error response.
         */
        ApplicationExceptionMapper(final Status status, final String message) {
            this(status, message, true);
        }

        /**
         * Constructs a new exception mapper that will utilize specified status
         * and message to create an error response.
         *
         * @param status
         *            Status object containing statusCode to be utilized for
         *            creating error response.
         * @param message
         *            text to be utilized for creating error response.
         * @param logExceptionTrace
         *            whether complete exception stack trace needs to be printed
         */
        ApplicationExceptionMapper(final Status status, final String message, final boolean logExceptionTrace) {
            this.status = status;
            this.message = message;
            this.logExceptionTrace = logExceptionTrace;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
         */
        @Override
        public Response toResponse(final T exception) {
            String errMsg = this.message != null ? this.message : exception.getMessage();
            int statusCode = this.status != null ? this.status.getStatusCode() : 500;

            // Since WebApplicationException contains response object, get the
            // statusCode from it
            if (exception instanceof WebApplicationException) {
                statusCode = ((WebApplicationException) exception).getResponse().getStatus();
            }

            final ErrorResponse errorResponse = new ErrorResponse(errMsg, statusCode);

            // Build message from constraints violation messages in case of
            // ConstraintViolationException
            if (exception instanceof ConstraintViolationException) {
                final Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception)
                        .getConstraintViolations();

                for (final ConstraintViolation<?> constraintViolation : constraintViolations) {
                    final Annotation annotation = constraintViolation.getConstraintDescriptor().getAnnotation();

                    // Check if annotation has targetField method
                    Method targetFieldMethod = null;
                    for (Method method : annotation.getClass().getDeclaredMethods()) {
                        if ("targetField".equals(method.getName())) {
                            targetFieldMethod = method;
                            break;
                        }
                    }

                    // If targetField method is present, use it to get field
                    // name
                    String fieldName = null;
                    String fieldErrMsg = constraintViolation.getMessage();
                    if (targetFieldMethod != null) {
                        try {
                            fieldName = (String) targetFieldMethod.invoke(annotation);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            LOG.error(e.getMessage(), e);
                        }
                    } else {
                        final String propertyPath = constraintViolation.getPropertyPath().toString();
                        int index = propertyPath.lastIndexOf('.');
                        final String propertyPathLeafNode = propertyPath.substring(index >= 0 ? index + 1 : 0);

                        if(!propertyPathLeafNode.contains("arg")) {
                            fieldName = propertyPathLeafNode;
                        } else {
                            // Drive field name from message by using colon(:) as
                            // field and msg string separator
                            final String separator = ":";
                            final int separatorIndex = fieldErrMsg.indexOf(separator);

                            fieldName = (separatorIndex >= 0 ? fieldErrMsg.substring(0, separatorIndex).trim()
                                    : "Specify field name in message attribute of constraint annotations in format - 'field name: error msg' ");
                            fieldErrMsg = (separatorIndex >= 0 ? fieldErrMsg.substring(separatorIndex + 1).trim()
                                    : fieldErrMsg);
                        }
                    }

                    // Add field validation error to final error response
                    errorResponse.addFieldValidationError(fieldName, fieldErrMsg);
                }
            }

            if(logExceptionTrace) {
                LOG.error(errorResponse.toString(), exception);
            } else {
                LOG.error(errorResponse.toString());
            }

            return Response.status(statusCode).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
        }

    }

    /**
     * Registers exception mappers for custom, generic and Jersey provided
     * exceptions with specified {@link ResourceConfig} instance.
     *
     * @param resourceConfig
     *            ResourceConfig object whom exception mappers need to be
     *            registered to.
     *
     */
    public static void registerExceptionMappers(final ResourceConfig resourceConfig) {

        // Register exception mappers for Jersey provided exceptions
        // Message will be taken from exception itself
        resourceConfig.register(new ApplicationExceptionMapper<ConstraintViolationException>(Status.BAD_REQUEST, false) {
        });
        // Message will be taken from exception itself
        resourceConfig.register(new ApplicationExceptionMapper<ValidationException>(Status.BAD_REQUEST, false) {
        });
        // Message will be taken from exception itself
        resourceConfig.register(new ApplicationExceptionMapper<NotFoundException>(Status.NOT_FOUND, false) {
        });
        // Message will be taken from exception itself
        resourceConfig.register(new ApplicationExceptionMapper<EmptyResultDataAccessException>(Status.NOT_FOUND) {
        });
        // Message and Status code will be taken from exception itself
        resourceConfig.register(new ApplicationExceptionMapper<WebApplicationException>() {
        });

        // Register custom exceptions
        resourceConfig
                .register(new ApplicationExceptionMapper<NotFoundException>(Status.NOT_FOUND, false) {
                });

        resourceConfig.register(new ApplicationExceptionMapper<IncorrectUserCredentialsException>(Status.UNAUTHORIZED, false) {
        });

        resourceConfig.register(new ApplicationExceptionMapper<UnauthorizedAccessException>(Status.FORBIDDEN, false) {
        });

        // Register generic exception
        resourceConfig.register(new ApplicationExceptionMapper<Exception>(Status.INTERNAL_SERVER_ERROR,
                "Unexpected error while processing your request") {
        });
    }
}