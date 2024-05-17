package br.com.energysa.energysareport.exceptions;


public class ConcurrentException extends RuntimeException {
    public ConcurrentException() {
        super();
    }

    public ConcurrentException(Throwable cause) {
        super(cause);
    }
}
