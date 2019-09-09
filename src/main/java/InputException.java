/**
 * This Exception is thrown when the format to an input for writing/updating/deleting any lines
 * in Duke is invalid or incorrect
 * @author Aw Chian Hao
 */
public class InputException extends Exception {
    /**
     * Constructs the Exception when the input format is invalid
     * @param message the error message that is printed upon throwing the exception
     */
    public InputException(String message) {
        super(message);
    }
}
