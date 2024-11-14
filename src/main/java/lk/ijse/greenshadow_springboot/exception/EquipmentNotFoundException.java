package lk.ijse.greenshadow_springboot.exception;

public class EquipmentNotFoundException extends RuntimeException {

    public EquipmentNotFoundException(String message) {
        super(message);
    }

    public EquipmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
