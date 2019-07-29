package system787.garagepi.pi4j.exception;

public class StateNotReadyException extends Exception {

    private String errorMessage;

    public StateNotReadyException(String message) {
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
