package Model;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class thar represents library
 * @author Alexander Rai
 * @version 1.0
 */

public class Library {
    private static Logger libraryLogger = LogManager.getLogger(Library.class.getName());

    /**
     * Books in library
     */
    private ArrayList<Book> booksInLibrary;

    /**
     * Readers in library
     */
    private ArrayList<Reader> readersInLibrary;

    /**
     * Running readers
     */
    private LinkedList<Thread> readersThreads;
    /**
     * Constructor fot library
     * @param booksCount - number of books to initialize library with
     * @param readersCount - number of readers to initialize library with
     */
    public Library(int booksCount, int readersCount){
        libraryLogger.info("Library started adding books");
        fillLibraryWithBooks(booksCount);
        libraryLogger.info("Library finished adding books");

        libraryLogger.info("Library started adding readers");
        addReaders(readersCount);
        libraryLogger.info("Library finished adding readers");
    }

    /**
     * Adds books to library
     * @param booksCount - number of books to add
     */
    private void fillLibraryWithBooks(int booksCount){
        booksInLibrary = new ArrayList<>(booksCount);
        for (int i = 0; i < booksCount; i++){
            booksInLibrary.set(i, new Book("Book #" + i));
            libraryLogger.info("Book #" + i + " has been added.");
        }
    }

    /**
     * Adds readers to library
     * @param readersCount - number of readers to add
     */
    private void addReaders(int readersCount){
        for (int i = 0; i < readersCount; i++) {
            Reader reader = new Reader("Reader #" + i, booksInLibrary);
            readersInLibrary.add(reader);
            libraryLogger.info("Reader #" + i + " has been added");
        }
    }

    /**
     * Starts library to work
     * */
    public void openLibrary() throws InterruptedException{
        for (Reader reader : readersInLibrary){
            Thread readerThread = new Thread(reader);
            readersThreads.add(readerThread);
            readerThread.run();
        }

        int countThreads = readersThreads.size();

        while (countThreads > 0) {
            for (Thread thd : readersThreads) {
                if (thd.getState() == Thread.State.TERMINATED) {
                    countThreads --;
                }
                Thread.sleep(100);
            }
        }
    }
}
