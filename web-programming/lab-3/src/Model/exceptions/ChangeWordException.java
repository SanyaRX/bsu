package Model.exceptions;

/**
 * Class that represents change word exception
 * @author Alexander Rai
 * @version 1.0
 */
public class ChangeWordException extends Exception {

    /**
     * Constructor for exception with specified string
     * @param message
     */
    public ChangeWordException(String message){
        super("Error while word changing, " + message);
    }

    /**
     * Constructor for exception with specified string and exception
     * @param message
     * @param e
     */
    public ChangeWordException(String message, Throwable e){
        super("Error while word changing, " + message, e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
