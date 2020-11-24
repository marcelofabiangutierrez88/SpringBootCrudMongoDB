package com.marcelo.spring.data.mongodb.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import com.marcelo.spring.data.mongodb.model.ErrorResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ControllerAdvice
public class SpringDataMongodbAdvice {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler({HttpClientErrorException.class})
	protected ResponseEntity<?> httpClientErrorException(HttpClientErrorException he, WebRequest request){
		logException(he, request);
		ErrorResponse error = new ErrorResponse("ERROR-500", he.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
	@ExceptionHandler({HttpServerErrorException.class})
	protected ResponseEntity<?> httpServerErrorException(HttpServerErrorException hs, WebRequest request) {
		logException(hs, request);
		ErrorResponse error = new ErrorResponse("ERROR-500", hs.getResponseBodyAsString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	
	@ExceptionHandler({Exception.class})
	protected ResponseEntity<?> exception(Exception e, WebRequest request){
		logException(e, request);
		ErrorResponse error = new ErrorResponse("ERROR-500", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	private void logException(Exception e, WebRequest request) {
		this.logger.error("error pasando por adviser para " + request.getContextPath());
		this.logger.error("ERROR: " + e);
		e.printStackTrace();
	}
}


