package controller.xml_utils;

import model.TravelVoucher;
import model.Vouchers.Cruise;
import model.Vouchers.Medication;
import model.Vouchers.Tour;
import model.Vouchers.Travel;
import model.enums.Duration;
import model.enums.NutritionType;
import model.enums.VehicleType;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public abstract class AbstractParser {

    /**
     * Parses xml file using DOM
     * @param xmlFilePath - xml file path
     */
    abstract public List<TravelVoucher> parseVouchers(String xmlFilePath) throws ParserConfigurationException, IOException, SAXException;

    /**
     * Creates one of the vouchers according to parsed parameters
     */
    protected static TravelVoucher buildVoucher(String vehicle, String duration, String nutrition,
                                       String priceString, String parameter, String voucherClass){
        int price = Integer.parseInt(priceString);
        switch (voucherClass){
            case "travel": return new Travel(parameter, VehicleType.valueOf(vehicle),
                    NutritionType.valueOf(nutrition), Duration.valueOf(duration), price);
            case "tour": return new Tour(parameter, VehicleType.valueOf(vehicle),
                    NutritionType.valueOf(nutrition), Duration.valueOf(duration), price);
            case "cruise": return new Cruise(parameter, NutritionType.valueOf(nutrition),
                    Duration.valueOf(duration), price);
            case "medication": return new Medication(parameter, VehicleType.valueOf(vehicle),
                    NutritionType.valueOf(nutrition), Duration.valueOf(duration), price);
            default: return null;
        }
    }

    /**
     * Returns voucher parameter's
     */
    protected static String getVoucherParameterName(String voucherName) {
        String parameterName = "";
        switch (voucherName){
            case "travel": parameterName = "cityToTravel"; break;
            case "tour": parameterName = "placesToVisit"; break;
            case "cruise": parameterName = "cabin"; break;
            case "medication": parameterName = "hospital"; break;
            default: parameterName = null; break;
        }
        return parameterName;
    }
}
