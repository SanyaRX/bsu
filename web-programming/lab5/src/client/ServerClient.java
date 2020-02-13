package model.client;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerClient {
    /**
     * Client's name
     */
    private String name;

    /**
     * Client's writer
     */
    private PrintWriter writer;

    /**
     * Client's reader
     */
    private BufferedReader reader;

    public ServerClient(String name, PrintWriter writer, BufferedReader reader){
        this.name = name;
        this.writer = writer;
        this.reader = reader;
    }

    /**
     * Returns client's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns client's reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Returns client's writer
     */
    public PrintWriter getWriter() {
        return writer;
    }
}
