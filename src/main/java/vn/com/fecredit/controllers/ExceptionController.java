package vn.com.fecredit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import vn.com.fecredit.exception.AwsS3ServiceException;
import vn.com.fecredit.models.ErrorModel;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	private Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		String errMsg = "";
		for (String err : errors) {
			errMsg += err;
			errMsg += " ";
		}
		LOGGER.info(errMsg);
		ErrorModel apiError = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Method argument is not valid");
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		LOGGER.info(error);
		ErrorModel apiError = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Request missing parameter");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		String errMsg = "";
		for (String err : errors) {
			errMsg += err;
			errMsg += " ";
		}
		LOGGER.info(errMsg);
		ErrorModel apiError = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Data model constraint violation");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		LOGGER.info(error);
		ErrorModel apiError = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Wrong type for method argument " + error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AwsS3ServiceException.class)
	public ResponseEntity<ErrorModel> mapAwsS3ServiceException(AwsS3ServiceException e) {
		LOGGER.info(e.getMessage());
		ErrorModel err = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error happened at service");
		return new ResponseEntity<ErrorModel>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorModel> mapGenericException(Exception e) {
		LOGGER.info(e.getMessage());
		ErrorModel err = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown exception happened!");
		return new ResponseEntity<ErrorModel>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Work with config: server.error.whitelabel.enabled=false &
	// spring.mvc.throw-exception-if-no-handler-found=true
	@Override
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		LOGGER.info(error);
		ErrorModel err = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Sorry, we do not serve this resource: " + error);
		return new ResponseEntity<Object>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
