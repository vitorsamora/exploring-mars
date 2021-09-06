package br.com.elo7.exploringmars.exception;

public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String msg) {
        super(msg);
    }

}
