package controller.xml_utils;

import model.TravelVoucher;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents DOM parser.
 * @autor Alexander Rai
 * @version 1.0
 */


public class DOMParser extends AbstractParser{
    static Logger domParserValidatorLogger = LogManager.getLogger(DOMParser.class.getName());

    /**
     * Parses xml file using DOM
     * @param xmlFilePath - xml file path
     */
    public List<TravelVoucher> parseVouchers(String xmlFilePath) throws ParserConfigurationException, IOException,
            SAXException {
        domParserValidatorLogger.info("DOMParser started working...");

        List<TravelVoucher> travelVouchers = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File( xmlFilePath ));
        document.getDocumentElement().normalize();
        NodeList travelVoucher = document.getChildNodes();

        for (int i = 0; i < travelVoucher.getLength(); i++){
            NodeList vouchers = travelVoucher.item(i).getChildNodes();
            for (int j = 0; j < vouchers.getLength(); j++){
                String voucherName = vouchers.item(j).getNodeName();
                if(!voucherName.equals("#text")){
                    NodeList voucherNode = document.getElementsByTagName(voucherName);
                    for (int k = 0; k < voucherNode.getLength(); k++){
                        Element element = (Element)voucherNode.item(k);
                        String vehicleType = element.getElementsByTagName("vehicleType").item(0).getTextContent();
                        String duration = element.getElementsByTagName("duration").item(0).getTextContent();
                        String nutritionType = element.getElementsByTagName("nutritionType").item(0).getTextContent();
                        String price = element.getElementsByTagName("price").item(0).getTextContent();
                        String parameterName = AbstractParser.getVoucherParameterName(voucherName);
                        String parameter = element.getElementsByTagName(parameterName).item(0).getTextContent();
                        travelVouchers.add(buildVoucher(vehicleType, duration, nutritionType, price, parameter,voucherName));
                    }
                }
            }
        }


        return travelVouchers;
    }
}
