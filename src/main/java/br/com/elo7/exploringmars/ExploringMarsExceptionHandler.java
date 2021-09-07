package br.com.elo7.exploringmars;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.elo7.exploringmars.bean.ErrorResp;
import br.com.elo7.exploringmars.bean.FieldErrorResp;
import br.com.elo7.exploringmars.exception.ProbeOutOfBoundsException;
import br.com.elo7.exploringmars.exception.UnauthorizedException;
import br.com.elo7.exploringmars.exception.MapNotEmptyException;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.exception.ProbeCollisionException;

@ControllerAdvice
public class ExploringMarsExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ErrorResp notFoundError(NotFoundException exception) {
		ErrorResp error = new ErrorResp();
        error.setMessage(exception.getMessage());
		return error;
	}

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ErrorResp unauthorizedError(UnauthorizedException exception) {
		ErrorResp error = new ErrorResp();
        error.setMessage(exception.getMessage());
		return error;
	}

    @ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({MapNotEmptyException.class, 
        ProbeOutOfBoundsException.class, ProbeCollisionException.class})
	@ResponseBody
	public ErrorResp forbiddenErrors(Exception exception) {
		ErrorResp error = new ErrorResp();
        error.setMessage(exception.getMessage());
		return error;
	}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldErrorResp> errors = new ArrayList<FieldErrorResp>();
        for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) objError;
            FieldErrorResp error = new FieldErrorResp();
            error.setField(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errors.add(error);
        }
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

}
