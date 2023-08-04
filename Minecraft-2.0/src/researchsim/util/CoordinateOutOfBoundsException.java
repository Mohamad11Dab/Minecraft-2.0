package researchsim.util;

/**
 * Exception thrown when the program attempts to access a Tile using an invalid Coordinate / index.
 *
 * @ass2
 */
public class CoordinateOutOfBoundsException extends Exception {

    public CoordinateOutOfBoundsException() {
        super();

    }
    public CoordinateOutOfBoundsException(String message) {
        super(message);

    }
    public CoordinateOutOfBoundsException(Throwable cause) {
        super(cause);
    }
    public CoordinateOutOfBoundsException(String message,
                                           Throwable cause) {
        super(message, cause);
    }

}
