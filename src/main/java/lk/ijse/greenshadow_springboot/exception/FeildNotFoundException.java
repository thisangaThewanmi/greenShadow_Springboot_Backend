package lk.ijse.greenshadow_springboot.exception;

public class FeildNotFoundException extends RuntimeException {
    public FeildNotFoundException() {
        super();
    }

    public FeildNotFoundException(String message) {
        super(message);
    }

    public FeildNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
