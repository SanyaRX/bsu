package controller.xml_utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Class that represents xml validator.
 * @autor Alexander Rai
 * @version 1.0
 */

public class XMLValidator {
    static Logger xmlValidatorLogger = LogManager.getLogger(XMLValidator.class.getName());
    /**
     * Validate xml file using xsd
     * @param xmlFileName xml file name
     * @param xsdFileName xsd file name
     */
    public static boolean validate(String xmlFileName, String xsdFileName) throws IOException {
        xmlValidatorLogger.info("XMLValidator started working...");
        try {
            SchemaFactory scFact =
                    SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            File f = new File(xsdFileName);
            Schema schema = scFact.newSchema(f);
            schema.newValidator().validate(new StreamSource(xmlFileName)) ;
        } catch (IOException e) {
            xmlValidatorLogger.error(e.getMessage());
        } catch (SAXException e){
            xmlValidatorLogger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
