package Controller;

import Model.Library;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Main class
 * @author Alexander Rai
 * @version 1.0
 */
public class Main {

    static Logger myLogger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        myLogger.info("Application started");

        int booksByReadyRoomCount = 5;
        int peoplesCount = 9;

        Library bookLibrary = new Library(booksByReadyRoomCount, peoplesCount);

        try {
            bookLibrary.startWorkLibrary();

        }   catch (InterruptedException e) {
            e.printStackTrace();
            myLogger.error(e.getMessage());
        }

        myLogger.info("Application stopped");
    }
}
