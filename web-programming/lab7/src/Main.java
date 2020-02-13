import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import controller.xml_utils.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Class to show work with classes.
 * @autor Alexander Rai
 * @version 1.0
 */

public class Main {
    static Logger mainLogger = LogManager.getLogger(DOMParser.class.getName());
    /**
     * Method that runs the program
     * */
    public static void main(String[] args) throws IOException {
        mainLogger.info("Program started...");
        boolean validationResult = false;
        try {
            validationResult = XMLValidator.validate("xml_files/vouchers.xml",
                    "xml_files/vouchers_schema.xsd");
        } catch (IOException e) {
            mainLogger.error(e.getMessage());
        }

        if (!validationResult){
            System.err.println("Error with validation");
            return;
        } else {
            System.out.println("Validation was successful");
        }

        AbstractParser parser = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Choose a parser: ");
        System.out.println("1 - DOM parser");
        System.out.println("2 - SAXParser");
        System.out.println("3 - StAXParser");

        switch (reader.readLine()){
            case "1": parser = new DOMParser(); break;
            case "2": parser = new MySAXParser(); break;
            case "3": parser = new StAXParser(); break;
            default:
                System.out.println("Invalid input");
                return;
        }

        try {
            System.out.println(Arrays.toString(parser.parseVouchers("xml_files/vouchers.xml").toArray()));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            mainLogger.error(e.getMessage());
        }
        mainLogger.info("Program finished...");
    }

}
