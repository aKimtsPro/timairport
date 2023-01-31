package be.technifutur.java.timairport.exceptions;

public class NoAvailablePlaneException extends RuntimeException {
    public NoAvailablePlaneException() {
        super("No available planes were found for these constraints");
    }
}
