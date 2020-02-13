package controller.xml_utils;

import model.TravelVoucher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents StAX parser.
 * @autor Alexander Rai
 * @version 1.0
 */

public class StAXParser extends AbstractParser {
    static Logger staxParserValidatorLogger = LogManager.getLogger(StAXParser.class.getName());
    private String vehicle, duration, nutrition, price, parameter;
    @Override
    public List<TravelVoucher> parseVouchers(String xmlFilePath) throws IOException, SAXException {
        List<TravelVoucher> travelVouchers = new ArrayList<>();
        staxParserValidatorLogger.info("StAX parser started working...");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader(xmlFilePath));
            boolean readingVoucher = true;
            String voucherName = "";
            String currentParameter = null;

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:{
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        String parameterName = AbstractParser.getVoucherParameterName(qName);

                        if (parameterName != null) {

                            readingVoucher = true;
                            voucherName = qName;

                        } else {
                            currentParameter = qName;
                        }
                        break;
                    }

                    case XMLStreamConstants.CHARACTERS: {
                        Characters characters = event.asCharacters();
                        if (readingVoucher && currentParameter != null) {
                            String line = characters.getData();
                            writeToParameter(line, currentParameter);
                            currentParameter = null;
                        }
                        break;
                    }

                    case XMLStreamConstants.END_ELEMENT: {
                        EndElement endElement = event.asEndElement();
                        String parameterName = AbstractParser.getVoucherParameterName(endElement.getName().getLocalPart());
                        if (parameterName != null) {
                            readingVoucher = false;
                            travelVouchers.add(AbstractParser.buildVoucher(vehicle, duration, nutrition,
                                    price, parameter, voucherName));
                            voucherName = "";
                        }
                        break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            staxParserValidatorLogger.error(e.getMessage());
        }

        return travelVouchers;
    }

    /**
     * Sets required parameters with value
     */
    private void writeToParameter(String parameter, String parameterName){
        switch (parameterName){
            case "vehicleType": this.vehicle = parameter; break;
            case "duration": this.duration = parameter; break;
            case "nutritionType": this.nutrition = parameter; break;
            case "price": this.price = parameter; break;
            case "hospital": this.parameter = parameter; break;
            case "cabin": this.parameter = parameter; break;
            case "placesToVisit": this.parameter = parameter; break;
            case "cityToTravel": this.parameter = parameter; break;
            default: break;
        }
    }
}

