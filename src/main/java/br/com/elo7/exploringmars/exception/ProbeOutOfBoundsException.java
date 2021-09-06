package br.com.elo7.exploringmars.exception;

public class ProbeOutOfBoundsException extends RuntimeException {
    
    public ProbeOutOfBoundsException(int x, int y) {
        super(String.format("Position (%d, %d) is out of map bounds",  x, y));
    }

}
