package main.exception;

import java.util.NoSuchElementException;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import main.DTO.MessageDTO;

@RestControllerAdvice
public class GeneralExceptionHandler {
	@ExceptionHandler({
		AccountException.class , AccountNotFoundException.class , NotAuthorizationException.class , NoSuchElementException.class
	})
	public MessageDTO handleBadRequestException(Exception e) {
	    return MessageDTO.builder()
	    		.code("-1")
	    		.message(e.getMessage())
	    		.build();
	}
}
