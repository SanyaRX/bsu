package controller.xml_utils;

import model.TravelVoucher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.*;

/**
 * Class that represents SAX parser.
 * @autor Alexander Rai
 * @version 1.0
 */


public class MySAXParser extends AbstractParser{
    static Logger saxParserValidatorLogger = LogManager.getLogger(MySAXParser.class.getName());

    /**
     * Parses xml file using DOM
     * @param xmlFilePath - xml file path
     */
    public List<TravelVoucher> parseVouchers(String xmlFilePath) throws ParserConfigurationException, IOException,
            SAXException {

        saxParserValidatorLogger.info("SAXParser started working...");
        List<TravelVoucher> travelVouchers = new ArrayList<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = factory.newSAXParser();

        DefaultHandler saxHandler = new SAXContentHandler(travelVouchers);
        saxParser.parse(xmlFilePath, saxHandler);
        return travelVouchers;
    }
}
