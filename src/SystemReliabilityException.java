/**
 * Exception thrown when the drone system becomes unreliable
 * and enters SAFE MODE.
 */
public class SystemReliabilityException extends Exception {

    /**
     * Constructor for SystemReliabilityException.
     * @param message Exception message
     */
    public SystemReliabilityException(String message) {
        super(message);
    }
}
