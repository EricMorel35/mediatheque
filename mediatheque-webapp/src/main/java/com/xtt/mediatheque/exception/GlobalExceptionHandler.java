package com.xtt.mediatheque.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { TechnicalAccessException.class })
	protected ResponseEntity<Object> handleTechnical(TechnicalAccessException ex, WebRequest request) {
		ErrorDTO error = new ErrorDTO();
		error.setCode(ex.getCode());
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { MessageException.class })
	protected ResponseEntity<Object> handleMessage(MessageException ex, WebRequest request) {
		MessageDTO error = new MessageDTO();
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { FonctionnalException.class })
	protected ResponseEntity<Object> handleFonctionnal(FonctionnalException ex, WebRequest request) {
		MessageDTO error = new MessageDTO();
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { MovieNotFoundException.class })
	protected ResponseEntity<Object> handleFonctionnal(MovieNotFoundException ex, WebRequest request) {
		MessageDTO error = new MessageDTO();
		error.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
