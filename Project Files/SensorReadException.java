import java.io.IOException;

/**
 * Exception thrown when a sensor fails to read data.
 */
public class SensorReadException extends IOException {

    /**
     * Constructor for SensorReadException.
     * @param message Exception message
     */
    public SensorReadException(String message) {
        super(message);
    }
}
