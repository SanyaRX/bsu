package controller.xml_utils;

import model.TravelVoucher;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * Class that represents SAX handler.
 * @autor Alexander Rai
 * @version 1.0
 */
public class SAXContentHandler extends DefaultHandler {

    /**
     * List of parsed vouchers
     */
    List<TravelVoucher> travelVouchers;

    boolean readingVoucher = true;
    String voucherName = "";
    String currentParameter = null;
    String vehicle, duration, nutrition, price, parameter;

    public SAXContentHandler(List<TravelVoucher> vouchers){
        this.travelVouchers = vouchers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        String parameterName = AbstractParser.getVoucherParameterName(qName);

        if(parameterName != null) {
            readingVoucher = true;
            voucherName = qName;
        } else {
            currentParameter = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {


        String parameterName = AbstractParser.getVoucherParameterName(qName);
        if(parameterName != null) {
            readingVoucher = false;
            travelVouchers.add(AbstractParser.buildVoucher(vehicle, duration, nutrition,
                    price, parameter, voucherName));
            voucherName = "";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(!readingVoucher || currentParameter == null)
            return;
        String line = new String(ch, start, length);
        writeToParameter(line, currentParameter);
        currentParameter = null;

    }

    private void writeToParameter(String parameter, String parameterName){
        switch (parameterName){
            case "vehicleType": vehicle = parameter; break;
            case "duration": duration = parameter; break;
            case "nutritionType": nutrition = parameter; break;
            case "price": price = parameter; break;
            case "hospital": this.parameter = parameter; break;
            case "cabin": this.parameter = parameter; break;
            case "placesToVisit": this.parameter = parameter; break;
            case "cityToTravel": this.parameter = parameter; break;
            default: break;
        }
    }
}
