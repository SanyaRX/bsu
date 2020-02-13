package Model;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * Class thar represents book
 * @author Alexander Rai
 * @version 1.0
 */
public class Book{
    /**
     * Book name
     */
    private String bookName;


    public Book(String bookName){
        this.bookName = bookName;
    }
}
