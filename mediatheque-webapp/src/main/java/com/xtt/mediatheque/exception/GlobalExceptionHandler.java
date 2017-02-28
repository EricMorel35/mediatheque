package com.xtt.mediatheque.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xtt.mediatheque.exceptions.TechnicalAccessException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { TechnicalAccessException.class })
	protected ResponseEntity<Object> handleConflict(TechnicalAccessException ex, WebRequest request) {
		Error error = new Error();
		error.setCode(ex.getCode());
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
