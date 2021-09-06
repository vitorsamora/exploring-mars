package br.com.elo7.exploringmars.exception;

public class ProbeCollisionException extends RuntimeException {
    
    public ProbeCollisionException(int x, int y) {
        super(String.format("There is already a probe at position (%d, %d)", x, y));
    }

}
