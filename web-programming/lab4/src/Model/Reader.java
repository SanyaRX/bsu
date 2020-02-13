package Model;

import javafx.collections.ObservableList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Class thar represents book reader
 * @author Alexander Rai
 * @version 1.0
 */
public class Reader implements Runnable {
    static Logger peopleLogger = LogManager.getLogger(Reader.class.getName());

    /**
     * Lock for synchronizing
     */
    private static Object readerLock = new Object();

    /**
     * Reader name
     */
    private String readerName;

    /**
     * List of books reader can take
     */
    private ArrayList<Book> bookList;

    private Random random = new Random();
    public Reader(String readerName, ArrayList<Book> bookList){
        this.readerName = readerName;
        this.bookList = bookList;
    }

    @Override
    public void run() {

    }

    private Book takeBookForReading(){
        Book book = null;
        synchronized (readerLock){
            int idx = random.nextInt(bookList.size());
        }
        return book;
    }
}
