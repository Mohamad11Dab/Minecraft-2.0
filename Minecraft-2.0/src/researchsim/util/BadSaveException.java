package researchsim.util;

/**
 * Exception thrown when a save file is invalid or contains incorrect data.
 *
 * @ass2
 */
public class BadSaveException extends Exception {

    public BadSaveException() {
        super();
    }
    public BadSaveException(String message) {
        super(message);
    }
    public BadSaveException(Throwable cause) {
        super(cause);
    }
    public BadSaveException(String message,
                             Throwable cause) {
        super(message, cause);
    }


}
