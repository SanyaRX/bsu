package Model.exceptions;



/**
 * Class that represents file load exception
 * @author Alexander Rai
 * @version 1.0
 */
public class FileLoadException extends Exception {

    /**
     * Constructor for exception with specified string
     * @param message
     */
    public FileLoadException(String message){
        super("Error while loading file, " + message);
    }

    /**
     * Constructor for exception with specified string and exception
     * @param message
     * @param e
     */
    public FileLoadException(String message, Throwable e){
        super("Error while loading file, " + message, e);
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
