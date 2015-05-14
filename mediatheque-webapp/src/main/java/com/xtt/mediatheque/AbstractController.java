//package com.xtt.mediatheque;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.client.RestClientException;
//
//import com.xtt.mediatheque.dto.ExceptionDTO;
//import com.xtt.mediatheque.exceptions.FonctionnalException;
//import com.xtt.mediatheque.exceptions.MessageException;
//import com.xtt.mediatheque.exceptions.MovieNotFoundException;
//import com.xtt.mediatheque.exceptions.TechnicalAccessException;
//
///**
// * Abstraction des controleurs.
// * 
// * @author Eric Morel
// */
//@Controller
//public class AbstractController {
//
//	/* Service. */
//	@Autowired
//	// protected MovieService movieService;
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(RestClientException.class)
//	public @ResponseBody
//	ExceptionDTO handleRestClientException(final RestClientException exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage("Une erreur technique est survenue : "
//				+ exception.getMessage());
//		return dto;
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(Throwable.class)
//	public @ResponseBody
//	ExceptionDTO handleThrowable(final Throwable exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage("Une erreur technique est survenue : "
//				+ exception.getMessage());
//		return dto;
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(FonctionnalException.class)
//	@ResponseBody
//	public ExceptionDTO handleFonctionnalException(
//			final FonctionnalException exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage(exception.getMessage());
//		return dto;
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(MovieNotFoundException.class)
//	@ResponseBody
//	public ExceptionDTO handleMovieNotFoundException(
//			final MovieNotFoundException exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage(exception.getMessage());
//		return dto;
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(TechnicalAccessException.class)
//	@ResponseBody
//	public ExceptionDTO handleTechnicalException(
//			final TechnicalAccessException exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage(exception.getMessage());
//		return dto;
//	}
//
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(MessageException.class)
//	@ResponseBody
//	public ExceptionDTO handleMessageException(final MessageException exception) {
//		ExceptionDTO dto = new ExceptionDTO();
//		dto.setMessage(exception.getMessage());
//		return dto;
//	}
//
// }